package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.product.service.ManageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Chengxj
 * @create 2020-03-23 16:28
 */
@Api(tags = "商品sku接口")
@RestController
@RequestMapping("admin/product/")
public class SkuManageController {
    @Autowired
    private ManageService manageService;

    @GetMapping("spuImageList/{spuId}")
    public Result<List<SpuImage>> getSpuImageList(@PathVariable("spuId") Long spuId){

        List<SpuImage> spuImageList = manageService.getSpuImageList(spuId);
        return  Result.ok(spuImageList);
    }

    @GetMapping("spuSaleAttrList/{spuId}")
    public Result<List<SpuSaleAttr>> getSpuSaleAttrList(@PathVariable("spuId") Long spuId){
       List<SpuSaleAttr> spuSaleAttrList= manageService.getSpuSaleAttrList(spuId);
       return Result.ok(spuSaleAttrList);
    }

    @PostMapping("saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfo skuInfo) {
        // 调用服务层
        manageService.saveSkuInfo(skuInfo);
        return Result.ok();
    }
}
