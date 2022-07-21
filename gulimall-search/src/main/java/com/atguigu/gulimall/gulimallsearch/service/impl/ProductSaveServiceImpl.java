package com.atguigu.gulimall.gulimallsearch.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.common.to.es.SkuEsModel;
import com.atguigu.gulimall.gulimallsearch.config.GulimallElasticSearchConfig;
import com.atguigu.gulimall.gulimallsearch.constant.EsConstant;
import com.atguigu.gulimall.gulimallsearch.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public Boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
//    保存到ES中
//        1、在ES中建立索引，product，建立好映射关系
//        2、给ES中保存数据
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel model : skuEsModels) {
//            指定索引
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
//            指定id，必须是字符串
            indexRequest.id(model.getSkuId().toString());
//            指定内容,把对象转为json,数据类型为XContentType.JSON
            String s = JSON.toJSONString(model);
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);
//        todo,如果批量错误，要处理错误
        boolean b = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(item -> item.getId()).collect(Collectors.toList());
        log.info("商品上架完成:{},返回数据:{}", collect, bulk);
        return b;
    }
}
