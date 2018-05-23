package cn.cyc.service;

import java.util.List;

import cn.cyc.common.pojo.EUTreeNode;

public interface ItemCatService {
	
	List<EUTreeNode> getItemCatList(long parentId) throws Exception;
	
}
