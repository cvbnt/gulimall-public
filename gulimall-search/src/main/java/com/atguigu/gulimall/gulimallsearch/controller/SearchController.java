package com.atguigu.gulimall.gulimallsearch.controller;

import com.atguigu.gulimall.gulimallsearch.service.MallSearchService;
import com.atguigu.gulimall.gulimallsearch.vo.SearchParam;
import com.atguigu.gulimall.gulimallsearch.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SearchController {
    @Autowired
    MallSearchService mallSearchService;

    /**
     * 自动将页面提交过来的所有查询请求参数封装成指定的对象
     * @param param
     * @return
     */
    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request){
//        获取请求查询字符串
        param.set_queryString(request.getQueryString());
//        根据传递过来的页面的查询参数,去es中检索商品
        SearchResult result = mallSearchService.search(param);
        model.addAttribute("result",result);
        return "list";
    }
}
