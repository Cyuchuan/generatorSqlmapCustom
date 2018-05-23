package cn.cyc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cyc.common.pojo.EUDataResult;
import cn.cyc.common.pojo.EUTreeNode;
import cn.cyc.common.util.StatusResult;
import cn.cyc.service.ContentCategoryService;
import cn.cyc.service.ContentManagerService;

@RequestMapping("/content/category")
@Controller
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;

	@Autowired
	private ContentManagerService contentManagerservice;

	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getCatList(@RequestParam(value = "id", defaultValue = "0") long parentId) throws Exception {
		List<EUTreeNode> itemCatList = contentCategoryService.getContentCategory(parentId);
		return itemCatList;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public StatusResult createContentCategory(long parentId, String name) throws Exception {
		StatusResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public StatusResult deleteContentCategory(long id) throws Exception {
		StatusResult result = contentCategoryService.deleteContentCategory(id);
		return result;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public StatusResult updateContentCategoryName(long id, String name) throws Exception {
		StatusResult result = contentCategoryService.updateContentCategoryName(id, name);
		return result;
	}

}
