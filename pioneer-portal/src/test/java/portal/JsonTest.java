package portal;

import org.junit.Test;

import cn.cyc.common.util.HttpClientUtil;
import cn.cyc.common.util.JsonUtils;
import cn.cyc.portal.pojo.Item;

public class JsonTest {

	
	@Test
	public void jsonTest(){
		Item item = new Item();
		item.setCategory_name("电脑");
		item.setId(1);
		item.setImage("image");
		item.setItem_desc("Item_desc");
		item.setPrice(3000);
		item.setSell_point("高性价比");
		item.setTitle("神舟");
		String string = JsonUtils.objectToJson(item);
		System.out.println(string);
	}
}
