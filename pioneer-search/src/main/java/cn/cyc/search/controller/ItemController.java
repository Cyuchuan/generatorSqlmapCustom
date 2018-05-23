package cn.cyc.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cyc.common.util.StatusResult;
import cn.cyc.search.service.ItemService;

@RequestMapping("/manager")
@Controller
public class ItemController {
	
	@Autowired
	ItemService itemService;
	
	@RequestMapping("/importall")
	@ResponseBody
	public StatusResult insertAllToSolr(){
		StatusResult result = itemService.InsertAllItemToSolr();
		return result;
	}
}
