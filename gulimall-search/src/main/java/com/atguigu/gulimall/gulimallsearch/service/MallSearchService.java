package com.atguigu.gulimall.gulimallsearch.service;

import com.atguigu.gulimall.gulimallsearch.vo.SearchParam;
import com.atguigu.gulimall.gulimallsearch.vo.SearchResult;

public interface MallSearchService {

    /**
     * @param param 检索的所有参数
     * @return 返回检索的结果,里面包含页面的所有信息
     */
    SearchResult search(SearchParam param);
}
