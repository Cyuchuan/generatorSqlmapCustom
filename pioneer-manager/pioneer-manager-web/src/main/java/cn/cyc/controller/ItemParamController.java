package cn.cyc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cyc.common.pojo.EUDataResult;
import cn.cyc.common.util.JsonUtils;
import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbItemParam;
import cn.cyc.service.ItemParaService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

	@Autowired
	private ItemParaService itemParaService;

	@RequestMapping("/list")
	@ResponseBody
	public EUDataResult getItemParamList(Integer page, Integer rows) {
		EUDataResult result = itemParaService.getItemParamList(page, rows);
		return result;
	}

	@RequestMapping("/query/itemcatid/{cid}")
	@ResponseBody
	public StatusResult getItemParamByCid(@PathVariable long cid) {
		StatusResult status = itemParaService.getItemParamByCid(cid);
		return status;
	}

	@RequestMapping("/save/{cid}")
	@ResponseBody
	public StatusResult insertItemParam(@PathVariable long cid, String paramData) {
		TbItemParam tbItemParam = new TbItemParam();
		tbItemParam.setItemCatId(cid);
		tbItemParam.setParamData(paramData);
		StatusResult status = itemParaService.insertItemParam(tbItemParam);
		return status;
	}

}
