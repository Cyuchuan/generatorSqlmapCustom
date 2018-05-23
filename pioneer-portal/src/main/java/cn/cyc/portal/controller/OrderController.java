package cn.cyc.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.RequiredTypes;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.cyc.pojo.TbOrderItem;
import cn.cyc.portal.pojo.CartItem;
import cn.cyc.portal.pojo.Order;
import cn.cyc.portal.service.CartService;
import cn.cyc.portal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	CartService cartService;

	@Autowired
	OrderService orderService;

	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 取购物车商品列表
		List<CartItem> list = cartService.getCartItemList(request, response);
		// 传递给页面
		model.addAttribute("cartList", list);

		return "order-cart";
	}

	@RequestMapping("/success")
	public String showSuccess(String orderId, String payment, String date, Model model) {
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", payment);
		model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
		return "success";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createOrder(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
		String orderId = orderService.insertOrder(order);
		List<TbOrderItem> orderItems = order.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			String itemId = tbOrderItem.getItemId();
			cartService.deleteCartItem(Long.parseLong(itemId), request, response);
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", order.getPayment());
		model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
		return "redirect:/order/success.html";
	}

}
