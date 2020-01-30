package ir.beesmart.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AuthResponse -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

	@Getter
	private String token;
	@Setter
	@Getter
	private String message;

	public AuthResponse(String token) {
		this.token = token;
	}
}
