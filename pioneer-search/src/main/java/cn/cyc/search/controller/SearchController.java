package cn.cyc.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cyc.common.util.ExceptionUtil;
import cn.cyc.common.util.StatusResult;
import cn.cyc.search.pojo.SearchResult;
import cn.cyc.search.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	SearchService searchService;

	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public StatusResult search(@RequestParam("q") String queryString, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "60") Integer rows) {
		// 查询条件不能为空
		SearchResult searchResult = null;
		try {
			if (!StringUtils.isBlank(queryString)) {
				queryString = new String(queryString.getBytes("ISO8859-1"), "UTF-8");
			}
			searchResult = searchService.search(queryString, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			return StatusResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return StatusResult.ok(searchResult);
	}
}
