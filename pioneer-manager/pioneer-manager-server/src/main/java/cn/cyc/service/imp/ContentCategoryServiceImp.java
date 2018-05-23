package cn.cyc.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.cyc.common.pojo.EUTreeNode;
import cn.cyc.common.util.HttpClientUtil;
import cn.cyc.common.util.StatusResult;
import cn.cyc.mapper.TbContentCategoryMapper;
import cn.cyc.pojo.TbContentCategory;
import cn.cyc.pojo.TbContentCategoryExample;
import cn.cyc.pojo.TbContentCategoryExample.Criteria;
import cn.cyc.service.ContentCategoryService;
/**
 * 内容类别管理（首页广告）
 * @Description  
 * @author       陈豫川
 * @date         2018年5月17日
 */
@Service
public class ContentCategoryServiceImp implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Value("${REST_CAT_SYNC_URL}")
	private String REST_CAT_SYNC_URL;

	@Override
	public List<EUTreeNode> getContentCategory(long parentId) {
		List<EUTreeNode> resultList = new ArrayList<>();
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> contentCatList = contentCategoryMapper.selectByExample(example);
		for (TbContentCategory tbContentCategory : contentCatList) {
			EUTreeNode euTreeNode = new EUTreeNode();
			euTreeNode.setId(tbContentCategory.getId());
			euTreeNode.setText(tbContentCategory.getName());
			euTreeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			resultList.add(euTreeNode);
		}
		return resultList;
	}

	@Override
	public StatusResult insertContentCategory(long parentId, String name) throws Exception {
		TbContentCategory insertOneCategory = insertOneCategory(parentId, name);
		if (parentId != 0) {
			TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(parentId);
			if (!category.getIsParent()) {
				category.setIsParent(true);
				contentCategoryMapper.updateByPrimaryKey(category);
			}
		}
		contentCategoryMapper.insert(insertOneCategory);
		// 添加redis缓存同步
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CAT_SYNC_URL + 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new StatusResult(insertOneCategory);
	}

	private TbContentCategory insertOneCategory(long parentId, String name) throws Exception {
		Date date = new Date();
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		tbContentCategory.setStatus(1);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setIsParent(false);
		tbContentCategory.setCreated(date);
		tbContentCategory.setUpdated(date);
		return tbContentCategory;
	}

	@Override
	public StatusResult deleteContentCategory(long id) throws Exception {
		deleteAllByParentId(id);
		TbContentCategory selectByPrimaryKey = contentCategoryMapper.selectByPrimaryKey(id);
		long parentId = selectByPrimaryKey.getParentId();
		contentCategoryMapper.deleteByPrimaryKey(id);

		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		if (list.size() == 0) {
			TbContentCategory changeParent = contentCategoryMapper.selectByPrimaryKey(parentId);
			changeParent.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(changeParent);
		}
		// 添加redis缓存同步
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CAT_SYNC_URL + 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StatusResult().ok();
	}

	private void deleteAllByParentId(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		for (TbContentCategory tbContentCategory : list) {
			if (tbContentCategory.getIsParent()) {
				deleteAllByParentId(tbContentCategory.getId());
			}
			contentCategoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
		}
	}

	@Override
	public StatusResult updateContentCategoryName(long id, String name) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		// 添加redis缓存同步
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CAT_SYNC_URL + 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StatusResult.ok();
	}
}
