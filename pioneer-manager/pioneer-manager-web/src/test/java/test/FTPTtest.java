package test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import cn.cyc.common.util.FtpUtil;

public class FTPTtest {
	@Test
	public void test() throws SocketException, IOException{
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("192.168.32.129", 21);
		ftpClient.login("ftpcyc", "123456");
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.changeWorkingDirectory("/home/ftpcyc/www/image");
		ftpClient.storeFile("1.png", new BufferedInputStream(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\3.png"))));
		ftpClient.logout();
	}
	
	
	
	@Test
	public void utilTest() throws FileNotFoundException{
		FtpUtil ftpUtil = new FtpUtil();
		String data = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		ftpUtil.uploadFile("192.168.32.129", 21, "ftpcyc", "123456", "/home/ftpcyc/www/image", data, "6.png", new BufferedInputStream(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\3.png"))));
	
	}
}
