package cn.cyc.portal.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.cyc.common.util.HttpClientUtil;
import cn.cyc.common.util.JsonUtils;
import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbItemDesc;
import cn.cyc.pojo.TbItemParamItem;
import cn.cyc.portal.pojo.ItemInfo;
import cn.cyc.portal.service.ItemService;

/**
 * 调用rest服务中的服务，获取商品的信息
 * 
 * @Description
 * @author 陈豫川
 * @date 2018年5月17日
 */
@Service
public class ItemServiceImp implements ItemService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Value("${ITEM_BASE_INFO}")
	private String ITEM_BASE_INFO;

	@Value("${ITEM_DESC}")
	private String ITEM_DESC;

	@Value("${ITEM_PARAM}")
	private String ITEM_PARAM;

	@Override
	public ItemInfo getItemByItemId(long itemId) {
		String url = REST_BASE_URL + ITEM_BASE_INFO + itemId;
		try {
			String json = HttpClientUtil.doGet(url);
			StatusResult resultStatus = StatusResult.formatToPojo(json, ItemInfo.class);
			if (resultStatus.getStatus() == 200) {
				ItemInfo data = (ItemInfo) resultStatus.getData();
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getItemDescByItemId(Long itemId) {
		try {
			// 查询商品描述
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC + itemId);
			// 转换成java对象
			StatusResult statusResult = StatusResult.formatToPojo(json, TbItemDesc.class);
			if (statusResult.getStatus() == 200) {
				TbItemDesc itemDesc = (TbItemDesc) statusResult.getData();
				// 取商品描述信息
				String result = itemDesc.getItemDesc();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getItemParamByItemId(Long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM + itemId);
			// 把json转换成java对象
			StatusResult statusResult = StatusResult.formatToPojo(json, TbItemParamItem.class);
			if (statusResult.getStatus() == 200) {
				TbItemParamItem itemParamItem = (TbItemParamItem) statusResult.getData();
				String paramData = itemParamItem.getParamData();
				// 生成html
				// 把规格参数json数据转换成java对象
				List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
				StringBuffer sb = new StringBuffer();
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
				sb.append("    <tbody>\n");
				for (Map m1 : jsonList) {
					sb.append("        <tr>\n");
					sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group") + "</th>\n");
					sb.append("        </tr>\n");
					List<Map> list2 = (List<Map>) m1.get("params");
					for (Map m2 : list2) {
						sb.append("        <tr>\n");
						sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
						sb.append("            <td>" + m2.get("v") + "</td>\n");
						sb.append("        </tr>\n");
					}
				}
				sb.append("    </tbody>\n");
				sb.append("</table>");
				// 返回html片段
				return sb.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
}
