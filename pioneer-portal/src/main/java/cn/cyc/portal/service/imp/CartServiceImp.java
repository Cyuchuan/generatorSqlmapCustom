package cn.cyc.portal.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.cyc.common.util.CookieUtils;
import cn.cyc.common.util.HttpClientUtil;
import cn.cyc.common.util.JsonUtils;
import cn.cyc.common.util.StatusResult;
import cn.cyc.pojo.TbItem;
import cn.cyc.portal.pojo.CartItem;
import cn.cyc.portal.service.CartService;

@Service
public class CartServiceImp implements CartService {

	// 服务URL
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	// 商品服务URL
	@Value("${ITEM_BASE_INFO}")
	private String ITEM_BASE_INFO;
	// COOKIE中购物车商品对应的key
	@Value("${CART_ITEMS_LIST_KEY}")
	private String CART_ITEMS_LIST_KEY;
	// 购物车cookie生存期
	@Value("${CART_ITEMS_EXPIRE_TIME}")
	private Integer CART_ITEMS_EXPIRE_TIME;

	@Override
	public StatusResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		// 取商品信息
		CartItem cartItem = null;
		// 取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		// 判断购物车商品列表中是否存在此商品
		for (CartItem cItem : itemList) {
			// 如果存在此商品
			if (cItem.getId() == itemId) {
				// 增加商品数量
				cItem.setNum(cItem.getNum() + num);
				cartItem = cItem;
				break;
			}
		}
		if (cartItem == null) {
			cartItem = new CartItem();
			// 根据商品id查询商品基本信息。
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_BASE_INFO + itemId);
			// 把json转换成java对象
			StatusResult result = StatusResult.formatToPojo(json, TbItem.class);
			if (result.getStatus() == 200) {
				TbItem item = (TbItem) result.getData();
				cartItem.setId(item.getId());
				cartItem.setTitle(item.getTitle());
				cartItem.setImage(item.getImage().split(",")[0]);
				cartItem.setNum(num);
				cartItem.setPrice(item.getPrice());
			}
			// 添加到购物车列表
			itemList.add(cartItem);
		}
		// 把购物车列表写入cookie
		CookieUtils.setCookie(request, response, CART_ITEMS_LIST_KEY, JsonUtils.objectToJson(itemList), true);

		return StatusResult.ok(itemList);
	}

	/**
	 * 从cookies中获取购物中的cookies值
	 * 
	 * @MethodName getCartItemList
	 * @Description
	 * @param request
	 * @return
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request) {
		// 从cookie中取商品列表
		String cartJson = CookieUtils.getCookieValue(request, CART_ITEMS_LIST_KEY, true);
		if (cartJson == null) {
			return new ArrayList<>();
		}
		// 把json转换成商品列表
		try {
			List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> cartItemList = getCartItemList(request);
		return cartItemList;
	}

	/**
	 * 更新购物车中的商品数量
	 * 
	 * @Description
	 * @param request
	 * @param response
	 * @return
	 * @see cn.cyc.portal.service.CartService#getCartItemList(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public List<CartItem> updateCartItem(long itemId, int num, HttpServletRequest request,
			HttpServletResponse response) {
		List<CartItem> cartItemList = getCartItemList(request);
		for (CartItem cartItem : cartItemList) {
			if (cartItem.getId() == itemId) {
				cartItem.setNum(num);
				break;
			}
		}
		CookieUtils.setCookie(request, response, CART_ITEMS_LIST_KEY, JsonUtils.objectToJson(cartItemList), true);
		return cartItemList;
	}

	/**
	 * 删除购物车中的商品
	 * 
	 * @Description
	 * @param itemId
	 * @param request
	 * @param response
	 * @return
	 * @see cn.cyc.portal.service.CartService#deleteCartItem(long,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public List<CartItem> deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> cartItemList = getCartItemList(request);
		for (CartItem cartItem : cartItemList) {
			if (cartItem.getId() == itemId) {
				cartItemList.remove(cartItem);
				break;
			}
		}
		CookieUtils.setCookie(request, response, CART_ITEMS_LIST_KEY, JsonUtils.objectToJson(cartItemList), true);
		return cartItemList;
	}
}
