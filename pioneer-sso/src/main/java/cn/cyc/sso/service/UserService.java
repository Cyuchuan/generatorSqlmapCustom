package cn.cyc.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbUser;

public interface UserService {
	StatusResult checkData(String content, Integer type);

	StatusResult insertUser(TbUser user);

	StatusResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response);

	StatusResult getUserByToken(String token);

	StatusResult userlogout(String token);
}
