package cn.cyc.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.cyc.common.util.CookieUtils;
import cn.cyc.pojo.TbUser;
import cn.cyc.portal.service.imp.UserServiceImp;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	UserServiceImp userServiceImp;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// 在Handler执行之前处理
		// 判断用户是否登录
		// 从cookie中取token
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		// 根据token换取用户信息，调用sso系统的接口。
		TbUser user = userServiceImp.getUserByToken(token);
		// 取不到用户信息
		if (null == user) {
			// 跳转到登录页面，把用户请求的url作为参数传递给登录页面。
			response.sendRedirect(
					userServiceImp.SSO_URL + userServiceImp.SSO_USER_SHOWLOGIN + "?redirect=" + request.getRequestURL());
			// 返回false
			return false;
		}
		// 取到用户信息，放行
		// 返回值决定handler是否执行。true：执行，false：不执行。
		return true;

	}

}
