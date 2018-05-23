package cn.cyc.rest.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.cyc.common.util.JsonUtils;
import cn.cyc.mapper.TbItemCatMapper;
import cn.cyc.pojo.TbItemCat;
import cn.cyc.pojo.TbItemCatExample;
import cn.cyc.pojo.TbItemCatExample.Criteria;
import cn.cyc.rest.dao.JedisClient;
import cn.cyc.rest.pojo.CatData;
import cn.cyc.rest.pojo.CatNode;
import cn.cyc.rest.service.ItemCatService;

/**
 * 
 * @Description 显示首页中左侧的菜单栏
 * @author 陈豫川
 * @date 2018年5月13日
 */
@Service
public class ItemCatServiceImp implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${ITEM_CATEGORY}")
	private String ITEM_CATEGORY;

	@Override
	public CatData getItemCatListService() {
		CatData catData = new CatData();
		catData.setData(getItemCatListByParentId(0));
		return catData;
	}

	private List<?> getItemCatListByParentId(long parentId) {
		// 先从redis缓存中提出数据
		try {
			String hget = jedisClient.hget(ITEM_CATEGORY, String.valueOf(parentId));
			if (!StringUtils.isBlank(hget)) {
				List<Object> jsonToList = JsonUtils.jsonToList(hget, Object.class);
				return jsonToList;
			}
		} catch (Exception e) {
			// 提取redis缓存过程出现异常处理
			e.printStackTrace();
		}
		// 分类业务处理
		List ResultList = new ArrayList<>(32);
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> listByParentId = itemCatMapper.selectByExample(example);
		int count = 0;
		for (TbItemCat tbItemCat : listByParentId) {
			count++;
			if (tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (tbItemCat.getParentId() == 0) {
					catNode.setName(
							"<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
				} else {
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
				catNode.setItem(getItemCatListByParentId(tbItemCat.getId()));
				ResultList.add(catNode);
				if (tbItemCat.getParentId() == 0 && count >= 14) {
					break;
				}
			} else {
				ResultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
			}
		}
		// 将查询出来的值加入redis缓存中
		try {
			if (parentId == 0 ) {
				String value = JsonUtils.objectToJson(ResultList);
				jedisClient.hset(ITEM_CATEGORY, String.valueOf(parentId), value);
			}
		} catch (Exception e) {
			// 加入缓存异常时处理
			e.printStackTrace();
		}

		return ResultList;
	}

}
