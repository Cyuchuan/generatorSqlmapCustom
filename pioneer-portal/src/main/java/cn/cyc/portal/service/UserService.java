package cn.cyc.portal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbUser;

public interface UserService {
	StatusResult logout(String token,HttpServletRequest request,HttpServletResponse response);
	
	TbUser getUserByToken(String token);
}
