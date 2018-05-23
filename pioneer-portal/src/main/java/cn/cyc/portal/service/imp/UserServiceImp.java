package cn.cyc.portal.service.imp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.cyc.common.util.CookieUtils;
import cn.cyc.common.util.HttpClientUtil;
import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbUser;
import cn.cyc.portal.service.UserService;

@Service
public class UserServiceImp implements UserService {

	@Value("${SSO_URL}")
	public String SSO_URL;

	@Value("${SSO_USER_LOGOUT}")
	private String SSO_USER_LOGOUT;

	@Value("${SSO_USER_TOKEN}")
	private String SSO_USER_TOKEN;

	@Value("${SSO_USER_SHOWLOGIN}")
	public String SSO_USER_SHOWLOGIN;

	@Override
	public StatusResult logout(String token, HttpServletRequest request, HttpServletResponse response) {
		try {
			String doGet = HttpClientUtil.doGet(SSO_URL + SSO_USER_LOGOUT + token);
			CookieUtils.setCookie(request, response, "TT_TOKEN", null, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StatusResult.ok();
	}

	@Override
	public TbUser getUserByToken(String token) {
		try {
			// 调用sso系统的服务，根据token取用户信息
			String json = HttpClientUtil.doGet(SSO_URL + SSO_USER_TOKEN + token);
			// 把json转换成TaotaoREsult
			StatusResult result = StatusResult.formatToPojo(json, TbUser.class);
			if (result.getStatus() == 200) {
				TbUser user = (TbUser) result.getData();
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
