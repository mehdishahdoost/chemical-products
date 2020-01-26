package com.asellion.product.service;

import com.asellion.product.exception.ProductNotFoundException;
import com.asellion.product.model.entity.Product;

import java.util.List;

/**
 * ProductService -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/26
 * @since 1.0.0
 */
public interface ProductService {

	/**
	 * Returns list of all products
	 */
	List<Product> getProducts();

	/**
	 * Returns {@link Product} by given id
	 */
	Product getProduct(Long id) throws ProductNotFoundException;

}
