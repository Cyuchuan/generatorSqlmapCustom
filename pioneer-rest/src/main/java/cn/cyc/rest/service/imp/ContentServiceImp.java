package cn.cyc.rest.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;

import cn.cyc.common.util.JsonUtils;
import cn.cyc.mapper.TbContentMapper;
import cn.cyc.pojo.TbContent;
import cn.cyc.pojo.TbContentExample;
import cn.cyc.pojo.TbContentExample.Criteria;
import cn.cyc.rest.dao.JedisClient;
import cn.cyc.rest.service.ContentService;

/**
 * 
 * @Description rest中大广告内容展示
 * @author 陈豫川
 * @date 2018年5月13日
 */
@Service
public class ContentServiceImp implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${BIG_CONTENT_KEY}")
	private String BIG_CONTENT_KEY;

	@Override
	public List<TbContent> getContentList(long contentCid) {
		// 加入缓存不影响正常业务
		try {
			String string = jedisClient.hget(BIG_CONTENT_KEY, String.valueOf(contentCid));
			if(!StringUtils.isBlank(string)){
				List<TbContent> jsonToList = JsonUtils.jsonToList(string, TbContent.class);
				return jsonToList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCid);
		List<TbContent> list = contentMapper.selectByExample(example);
		// 将查询出来的大广告信息加入缓存
		try {
			String value = JsonUtils.objectToJson(list);
			jedisClient.hset(BIG_CONTENT_KEY, String.valueOf(contentCid), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
