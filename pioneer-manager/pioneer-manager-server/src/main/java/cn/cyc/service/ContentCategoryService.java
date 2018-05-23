package cn.cyc.service;

import java.util.List;

import cn.cyc.common.pojo.EUTreeNode;
import cn.cyc.common.util.StatusResult;

public interface ContentCategoryService {
	List<EUTreeNode> getContentCategory(long parentId);
	
	StatusResult insertContentCategory(long parentId,String name) throws Exception;
	
	StatusResult deleteContentCategory(long id) throws Exception;
	
	StatusResult updateContentCategoryName(long id,String name);
}
