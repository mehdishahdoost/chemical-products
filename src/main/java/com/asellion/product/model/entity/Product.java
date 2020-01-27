package com.asellion.product.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Product -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/26
 * @since 1.0.0
 */
@Entity
@Table(name = "products")
@Setter
@Getter
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	@NotNull
	private String name;
	@Column(name = "current_price")
	@NotNull
	private BigDecimal currentPrice;
	@Column(name = "last_updated")
	@NotNull
	private Timestamp lastUpdated;
	@Column
	@NotNull
	private Timestamp created;
}
