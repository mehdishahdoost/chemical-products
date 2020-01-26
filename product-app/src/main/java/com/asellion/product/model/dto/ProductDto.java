package com.asellion.product.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * ProductDto -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/26
 * @since 1.0.0
 */
@Data
public class ProductDto {

	private Long id;
	private String name;
	private String currentPrice;
	private Date lastUpdated;
}
