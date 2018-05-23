package cn.cyc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cyc.common.pojo.EUDataResult;
import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbContent;
import cn.cyc.service.ContentManagerService;

@RequestMapping("/content")
@Controller
public class ContentManageController {
	
	@Autowired
	private ContentManagerService contentManagerService;
	
	
	@RequestMapping("/query/list")
	@ResponseBody
	public EUDataResult getContentListByCatId(int page, int rows, long categoryId){
		EUDataResult result = contentManagerService.getContentListByCatId(page, rows, categoryId);
		return result;
	}
	
	
	@RequestMapping("/save")
	@ResponseBody
	public StatusResult saveContent(TbContent content){
		StatusResult result = contentManagerService.saveContent(content);
		return result;
	}
	
	
	@RequestMapping("/edit")
	@ResponseBody
	public StatusResult editContent(TbContent content){
		StatusResult result = contentManagerService.editContent(content);
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public StatusResult deleteContentByCatId(long ids){
		StatusResult result = contentManagerService.deleteContentByCid(ids);
		return result;
	}
	
}
