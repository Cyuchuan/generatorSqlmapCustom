package cn.cyc.sso.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.junit.Test;

public class CookiesTest {

	
	@Test
	public void test() throws UnsupportedEncodingException{
		String encode = URLEncoder.encode("你好", "utf-8");
		System.out.println(encode);
		String decode = URLDecoder.decode(encode,"utf-8");
		System.out.println(decode);
	}
}
