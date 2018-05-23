package cn.cyc.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cyc.portal.service.ContentService;

@Controller
public class PageController {
	@Autowired
	private ContentService contentService;

	@RequestMapping("/index")
	public String showIndex(Model model) {
		String adResult = contentService.getAdItemList();
		model.addAttribute("ad1", adResult);
		return "index";
	}
}
