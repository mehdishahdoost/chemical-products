package com.asellion.product.repository;

import com.asellion.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/26
 * @since 1.0.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
