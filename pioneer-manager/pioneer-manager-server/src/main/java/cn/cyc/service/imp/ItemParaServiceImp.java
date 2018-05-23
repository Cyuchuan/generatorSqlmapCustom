package cn.cyc.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.cyc.common.pojo.EUDataResult;
import cn.cyc.common.util.StatusResult;
import cn.cyc.mapper.TbItemParamMapper;
import cn.cyc.pojo.TbItemParam;
import cn.cyc.pojo.TbItemParamExample;
import cn.cyc.pojo.TbItemParamExample.Criteria;
import cn.cyc.service.ItemParaService;

/**
 * 商品的规格参数查询等服务
 * @Description  
 * @author       陈豫川
 * @date         2018年5月17日
 */
@Service
public class ItemParaServiceImp implements ItemParaService {

	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	@Override
	public StatusResult getItemParamByCid(long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> paralist = itemParamMapper.selectByExampleWithBLOBs(example);
		if(!paralist.isEmpty()){
			return StatusResult.ok(paralist.get(0));
		}
		return StatusResult.ok();
	}
	
	
	@Override
	public EUDataResult getItemParamList(int page, int rows) {
		PageHelper.startPage(page, rows);
		List<TbItemParam> list = itemParamMapper.selectAllWithBLOBsAndCatName();
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		EUDataResult result = new EUDataResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}


	@Override
	public StatusResult insertItemParam(TbItemParam itemParam) {
		//补全pojo
		Date date = new Date();
		itemParam.setCreated(date);
		itemParam.setUpdated(date);
		itemParamMapper.insert(itemParam);
		
		return StatusResult.ok();
	}


}
