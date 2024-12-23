package com.bpi.feign;

import com.bpi.configuration.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "coindeskFeign", url = "${api.coindesk.url}", configuration = FeignConfig.class)
public interface CoindeskFeign {

    @GetMapping("/v1/bpi/currentprice.json")
    String getCurrentPrice();

}
