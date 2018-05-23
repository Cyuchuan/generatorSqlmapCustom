package cn.cyc.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cyc.common.util.StatusResult;
import cn.cyc.rest.service.ItemService;

/**
 * 获得商品信息（基础、描述、规格等）
 * @Description  
 * @author       陈豫川
 * @date         2018年5月17日
 */

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	ItemService itemService;
	
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public StatusResult getItemBaseInfo(@PathVariable Long itemId){
		StatusResult result = itemService.getItemBaseInfo(itemId);
		return result;
	}
	
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public StatusResult getItemBaseDesc(@PathVariable Long itemId){
		StatusResult result = itemService.getItemBaseDesc(itemId);
		return result;
	}
	
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public StatusResult getItemBaseParam(@PathVariable Long itemId){
		StatusResult result = itemService.getItemBaseParam(itemId);
		return result;
	}
	
	
}
