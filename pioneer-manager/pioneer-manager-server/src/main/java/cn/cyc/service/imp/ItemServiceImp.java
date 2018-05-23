package cn.cyc.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.cyc.common.pojo.EUDataResult;
import cn.cyc.common.util.IDUtils;
import cn.cyc.common.util.StatusResult;
import cn.cyc.mapper.TbItemDescMapper;
import cn.cyc.mapper.TbItemMapper;
import cn.cyc.mapper.TbItemParamItemMapper;
import cn.cyc.mapper.TbItemParamMapper;
import cn.cyc.pojo.TbItem;
import cn.cyc.pojo.TbItemDesc;
import cn.cyc.pojo.TbItemExample;
import cn.cyc.pojo.TbItemParam;
import cn.cyc.pojo.TbItemParamItem;
import cn.cyc.service.ItemService;
/**
 * 商品查询等服务
 * @Description  
 * @author       陈豫川
 * @date         2018年5月17日
 */
@Service
public class ItemServiceImp implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper itemdescMapper;

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public EUDataResult getItemList(int page, int rows) {
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EUDataResult result = new EUDataResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}

	@Override
	public StatusResult insertItem(TbItem item, String desc, String itemParam) throws Exception {
		long id = IDUtils.getItemId();
		Date date = new Date();
		item.setId(id);
		item.setStatus((byte) 1);
		item.setCreated(date);
		item.setUpdated(date);
		itemMapper.insert(item);
		// 添加商品描述
		StatusResult insertItemDescStatus = insertItemDesc(id, desc, date);
		// 添加规格
		StatusResult insertItemParamStatus = insertItemParam(id, itemParam, date);
		if (insertItemDescStatus.getStatus() != 200 || insertItemParamStatus.getStatus() != 200) {
			throw new Exception();
		}
		return StatusResult.ok();

	}

	private StatusResult insertItemDesc(long id, String desc, Date date) {
		// 创建TbItemDesc对象
		TbItemDesc itemDesc = new TbItemDesc();
		// 获得一个商品id
		itemDesc.setItemId(id);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemdescMapper.insert(itemDesc);
		return StatusResult.ok();
	}

	private StatusResult insertItemParam(long id, String itemParam, Date date) {
		// 创建TbItemDesc对象
		TbItemParamItem itemParamItem = new TbItemParamItem();
		// 获得一个商品id
		itemParamItem.setItemId(id);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		itemParamItemMapper.insert(itemParamItem);
		return StatusResult.ok();
	}

}
