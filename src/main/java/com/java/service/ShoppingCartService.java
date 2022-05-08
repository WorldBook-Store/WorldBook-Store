package com.java.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.java.entity.Book;
import com.java.entity.CartItem;

@Service
public interface ShoppingCartService {

	int getCount();

	double getAmount();

	void clear();
	
	Collection<CartItem> getCartItems();

	void remove(CartItem item);

	void add(CartItem item);

	void remove(Book book);
}
