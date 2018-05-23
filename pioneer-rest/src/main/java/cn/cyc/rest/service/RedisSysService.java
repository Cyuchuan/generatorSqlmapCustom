package cn.cyc.rest.service;

import cn.cyc.common.util.StatusResult;

public interface RedisSysService {
	StatusResult redisContentSys(long cid);
	
	StatusResult redisCatSys(long parentId);
}
