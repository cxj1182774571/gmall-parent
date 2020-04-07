package com.atguigu.gmall.all.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Chengxj
 * @create 2020-04-01 17:08
 */
@Controller
@RequestMapping
public class IndexController {
    @Autowired
    private ProductFeignClient productFeignClient;

    //利用模板引擎自动生成页面，可以将页面放入nginx中
    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    //直接写一个控制器，走缓存的模式
    @GetMapping("/createHtml")
    @ResponseBody
    public Result createHtml() throws IOException {
        Result result = productFeignClient.getBaseCategoryList();
        Context context = new Context();
        context.setVariable("list", result.getData());
        FileWriter write = new FileWriter("C:\\index.html");
        springTemplateEngine.process("index/index.html", context, write);
        return Result.ok();
    }

    /**
     * 首页 方式二：分类数据缓存，动态渲染
     * @param request
     * @return
     */
    @GetMapping({"/", "index.html"})
    public String index(HttpServletRequest request) {
        Result result = productFeignClient.getBaseCategoryList();
        request.setAttribute("list", result.getData());
        return "index/index";
    }


}
