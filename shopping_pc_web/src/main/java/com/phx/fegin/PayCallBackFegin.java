package com.phx.fegin;

import com.phx.api.service.PayCallBackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient("pay")
public interface PayCallBackFegin extends PayCallBackService {

}
