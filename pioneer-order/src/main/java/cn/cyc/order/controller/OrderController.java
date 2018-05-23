package cn.cyc.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cyc.common.util.ExceptionUtil;
import cn.cyc.common.util.StatusResult;
import cn.cyc.order.pojo.Order;
import cn.cyc.order.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/create")
	@ResponseBody
	public StatusResult createOrder(@RequestBody Order order) {
		try {
			StatusResult result = orderService.insertOrder(order, order.getOrderItems(), order.getOrderShipping());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return StatusResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
