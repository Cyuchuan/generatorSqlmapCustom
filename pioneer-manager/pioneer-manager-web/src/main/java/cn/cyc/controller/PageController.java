package cn.cyc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}

	@RequestMapping("/{path}")
	public String showOthers(@PathVariable String path){
		return path;
	}
}
