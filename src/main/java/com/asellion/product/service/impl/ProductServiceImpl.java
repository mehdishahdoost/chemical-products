package com.asellion.product.service.impl;

import com.asellion.product.exception.ProductNotFoundException;
import com.asellion.product.model.entity.Product;
import com.asellion.product.repository.ProductRepository;
import com.asellion.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
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

	/**
	 * Returns list of {@link Product}
	 *
	 * @return array of Product
	 */
	@Override
	public List<Product> getProducts() {
		return repository.findAll();
	}

	/**
	 * Return {@link Product} by given product's id
	 *
	 * @param id
	 * @return
	 * @throws ProductNotFoundException
	 */
	@Override
	public Product getProduct(Long id) throws ProductNotFoundException {
		Optional<Product> product = repository.findById(id);
		if (product.isPresent()) {
			return product.get();
		} else {
			throw new ProductNotFoundException("Product not found!");
		}
	}

	/**
	 * Update name and currentPrice of {@link Product}
	 *
	 * @param product
	 * @return Product
	 * @throws ProductNotFoundException
	 */
	@Override
	@Transactional
	public Product updateProduct(Product product) throws ProductNotFoundException {
		Optional<Product> currentProduct = repository.findById(product.getId());
		if (currentProduct.isPresent()) {
			Product curProduct = currentProduct.get();
			curProduct.setName(product.getName());
			curProduct.setCurrentPrice(product.getCurrentPrice());
			curProduct.setLastUpdated(new Timestamp(System.currentTimeMillis()));
			repository.save(curProduct);
			return curProduct;
		} else {
			throw new ProductNotFoundException("Product not found!");
		}
	}

	/**
	 * Creates {@link Product}
	 * @param product
	 * @return Product
	 */
	@Override
	@Transactional
	public Product createProduct(Product product) {
		product.setId(null);
		product.setCreated(new Timestamp(System.currentTimeMillis()));
		return repository.save(product);
	}
}
