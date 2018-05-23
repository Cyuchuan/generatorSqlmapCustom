package cn.cyc.portal.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.cyc.common.util.HttpClientUtil;
import cn.cyc.common.util.JsonUtils;
import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbContent;
import cn.cyc.portal.pojo.ADItem;
import cn.cyc.portal.service.ContentService;

/**
 * 调用rest服务，返回大广告位的json数据
 * 
 * @Description
 * @author 陈豫川
 * @date 2018年5月14日
 */
@Service
public class ContentServiceImp implements ContentService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Value("${INDEX_AD1_URL}")
	private String INDEX_AD1_URL;

	@Override
	public String getAdItemList() {
		try {
			// 调用服务层的服务查询大广告位的数据
			String result = HttpClientUtil.doGet(REST_BASE_URL + INDEX_AD1_URL);
			// 把json数据转换成对象
			StatusResult statusResult = StatusResult.formatToList(result, TbContent.class);
			List<ADItem> itemList = new ArrayList<>();
			if (statusResult.getStatus() == 200) {
				List<TbContent> contentList = (List<TbContent>) statusResult.getData();
				for (TbContent tbContent : contentList) {
					ADItem item = new ADItem();
					item.setHeight(240);
					item.setWidth(670);
					item.setSrc(tbContent.getPic());
					item.setHeightB(240);
					item.setWidthB(550);
					item.setSrcB(tbContent.getPic2());
					item.setAlt(tbContent.getTitleDesc());
					item.setHref(tbContent.getUrl());
					itemList.add(item);
				}
			}
			return JsonUtils.objectToJson(itemList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
