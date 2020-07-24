package com.phx.fegin;

import com.phx.api.service.MemberService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("member")
public interface MemberServiceFegin extends MemberService {

}
