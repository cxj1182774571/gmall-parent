package com.atguigu.gmall.item.client;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.item.client.impl.ItemDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Chengxj
 * @create 2020-03-28 17:41
 */
@FeignClient(name = "service-item1" ,fallback= ItemDegradeFeignClient.class )
public interface ItemFeignClient {
    //把service-item1 中的服务暴露处理
    @GetMapping("/api/item/{skuId}")
    Result getItem(@PathVariable("skuId") Long skuId);
}
