package com.atguigu.gulimall.product;

import com.atguigu.gulimall.product.dao.AttrGroupDao;
import com.atguigu.gulimall.product.dao.SkuSaleAttrValueDao;
import com.atguigu.gulimall.product.vo.SkuItemSaleAttrVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

@Slf4j
@SpringBootTest
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class GulimallProductApplicationTests {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedissonClient redissonClient;
//    @Resource
//    BrandService brandService;
//    @Resource
//    private Storage storage;
//    @Resource
//    private CategoryService categoryService;
//
//    @Value("${cloud.storage.bucketName}")
//    String bucketName;
//
//    @Test
//    public void contextLoads() {
//        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
//        list.forEach((System.out::println));
//    }
//
//
//    @Test
//    public void upload2() throws FileNotFoundException {
//        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
//        String endpoint = "endpoint";
//// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
//        String accessKeyId = "accessKeyId";
//        String accessKeySecret = "accessKeySecret";
//
//// 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//// 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
//        InputStream inputStream = new FileInputStream("");
//// 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
//        ossClient.putObject("gulimall-cloudstorage", "exampleobject.png", inputStream);
//
//// 关闭OSSClient。
//        ossClient.shutdown();
//    }
//
//    @Test
//    public void name() {
//        Long[] categoryPath = categoryService.findCategoryPath(225L);
//        log.info("完整路径：{}", Arrays.asList(categoryPath));
//    }
    @Autowired
    AttrGroupDao attrGroupDao;
    @Autowired
    SkuSaleAttrValueDao skuSaleAttrValueDao;
    @Test
    public void test(){
//        List<SpuItemAttrGroupVo> attrGroupWithAttrsBySpuId = attrGroupDao.getAttrGroupWithAttrsBySpuId(23L, 225L);
//        System.out.println(attrGroupWithAttrsBySpuId);
        List<SkuItemSaleAttrVo> saleAttrsBySpuId = skuSaleAttrValueDao.getSaleAttrsBySpuId(23L);
        System.out.println(saleAttrsBySpuId);
    }
    @Test
    public void name() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //        保存
        ops.set("hello","world_"+ UUID.randomUUID().toString());
        //        查询
        String hello = ops.get("hello");
        System.out.println("之前保存的数据是："+hello);
    }

    @Test
    public void name1() {
        System.out.println(redissonClient);
    }
}
