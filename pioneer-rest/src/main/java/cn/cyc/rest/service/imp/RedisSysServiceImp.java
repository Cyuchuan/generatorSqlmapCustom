package cn.cyc.rest.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.cyc.common.util.ExceptionUtil;
import cn.cyc.common.util.StatusResult;
import cn.cyc.rest.dao.JedisClient;
import cn.cyc.rest.service.RedisSysService;

/**
 * 在后台管理中添加了新广告内容或编辑、删除时调用redis同步服务
 * 
 * @Description
 * @author 陈豫川
 * @date 2018年5月15日
 */
@Service
public class RedisSysServiceImp implements RedisSysService {

	@Value("${BIG_CONTENT_KEY}")
	private String BIG_CONTENT_KEY;

	@Value("${ITEM_CATEGORY}")
	private String ITEM_CATEGORY;
	@Autowired
	private JedisClient jedisClient;

	@Override
	public StatusResult redisContentSys(long cid) {
		try {
			jedisClient.hdel(BIG_CONTENT_KEY, String.valueOf(cid));
		} catch (Exception e) {
			e.printStackTrace();
			return StatusResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return StatusResult.ok();
	}

	@Override
	public StatusResult redisCatSys(long parentId) {
		try{
			jedisClient.hdel(ITEM_CATEGORY, String.valueOf(parentId));
		}catch (Exception e) {
			e.printStackTrace();
			return StatusResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return StatusResult.ok();
	}

}
