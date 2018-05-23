package cn.cyc.search.service.imp;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cyc.common.util.ExceptionUtil;
import cn.cyc.common.util.StatusResult;
import cn.cyc.search.mapper.ItemMapper;
import cn.cyc.search.pojo.Item;
import cn.cyc.search.service.ItemService;


@Service
public class ItemServiceImp implements ItemService {

	@Autowired
	ItemMapper itemMapper;

	@Autowired
	SolrServer solrServer;

	@Override
	public StatusResult InsertAllItemToSolr() {

		try {
			List<Item> list = itemMapper.getItemList();
			for (Item item : list) {
				SolrInputDocument document = new SolrInputDocument();
				document.setField("id", item.getId());
				document.setField("item_title", item.getTitle());
				document.setField("item_sell_point", item.getSell_point());
				document.setField("item_price", item.getPrice());
				document.setField("item_image", item.getImage());
				document.setField("item_category_name", item.getCategory_name());
				document.setField("item_desc", item.getItem_desc());
				solrServer.add(document);
			}
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return StatusResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		return StatusResult.ok();
	}

}
