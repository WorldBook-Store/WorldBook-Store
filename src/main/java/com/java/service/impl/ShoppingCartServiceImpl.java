package com.java.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.java.entity.Book;
import com.java.entity.CartItem;
import com.java.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	private Map<Integer, CartItem> map = new HashMap<Integer, CartItem>();

	@Override
	public void add(CartItem item) {
		CartItem existedItem = map.get(item.getBookId());

		if (existedItem != null) {
			existedItem.setQuantity(item.getQuantity() + existedItem.getQuantity());
			existedItem.setTotalPrice(item.getTotalPrice() + existedItem.getUnitPrice() * existedItem.getQuantity());
		} else {
			map.put(item.getBookId(), item);
		}
	}

	@Override
	public void remove(CartItem item) {

		map.remove(item.getBookId());

	}

	@Override
	public Collection<CartItem> getCartItems() {
		return map.values();
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public double getAmount() {
		return map.values().stream().mapToDouble(item -> item.getQuantity() * item.getUnitPrice()).sum();
	}

	@Override
	public int getCount() {
		if (map.isEmpty()) {
			return 0;
		}

		return map.values().size();
	}

	@Override
	public void remove(Book book) {

	}
}
