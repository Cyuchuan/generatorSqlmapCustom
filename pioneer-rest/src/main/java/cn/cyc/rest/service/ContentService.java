package cn.cyc.rest.service;

import java.util.List;

import cn.cyc.pojo.TbContent;


public interface ContentService {
	List<TbContent> getContentList(long contentCid);
}
