package cn.cyc.sso.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.xml.internal.rngom.util.Uri;

import cn.cyc.common.util.ExceptionUtil;
import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbUser;
import cn.cyc.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {

		StatusResult result = null;

		// 参数有效性校验
		if (StringUtils.isBlank(param)) {
			result = StatusResult.build(400, "校验内容不能为空");
		}
		if (type == null) {
			result = StatusResult.build(400, "校验内容类型不能为空");
		}
		if (type != 1 && type != 2 && type != 3) {
			result = StatusResult.build(400, "校验内容类型错误");
		}
		// 校验出错
		if (null != result) {
			if (null != callback) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			} else {
				return result;
			}
		}
		// 调用服务
		try {
			result = userService.checkData(param, type);

		} catch (Exception e) {
			result = StatusResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		if (null != callback) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return result;
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public StatusResult createUser(TbUser user) {
		try {
			StatusResult result = userService.insertUser(user);
			return result;
		} catch (Exception e) {
			return StatusResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	// 用户登录
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public StatusResult userLogin(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			StatusResult result = userService.userLogin(username, password, request, response);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return StatusResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		StatusResult result = null;
		try {
			result = userService.getUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = StatusResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		// 判断是否为jsonp调用
		if (StringUtils.isBlank(callback)) {
			return result;
		} else {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
	}

	@RequestMapping("/logout/{token}")
	@ResponseBody
	public Object logoutUserByToken(@PathVariable String token, String callback) {
		StatusResult result = null;
		try {
			result = userService.userlogout(token);
		} catch (Exception e) {
			result = StatusResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		if (StringUtils.isBlank(callback)) {
			return result;
		} else {
			MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
			jacksonValue.setJsonpFunction(callback);
			return jacksonValue;
		}
	}

	@RequestMapping("/showRegister")
	public String showRegister() {
		return "register";
	}

	@RequestMapping("/showLogin")
	public String showLogin(String redirect, Model model) {
		model.addAttribute("redirect", redirect);
		return "login";
	}
}
