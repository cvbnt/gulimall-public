package com.atguigu.gulimall.gulimallsearch.service;

import com.atguigu.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface ProductSaveService {

    Boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
