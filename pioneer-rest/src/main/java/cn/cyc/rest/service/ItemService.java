package cn.cyc.rest.service;

import cn.cyc.common.util.StatusResult;

public interface ItemService {
	StatusResult getItemBaseInfo(long itemId);
	
	StatusResult getItemBaseDesc(long itemId);
	
	StatusResult getItemBaseParam(long itemId);
}
