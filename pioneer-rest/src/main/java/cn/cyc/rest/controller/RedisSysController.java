package cn.cyc.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cyc.common.util.StatusResult;
import cn.cyc.rest.service.RedisSysService;

/**
 * 缓存同步controller
 * @Description  
 * @author       陈豫川
 * @date         2018年5月15日
 */


@RequestMapping("/cache")
@Controller
public class RedisSysController {
	@Autowired
	RedisSysService redisSys;
	
	
	@RequestMapping("/syncContent/{contentCid}")
	@ResponseBody
	public StatusResult contentCacheSys(@PathVariable long contentCid){
		StatusResult result = redisSys.redisContentSys(contentCid);
		return result;
	}
	
	@RequestMapping("/syncCat/{parentId}")
	@ResponseBody
	public StatusResult catCacheSys(@PathVariable long parentId){
		StatusResult result = redisSys.redisCatSys(parentId);
		return result;
	}
}
