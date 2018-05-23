package cn.cyc.order.service;

import java.util.List;

import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbOrder;
import cn.cyc.pojo.TbOrderItem;
import cn.cyc.pojo.TbOrderShipping;

public interface OrderService {
	StatusResult insertOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping);
}
