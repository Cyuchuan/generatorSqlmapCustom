package cn.cyc.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cyc.common.util.StatusResult;
import cn.cyc.portal.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping("/logout/{token}")
	public String userLogout(@PathVariable String token, HttpServletRequest request, HttpServletResponse response) {
		StatusResult logout = userService.logout(token, request, response);
		return "redirect:/index.html";
	}
	
	@RequestMapping("/info")
	public String showMyInfo(){
		return "my-info";
	}
	
	@RequestMapping("/userinfo/showImg")
	public String showMyInfoImg(){
		return "my-info-img";
	}
	
	@RequestMapping("/userinfo/more")
	public String showMyInfoMore(){
		return "my-info-more";
	}
	
	@RequestMapping("/orders/comment")
	public String showMyOrderComment(){
		return "my-order-comment";
	}
	
	@RequestMapping("/orders")
	public String showMyOrders(){
		return "my-orders";
	}
}
