package ir.beesmart.product.mapper;

import ir.beesmart.product.model.dto.ProductDto;
import ir.beesmart.product.model.entity.Product;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ProductDataMapper -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/26
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
@Component
public interface ProductDataMapper {

	@ProductMap
	@Mappings(
			{
					@Mapping(target = "currentPrice", source = "currentPrice")
			}
	)
	ProductDto toProductDto(Product product);

	@IterableMapping(qualifiedBy = ProductMap.class)
	List<ProductDto> toProductDtoList(List<Product> products);

	Product toProduct(ProductDto productDto);
}
