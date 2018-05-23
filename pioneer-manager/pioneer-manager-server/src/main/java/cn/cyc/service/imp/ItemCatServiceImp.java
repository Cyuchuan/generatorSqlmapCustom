package cn.cyc.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cyc.common.pojo.EUTreeNode;
import cn.cyc.mapper.TbItemCatMapper;
import cn.cyc.pojo.TbItemCat;
import cn.cyc.pojo.TbItemCatExample;
import cn.cyc.pojo.TbItemCatExample.Criteria;
import cn.cyc.service.ItemCatService;

/**
 * 商品类目查询等服务
 * @Description  
 * @author       陈豫川
 * @date         2018年5月17日
 */
@Service
public class ItemCatServiceImp implements ItemCatService {
	@Autowired
	private TbItemCatMapper catMapper;

	@Override
	public List<EUTreeNode> getItemCatList(long parentId) throws Exception {
		List<EUTreeNode> listNode = new ArrayList<>(32);
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> listItemCat = catMapper.selectByExample(example);
		for(TbItemCat x:listItemCat){
			EUTreeNode euTreeNode = new EUTreeNode();
			euTreeNode.setId(x.getId());
			euTreeNode.setText(x.getName());
			euTreeNode.setState(x.getIsParent()?"closed":"open");
			listNode.add(euTreeNode);
		}
		
		return listNode;
	}
	
	

}
