package cn.cyc.service;

import cn.cyc.common.pojo.EUDataResult;
import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbItem;

public interface ItemService {
	EUDataResult getItemList(int page, int rows);

	StatusResult insertItem(TbItem item, String desc, String itemParam) throws Exception;
}
