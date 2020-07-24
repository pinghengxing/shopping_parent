package com.phx.fegin;

import com.phx.api.service.PayService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("pay")
public interface PayServiceFegin extends PayService {

}
