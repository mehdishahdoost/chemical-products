package com.asellion.product.service.impl;

import com.asellion.product.exception.ProductNotFoundException;
import com.asellion.product.model.entity.Product;
import com.asellion.product.repository.ProductRepository;
import com.asellion.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ProductServiceImpl -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/26
 * @since 1.0.0
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repository;

	@Override
	public List<Product> getProducts() {
		return repository.findAll();
	}

	@Override
	public Product getProduct(Long id) throws ProductNotFoundException {
		Optional<Product> product = repository.findById(id);
		if(product.isPresent()) {
			return product.get();
		}else {
			throw new ProductNotFoundException("Product not found!");
		}
	}
}
