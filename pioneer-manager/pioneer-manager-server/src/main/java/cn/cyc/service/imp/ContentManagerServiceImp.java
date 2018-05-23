package cn.cyc.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.HttpClientUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.cyc.common.pojo.EUDataResult;
import cn.cyc.common.util.HttpClientUtil;
import cn.cyc.common.util.StatusResult;
import cn.cyc.mapper.TbContentMapper;
import cn.cyc.pojo.TbContent;
import cn.cyc.pojo.TbContentExample;
import cn.cyc.pojo.TbContentExample.Criteria;
import cn.cyc.service.ContentCategoryService;
import cn.cyc.service.ContentManagerService;
/**
 * 首页广告等信息的展示
 * @Description  
 * @author       陈豫川
 * @date         2018年5月17日
 */
@Service
public class ContentManagerServiceImp implements ContentManagerService {

	@Autowired
	private TbContentMapper contentMapper;

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;

	@Override
	public EUDataResult getContentListByCatId(int page, int rows, long categoryId) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		PageHelper.startPage(page, rows);
		List<TbContent> list = contentMapper.selectByExample(example);
		PageInfo<TbContent> info = new PageInfo<>(list);
		EUDataResult euDataResult = new EUDataResult();
		euDataResult.setTotal(info.getTotal());
		euDataResult.setRows(list);
		return euDataResult;
	}

	@Override
	public StatusResult saveContent(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		contentMapper.insert(content);
		// 添加redis缓存同步
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StatusResult.ok();
	}

	public StatusResult editContent(TbContent content) {
		Date date = new Date();
		content.setUpdated(date);
		contentMapper.updateByPrimaryKey(content);
		// 添加redis缓存同步
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return StatusResult.ok();
	}

	@Override
	public StatusResult deleteContentByCid(long ids) {
		contentMapper.deleteByPrimaryKey(ids);
		// 添加redis缓存同步
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + String.valueOf(ids));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StatusResult().ok();
	}
}
