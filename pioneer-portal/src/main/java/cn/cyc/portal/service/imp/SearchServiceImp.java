package cn.cyc.portal.service.imp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.cyc.common.util.HttpClientUtil;
import cn.cyc.common.util.JsonUtils;
import cn.cyc.common.util.StatusResult;
import cn.cyc.portal.pojo.SearchResult;
import cn.cyc.portal.service.SearchService;

@Service
public class SearchServiceImp implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;

	@Override
	public SearchResult search(String queryString, Integer page) {
		Map<String, String> param = new HashMap<>();
		param.put("q", queryString);
		param.put("page", String.valueOf(page));
		String result = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
		StatusResult statusResult = StatusResult.formatToPojo(result, SearchResult.class);
		if (statusResult != null && statusResult.getStatus() == 200) {
			SearchResult data = (SearchResult) statusResult.getData();
			return data;
		}
		return null;
	}

}
