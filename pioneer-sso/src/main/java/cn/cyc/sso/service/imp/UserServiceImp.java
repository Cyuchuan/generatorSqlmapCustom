package cn.cyc.sso.service.imp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.cyc.common.util.CookieUtils;
import cn.cyc.common.util.JsonUtils;
import cn.cyc.common.util.StatusResult;
import cn.cyc.mapper.TbOrderMapper;
import cn.cyc.mapper.TbUserMapper;
import cn.cyc.pojo.TbOrder;
import cn.cyc.pojo.TbUser;
import cn.cyc.pojo.TbUserExample;
import cn.cyc.pojo.TbUserExample.Criteria;
import cn.cyc.sso.dao.JedisClient;
import cn.cyc.sso.service.UserService;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Autowired
	private TbOrderMapper orderMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${USER_SESSION}")
	private String USER_SESSION;

	@Value("${SESSION_EXPIRE_TIME}")
	private String SESSION_EXPIRE_TIME;

	@Override
	public StatusResult checkData(String content, Integer type) {
		// 创建查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 对数据进行校验：1、2、3分别代表username、phone、email
		// 用户名校验
		if (1 == type) {
			criteria.andUsernameEqualTo(content);
			// 电话校验
		} else if (2 == type) {
			criteria.andPhoneEqualTo(content);
			// email校验
		} else {
			criteria.andEmailEqualTo(content);
		}
		// 执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return StatusResult.ok(true);
		}
		return StatusResult.ok(false);
	}

	@Override
	public StatusResult insertUser(TbUser user) {
		Date date = new Date();
		user.setUpdated(date);
		user.setCreated(date);
		// md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return StatusResult.ok();
	}

	@Override
	public StatusResult userLogin(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {

		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		// 如果没有此用户名
		if (null == list || list.size() == 0) {
			return StatusResult.build(400, "用户名或密码错误");
		}
		TbUser user = list.get(0);
		// 比对密码
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return StatusResult.build(400, "用户名或密码错误");
		}
		// 生成token
		String token = UUID.randomUUID().toString();
		// 保存用户之前，把用户对象中的密码清空。
		user.setPassword(null);
		// 把用户信息写入redis
		jedisClient.set(USER_SESSION + ":" + token, JsonUtils.objectToJson(user));
		// 设置session的过期时间
		jedisClient.expire(USER_SESSION + ":" + token, Integer.parseInt(SESSION_EXPIRE_TIME));
		// 将token写入cookies
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		// 返回token
		return StatusResult.ok(token);
	}

	@Override
	public StatusResult getUserByToken(String token) {

		// 根据token从redis中查询用户信息
		String json = jedisClient.get(USER_SESSION + ":" + token);
		// 判断是否为空
		if (StringUtils.isBlank(json)) {
			return StatusResult.build(400, "此session已经过期，请重新登录");
		}
		// 更新过期时间
		jedisClient.expire(USER_SESSION + ":" + token, Integer.parseInt(SESSION_EXPIRE_TIME));
		// 返回用户信息
		return StatusResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
	}

	@Override
	public StatusResult userlogout(String token) {
		jedisClient.del(USER_SESSION + ":" + token);
		return StatusResult.ok();
	}
}