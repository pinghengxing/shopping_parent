package com.phx.api.feign;

import com.phx.api.service.OrderService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("order")
public interface OrderServiceFegin extends OrderService {

}
