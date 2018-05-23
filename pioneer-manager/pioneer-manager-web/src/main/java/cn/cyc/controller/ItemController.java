package cn.cyc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cyc.common.pojo.EUDataResult;
import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbItem;
import cn.cyc.service.ItemService;

@RequestMapping("/item")
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping("/list")
	@ResponseBody
	public EUDataResult getItemList(Integer page, Integer rows) {
		EUDataResult result = itemService.getItemList(page, rows);
		return result;
	}

	@RequestMapping("/save")
	@ResponseBody
	public StatusResult insertItem(TbItem item, String desc, String itemParams) throws Exception {
		StatusResult resultStatus = itemService.insertItem(item, desc, itemParams);
		return resultStatus;
	}
}
