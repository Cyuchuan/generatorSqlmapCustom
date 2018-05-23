package cn.cyc.portal.pojo;

import cn.cyc.pojo.TbItem;

public class ItemInfo extends TbItem{
	public String[] getImages() {
		if (this.getImage() != null) {
			String[] images = this.getImage().split(",");
			return images;
		}
		return null;
	}
}
