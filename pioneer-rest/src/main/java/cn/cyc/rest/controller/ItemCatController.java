package cn.cyc.rest.controller;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cyc.rest.pojo.CatData;
import cn.cyc.rest.service.ItemCatService;

@RequestMapping("/itemcat")
@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping("/list")
	@ResponseBody
	public Object getItemCatList(String callback) {
		CatData catData = new CatData();
		catData = itemCatService.getItemCatListService();
		MappingJacksonValue jacksonValue = new MappingJacksonValue(catData);
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}

}
