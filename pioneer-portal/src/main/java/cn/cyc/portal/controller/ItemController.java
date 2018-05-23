package cn.cyc.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cyc.portal.pojo.ItemInfo;
import cn.cyc.portal.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	ItemService itemService;

	@RequestMapping("/{itemId}")
	public String getItemBaseInfoById(@PathVariable Long itemId, Model model) {
		ItemInfo item = itemService.getItemByItemId(itemId);
		model.addAttribute("item", item);
		return "item";
	}

	@RequestMapping(value = "/desc/{itemId}", produces = MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
	@ResponseBody
	public String getItemDescByItemId(@PathVariable Long itemId) {
		String descString = itemService.getItemDescByItemId(itemId);
		return descString;
	}

	@RequestMapping(value = "/param/{itemId}", produces = MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
	@ResponseBody
	public String getItemParamByItemId(@PathVariable Long itemId) {
		String descString = itemService.getItemParamByItemId(itemId);
		return descString;
	}
}
