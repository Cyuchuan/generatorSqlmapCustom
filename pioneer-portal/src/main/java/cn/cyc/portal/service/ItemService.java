package cn.cyc.portal.service;

import cn.cyc.portal.pojo.ItemInfo;

public interface ItemService {
	ItemInfo getItemByItemId(long itemId);

	String getItemDescByItemId(Long itemId);
	
	String getItemParamByItemId(Long itemId);
}
