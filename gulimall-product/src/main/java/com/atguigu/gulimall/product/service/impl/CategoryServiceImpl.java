package com.atguigu.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.Catelog2Vo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    RedissonClient redisson;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithtree() {
//        查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
//        组装成父子树形结构
//       找到所有一级分类
        return entities.stream().filter((categoryEntity) -> categoryEntity.getParentCid() == 0)
                .peek((menu) -> menu.setChildren(getChildren(menu, entities)))
                .sorted(Comparator.comparingInt(menu -> (Optional.ofNullable(menu.getSort()).orElse(0))))
                .collect(Collectors.toList());
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
//            TODO 1、检查当前删除的菜单是否被别的菜单引用
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCategoryPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);
        Collections.reverse(parentPath);
        return paths.toArray(new Long[parentPath.size()]);
    }

    //    级联更新所有关联的数据
//    @Caching(evict = {
//            @CacheEvict(value = "category",key = "'getLevel1Categorys'"),
//            @CacheEvict(value = "category",key = "'getCatalogJson'")
//    })
    @CacheEvict(value = "category",allEntries = true)
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }
//    1、每一个需要缓存的数据我们都来指定要放到那个名字的缓存，【缓存的分区(按照业务类型分)】
//    2、当前方法的结果需要缓存，如果缓存中有，方法不用调用，如果缓存中没有，会调用方法，最后将方法的结果放入缓存
//    3、默认行为
//        1)、如果缓存中有，方法不用调用。
//        2)、key默认自动生成：缓存的名字:: Simplekey
//        3)、缓存的value值，默认使用jdk序列化机制，将序列化后的数据存到redis
//        4)、默认ttl时间是-1，
//    自定义：
//        1)、指定生成缓存的key,key属性指定，接收一个spel表达式，
//        2)、指定缓存数据的存活时间，配置文件中修改
//        3)、将数据保存为Json格式
    @Cacheable(value = {"category"},key = "#root.method.name")
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return categoryEntities;
    }

    //    会产生堆外内存溢出:OutOfDirectMemoryError
//    1)、SpringBoot2.0以后默认使用lettuce作为操作redis的客户端，它使用netty进行网络通信
//    2)、lettuce的bug会导致堆外内存溢出，netty如果没有指定堆外内存，默认会使用-Xmx100m的值作为堆外内存
//    3)、可以通过-Dio.netty.maxDirectMemory进行设置
//    解决方案：不能使用-Dio.netty.maxDirectMemory只去调大堆外内存。
//    1)、升级lettuce客户端。
//    2)、切换使用jedis
    public Map<String, List<Catelog2Vo>> getCatalogJson2() {
//        给缓存中放json字符串，拿出的json字符串逆转为能用的对象类型；【序列化】和反序列化的过程
        /*
         * 1、空结果缓存，解决缓存穿透
         * 2、设置过期时间（加随机值），解决缓存雪崩
         * 3、加锁，解决缓存击穿问题
         * */
//        1、加入缓存逻辑，缓存中存的数据是Json字符串
//        JSON跨语言，跨平台兼容
        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
        if (StringUtils.isEmpty(catalogJSON)) {
//            2、缓存中没有，从数据库中查出来
            Map<String, List<Catelog2Vo>> catalogJsonFromDb = getCatalogJsonFromDb();
//            3、查到的数据放入缓存，将查出的对象转为Json保存
            String s = JSON.toJSONString(catalogJsonFromDb);
            redisTemplate.opsForValue().set("catalogJSON", s, 1, TimeUnit.DAYS);
            return catalogJsonFromDb;
        }
//        转为我们指定的对象
        Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {
        });
        return result;
    }
    @Cacheable(value = "category",key = "#root.methodName")
    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson(){
        List<CategoryEntity> selectList = baseMapper.selectList(null);
//        1、查出所有一级分类
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);
//        2、封装数据，查到一级分类id，再查到二级分类
        Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k ->
                k.getCatId().toString(), v -> {
            List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());
            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
//                    找当前二级分类的三级分类
                    List<CategoryEntity> level3Catelog = getParent_cid(selectList, l2.getCatId());
                    if (level3Catelog != null) {
                        List<Catelog2Vo.Catelog3Vo> collect = level3Catelog.stream().map(l3 -> {
//                        封装成指定格式
                            Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                            return catelog3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatelog3List(collect);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        return parent_cid;
    }
    //    从数据库查询并封装整个分类数据
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDb() {
        /*
         * 1、将数据库的多次查询变为一次
         * */
//        只要是同一把锁，就能锁住，需要这个锁的所有线程
//        1、synchronized(this),springboot所有的组件在容器中都是单例的
//        todo 本地锁：syncronized，JUC(Lock)，在分布式情况下，想要锁住所有，必须使用分布式锁
        List<CategoryEntity> selectList = baseMapper.selectList(null);

//        1、查出所有一级分类
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);
//        2、封装数据，查到一级分类id，再查到二级分类
        Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k ->
                k.getCatId().toString(), v -> {
            List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());
            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
//                    找当前二级分类的三级分类
                    List<CategoryEntity> level3Catelog = getParent_cid(selectList, l2.getCatId());
                    if (level3Catelog != null) {
                        List<Catelog2Vo.Catelog3Vo> collect = level3Catelog.stream().map(l3 -> {
//                        封装成指定格式
                            Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                            return catelog3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatelog3List(collect);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        return parent_cid;
    }

    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedislock() {
        String uuid = UUID.randomUUID().toString();
//        1、占分布式锁，去redis占坑，利用setnx
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid,300,TimeUnit.SECONDS);
        if (lock) {
//            加锁成功...执行业务
//            2、设置过期时间
            Map<String, List<Catelog2Vo>> dataFromDb;
            try{
                dataFromDb = getDataFromDb();
            }finally {
                //            释放锁,LUA脚本解锁
                String script="if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                        "    return redis.call(\"del\",KEYS[1])\n" +
                        "else\n" +
                        "    return 0\n" +
                        "end";
//                单机redis可直接解锁，集群模式要用到哈希标记键或者获取到jedis连接修改，https://www.cnblogs.com/cnsyear/p/12731979.html
                Integer lock1 = redisTemplate.execute(new DefaultRedisScript<Integer>(script, Integer.class), Arrays.asList("lock"), uuid);
            }
            return dataFromDb;
        } else {
//            加锁失败...重试,synchronized ()
            return getCatalogJsonFromDbWithRedislock();//自旋的方式
        }
    }

    /**
     * 缓存里面的数据如何和数据库保持一致
     * 缓存数据一致性
     * 1）、双写模式
     * 2）、失效模式
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedissonlock() {
//        1、锁的名字。锁的粒度，越细越快。
//        锁的粒度：具体缓存的是某个数据，11-号商品：product-11-lock
        RLock lock = redisson.getLock("catalogJson-lock");
        lock.lock();
        Map<String, List<Catelog2Vo>> dataFromDb;
            try{
                dataFromDb = getDataFromDb();
            }finally {
                lock.unlock();
            }
            return dataFromDb;
    }

    private Map<String, List<Catelog2Vo>> getDataFromDb() {
        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
        if (StringUtils.isEmpty(catalogJSON)) {
            Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {
            });
            return result;
        }
        List<CategoryEntity> selectList = baseMapper.selectList(null);

//        1、查出所有一级分类
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);
//        2、封装数据，查到一级分类id，再查到二级分类
        Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k ->
                k.getCatId().toString(), v -> {
            List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());
            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
//                    找当前二级分类的三级分类
                    List<CategoryEntity> level3Catelog = getParent_cid(selectList, l2.getCatId());
                    if (level3Catelog != null) {
                        List<Catelog2Vo.Catelog3Vo> collect = level3Catelog.stream().map(l3 -> {
//                        封装成指定格式
                            Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                            return catelog3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatelog3List(collect);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        String s = JSON.toJSONString(parent_cid);
        redisTemplate.opsForValue().set("catalogJSON",s,1,TimeUnit.DAYS);
        return parent_cid;
    }

    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parent_cid) {
//        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
        List<CategoryEntity> collect = selectList.stream().filter(item -> item.getParentCid().equals(parent_cid)).collect(Collectors.toList());
        return collect;
    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }

    //        递归获取当前菜单子菜单
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
        return all.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid().equals(root.getCatId()))
                .peek(categoryEntity -> {
//            找到子菜单
                    categoryEntity.setChildren(getChildren(categoryEntity, all));
                })
                .sorted(Comparator.comparingInt(menu -> (Optional.ofNullable(menu.getSort()).orElse(0))))
                .collect(Collectors.toList());
    }
}
