package cn.cyc.portal.service;

import cn.cyc.common.util.StatusResult;
import cn.cyc.portal.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String queryString, Integer page);
}
