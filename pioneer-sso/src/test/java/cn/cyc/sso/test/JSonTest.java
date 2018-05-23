package cn.cyc.sso.test;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.cyc.common.util.JsonUtils;
import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbUser;

public class JSonTest {

	@Test
	public void test() throws IOException{
//		TbUser user = new TbUser();
//		user.setEmail("3123213");
//		user.setPassword("3213213");
//		user.setUsername("3123213");
//		ObjectMapper mapper = new ObjectMapper();
//		String writeValueAsString = mapper.writeValueAsString(user);
//		System.out.println(writeValueAsString);
		ObjectMapper mapper = new ObjectMapper();
		String content = new String("{\"id\":null,\"username\":\"3123213\",\"password\":\"3213213\",\"phone\":null,\"email\":\"3123213\",\"created\":null,\"updated\":null}");
		TbUser readValue = mapper.readValue(content, TbUser.class);
		System.out.println(readValue.getEmail());
	}

}
