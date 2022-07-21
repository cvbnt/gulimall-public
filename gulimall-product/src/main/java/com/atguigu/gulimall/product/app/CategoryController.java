package com.atguigu.gulimall.product.app;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


/**
 * 商品三级分类
 *
 * @author cvbnt
 * @email 5465497@gmail.com
 * @date 2021-05-24 10:44:02
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {

//    @Value("${cloud.storage.bucketName}")
//    String bucketName;
//    @Value("${cloud.storage.projectId}")
//    String projectId;
//    @Resource
//    Storage storage;
    @Autowired
    private CategoryService categoryService;

    /**
     * 查出所有分类以及子分类,以树形结构组装起来
     */
    @RequestMapping("/list/tree")
//    @RequiresPermissions("product:category:list")
    public R list() {
        List<CategoryEntity> entries = categoryService.listWithtree();
        return R.ok().put("data", entries);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
//    @RequiresPermissions("product:category:info")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("product:category:save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.save(category);

        return R.ok();
    }

    /**
     * 批量修改
     */
    @RequestMapping("/update/sort")
//    @RequiresPermissions("product:category:update")
    public R updateSort(@RequestBody CategoryEntity[] category) {
        categoryService.updateBatchById(Arrays.asList(category));
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("product:category:update")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateCascade(category);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("product:category:delete")
    public R delete(@RequestBody Long[] catIds) {
//        1、检查当前删除的菜单是否被别的菜单引用
        categoryService.removeByIds(Arrays.asList(catIds));
        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }

//    @RequestMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//        public String uploadFile(MultipartFile multipartFile) throws IOException {
//        String fileName = multipartFile.getOriginalFilename();
//        BlobId blobId = BlobId.of(bucketName, fileName);
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//        Blob blob = storage.create(blobInfo, multipartFile.getBytes());
//        return blob.getMediaLink();
//    }
}
