package cn.cyc.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cyc.common.util.ExceptionUtil;
import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbContent;
import cn.cyc.rest.service.ContentService;

/**
 * rest中Content内容管理（广告类）
 * @Description  
 * @author       陈豫川
 * @date         2018年5月13日
 */
@RequestMapping("/content")
@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	
	@RequestMapping(value="/list/{contentCategoryId}")
	@ResponseBody
	public StatusResult getContentList(@PathVariable long contentCategoryId){
		try {
			List<TbContent> list = contentService.getContentList(contentCategoryId);
			return StatusResult.ok(list);
		} catch (Exception e) {
			return StatusResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
