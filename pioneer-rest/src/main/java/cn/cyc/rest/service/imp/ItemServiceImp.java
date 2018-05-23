package cn.cyc.rest.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.cyc.common.util.JsonUtils;
import cn.cyc.common.util.StatusResult;
import cn.cyc.mapper.TbItemDescMapper;
import cn.cyc.mapper.TbItemMapper;
import cn.cyc.mapper.TbItemParamItemMapper;
import cn.cyc.pojo.TbItem;
import cn.cyc.pojo.TbItemDesc;
import cn.cyc.pojo.TbItemParamItem;
import cn.cyc.pojo.TbItemParamItemExample;
import cn.cyc.pojo.TbItemParamItemExample.Criteria;
import cn.cyc.rest.dao.JedisClient;
import cn.cyc.rest.service.ItemService;

@Service
public class ItemServiceImp implements ItemService {

	@Autowired
	TbItemMapper itemMapper;

	@Autowired
	TbItemDescMapper descMapper;

	@Autowired
	TbItemParamItemMapper itemParamItemMapper;

	@Autowired
	JedisClient jedisClient;

	@Value("${ITEM_ALL_INFO}")
	String ITEM_ALL_INFO;

	@Value("${ITEM_EXPIRE}")
	String ITEM_EXPIRE;

	@Override
	public StatusResult getItemBaseInfo(long itemId) {
		// 在redis中key的存储方式:"ITEM_ALL_INFO:商品id:(base|desc|param)":"value(json)"
		String key = ITEM_ALL_INFO + ":" + itemId + ":base";
		// 从缓存中获得商品基本信息
		try {
			String json = jedisClient.get(key);
			if (!StringUtils.isBlank(json)) {
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				jedisClient.expire(key, Integer.parseInt(ITEM_EXPIRE));
				return StatusResult.ok(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查询一个商品信息的业务
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		// 插入缓存，缓存不能影响正常的业务
		try {
			jedisClient.set(key, JsonUtils.objectToJson(item));
			jedisClient.expire(key, Integer.parseInt(ITEM_EXPIRE));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return StatusResult.ok(item);
	}

	/**
	 * 
	 * @Description
	 * @param itemId
	 * @return
	 * @see cn.cyc.rest.service.ItemService#getItemBaseDesc(long)
	 */
	@Override
	public StatusResult getItemBaseDesc(long itemId) {
		String key = ITEM_ALL_INFO + ":" + itemId + ":desc";
		// 从缓存中获得商品描述
		try {
			String json = jedisClient.get(key);
			if (!StringUtils.isBlank(json)) {
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				jedisClient.expire(key, Integer.parseInt(ITEM_EXPIRE));
				return StatusResult.ok(itemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查询商品描述
		TbItemDesc itemDesc = descMapper.selectByPrimaryKey(itemId);
		// 插入缓存，缓存不能影响正常的业务
		try {
			jedisClient.set(key, JsonUtils.objectToJson(itemDesc));
			jedisClient.expire(key, Integer.parseInt(ITEM_EXPIRE));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return StatusResult.ok(itemDesc);
	}

	@Override
	public StatusResult getItemBaseParam(long itemId) {
		String key = ITEM_ALL_INFO + ":" + itemId + ":param";
		// 从缓存中获得商品规格项详情
		try {
			String json = jedisClient.get(key);
			if (!StringUtils.isBlank(json)) {
				TbItemParamItem item = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				jedisClient.expire(key, Integer.parseInt(ITEM_EXPIRE));
				return StatusResult.ok(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemParamItem tbItemParamItem = null;
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> selectByExample = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (selectByExample != null && selectByExample.size() > 0) {
			tbItemParamItem = selectByExample.get(0);
		}

		// 插入缓存，缓存不能影响正常的业务
		try {
			jedisClient.set(key, JsonUtils.objectToJson(tbItemParamItem));
			jedisClient.expire(key, Integer.parseInt(ITEM_EXPIRE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StatusResult.ok(tbItemParamItem);
	}

}
