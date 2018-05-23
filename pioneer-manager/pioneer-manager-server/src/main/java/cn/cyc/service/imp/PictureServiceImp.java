package cn.cyc.service.imp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.cyc.common.util.FtpUtil;
import cn.cyc.common.util.IDUtils;
import cn.cyc.service.PictureService;
/**
 * 图片上传等服务（使用ftp上传图片到nginx服务器）
 * @Description  
 * @author       陈豫川
 * @date         2018年5月17日
 */
@Service
public class PictureServiceImp implements PictureService {

	@Value("${FTP_IP}")
	private String FTP_IP;

	@Value("${FTP_PORT}")
	private Integer FTP_PORT;

	@Value("${FTP_USER}")
	private String FTP_USER;

	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;

	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;

	@Value("${BASE_PATH}")
	private String BASE_PATH;

	@Override
	public Map uploadPicture(MultipartFile uploadFile) throws Exception{
		Map result = new HashMap<>();
		String originalFilename = uploadFile.getOriginalFilename();
		String newName = IDUtils.getImageName();
		newName += originalFilename.substring(originalFilename.lastIndexOf('.'));
		String newPath = new DateTime().toString("yyyy/MM/dd");
		try {
			if (FtpUtil.uploadFile(FTP_IP, FTP_PORT, FTP_USER, FTP_PASSWORD, FTP_BASE_PATH, newPath, newName,
					uploadFile.getInputStream())) {
				result.put("error", 0);
				result.put("url", BASE_PATH + newPath + "/" + newName);
			}else{
				result.put("error", 1);
				result.put("message", "上传失败");
			}
		} catch (IOException e) {
			result.put("error", 1);
			result.put("message", "上传失败");
			throw new Exception();
		}

		return result;
	}

}
