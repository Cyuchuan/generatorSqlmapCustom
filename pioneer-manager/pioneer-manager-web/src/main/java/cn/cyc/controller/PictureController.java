package cn.cyc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.cyc.service.PictureService;

@Controller
@RequestMapping("/pic")
public class PictureController {
	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("/upload")
	@ResponseBody
	public Map uploadPicture(MultipartFile uploadFile) throws Exception{
		
		Map map = pictureService.uploadPicture(uploadFile);
		
		return map;
	}
}
