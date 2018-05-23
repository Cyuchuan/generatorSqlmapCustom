package cn.cyc.service;

import cn.cyc.common.pojo.EUDataResult;
import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbItemParam;

public interface ItemParaService {
	StatusResult getItemParamByCid(long cid);

	EUDataResult getItemParamList(int page, int rows);
	
	StatusResult insertItemParam(TbItemParam itemParam);
}
