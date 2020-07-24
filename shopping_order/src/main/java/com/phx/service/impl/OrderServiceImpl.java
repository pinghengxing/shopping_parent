package com.phx.service.impl;

import com.phx.api.service.OrderService;
import com.phx.base.BaseController;
import com.phx.base.ResponseBase;
import com.phx.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderServiceImpl extends BaseController implements OrderService {
	@Autowired
	private OrderDao orderDao;

	@Override
	public ResponseBase updateOrderIdInfo(@RequestParam("isPay") Long isPay, @RequestParam("payId") String aliPayId,
                                          @RequestParam("orderNumber") String orderNumber) {
		int updateOrder = orderDao.updateOrder(isPay, aliPayId, orderNumber);
		if (updateOrder <= 0) {
			return setResultError("系统错误!");
		}
		return setResultSuccess();
	}

}
