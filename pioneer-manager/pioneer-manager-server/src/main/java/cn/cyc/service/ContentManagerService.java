package cn.cyc.service;

import cn.cyc.common.pojo.EUDataResult;
import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbContent;

public interface ContentManagerService {
	EUDataResult getContentListByCatId(int page, int rows, long categoryId);
	
	StatusResult saveContent(TbContent content);
	
	StatusResult editContent(TbContent content);
	
	StatusResult deleteContentByCid(long ids);
}
