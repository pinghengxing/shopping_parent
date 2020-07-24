package com.phx.api.service;

import com.phx.base.ResponseBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequestMapping("/callBack")
public interface PayCallBackService {
	// // 同步回调
	@RequestMapping("/synCallBackService")
	public ResponseBase synCallBack(@RequestParam Map<String, String> params);

	// // 异步回调
	@RequestMapping("/asynCallBackService")
	public String asynCallBack(@RequestParam Map<String, String> params);
}
