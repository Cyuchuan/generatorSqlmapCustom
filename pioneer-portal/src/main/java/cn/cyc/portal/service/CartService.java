package cn.cyc.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cyc.common.util.StatusResult;
import cn.cyc.portal.pojo.CartItem;

public interface CartService {
	StatusResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response);

	List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);

	List<CartItem> updateCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response);

	List<CartItem> deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response);
}
