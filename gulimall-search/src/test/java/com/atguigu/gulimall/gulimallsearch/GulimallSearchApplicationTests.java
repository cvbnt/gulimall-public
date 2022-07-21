package com.atguigu.gulimall.gulimallsearch;

import com.alibaba.fastjson.JSON;
import com.atguigu.gulimall.gulimallsearch.config.GulimallElasticSearchConfig;
import lombok.Data;
import lombok.ToString;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallSearchApplicationTests {
    @Autowired
    private RestHighLevelClient client;

    @Test
    public void contextLoads() {
        System.out.println(client);
    }

    /*
     * 测试es存储数据
     * */
    @Test
    public void indexData() throws IOException {
//        操作的索引
        IndexRequest request = new IndexRequest("users");
//        设置id
        request.id("1");
//        request.source("userName","zhangsan","age",18,"gender","男");
        User user = new User();
        user.setUserName("zhangsan");
        user.setAge(18);
        user.setGender("男");
        String jsonString = JSON.toJSONString(user);
//        设置传递类型为json
        request.source(jsonString, XContentType.JSON);
//        执行操作
        IndexResponse index = client.index(request, GulimallElasticSearchConfig.COMMON_OPTIONS);
//        提取有用的响应数据
        System.out.println(index);
    }

    @Data
    class User {
        private String userName;
        private String gender;
        private int age;
    }

    @Test
    public void searchData() throws IOException {
//        1、创建检索请求
        SearchRequest searchRequest = new SearchRequest();
//        指定索引
        searchRequest.indices("bank");
//        指定DSL,检索条件
//        SearchSourceBuilder sourceBuilder
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        查找地址含mile的
        sourceBuilder.query(QueryBuilders.matchQuery("address", "mill"));
//        聚合搜索结果，按照年龄值分布进行聚合
        TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("age").size(10);
//        按照平均薪资进行聚合
        sourceBuilder.aggregation(ageAgg);
        AvgAggregationBuilder balanceAvg = AggregationBuilders.avg("balanceAvg").field("balance");
        sourceBuilder.aggregation(balanceAvg);
        searchRequest.source(sourceBuilder);
//        2、执行检索
        SearchResponse searchResponse = client.search(searchRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);
//        3、分析结果,获取时间状态码命中记录之类
//        获取所有查到的数据
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
//            获取命中结果json字符串
            String string = hit.getSourceAsString();
            Account account = JSON.parseObject(string, Account.class);
            System.out.println("account: " + account);
        }
//        获取到检索得到的分析信息
        Aggregations aggregations = searchResponse.getAggregations();
//        将聚合结果转成list，并遍历
        for (Aggregation aggregation : aggregations.asList()) {
//            当前聚合的名字
            System.out.println("当前聚合的名字："+aggregation.getName());
        }
        Terms ageAgg1 = aggregations.get("ageAgg");
        for (Terms.Bucket bucket : ageAgg1.getBuckets()) {
//            获取聚合内的bucket值,年龄的个数
            String keyAsString = bucket.getKeyAsString();
            System.out.println("年龄："+keyAsString+"==>"+bucket.getDocCount());
        }
        Avg balanceAvg1 = aggregations.get("balanceAvg");
        System.out.println("平均薪资："+balanceAvg1.getValue());
    }
    /**
     * Copyright 2021 json.cn
     */
    /**
     * Auto-generated: 2021-08-15 22:15:42
     *
     * @author json.cn (i@json.cn)
     * @website http://www.json.cn/java2pojo/
     */
    @Data
    @ToString
    static class Account {

        private int account_number;
        private int balance;
        private String firstname;
        private String lastname;
        private int age;
        private String gender;
        private String address;
        private String employer;
        private String email;
        private String city;
        private String state;

    }
}
