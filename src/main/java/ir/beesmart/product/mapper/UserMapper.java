package ir.beesmart.product.mapper;

import ir.beesmart.product.model.dto.UserRequest;
import ir.beesmart.product.model.dto.UserResponse;
import ir.beesmart.product.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

/**
 * UserMapper -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

	UserResponse toUserResponse(User user);
	@Mappings({
			@Mapping(source = "name", target = "name"),
			@Mapping(source= "family", target = "family"),
			@Mapping(source = "username", target = "username"),
			@Mapping(source = "password", target = "password")
	})
	User toUser(UserRequest userRequest);
}
