package com.nitinrana.movieticketbooking.dto;

import java.util.List;

import com.nitinrana.movieticketbooking.constant.MtbConstants;
import com.nitinrana.movieticketbooking.validation.Create;
import com.nitinrana.movieticketbooking.validation.Update;
import com.nitinrana.movieticketbooking.validation.UserCredentials;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto implements MtbConstants {

	@NotBlank(groups = { Create.class, Update.class })
	@Pattern(regexp = REGEXP_NO_LEAD_TRAIL_SPACES, message = MSG_NO_LEAD_TRAIL_SPACES, groups = { Create.class,
			Update.class })
	@Size(max = 100, message = MSG_VALUE_LESS_THAN_EQUAL, groups = { Create.class, Update.class })
	private String name;

	@NotBlank(groups = { Create.class, UserCredentials.class })
	@Email(groups = { Create.class, UserCredentials.class })
	@Size(max = 100, message = MSG_VALUE_LESS_THAN_EQUAL, groups = { Create.class, UserCredentials.class })
	private String email;

	@NotBlank(groups = { Create.class, Update.class, UserCredentials.class })
	@Size(max = 50, message = MSG_VALUE_LESS_THAN_EQUAL, groups = { Create.class, Update.class, UserCredentials.class })
	private String password;

	@NotNull(groups = { Create.class, Update.class })
	@Size(min = 1, message = MSG_VALUE_GREATER_THAN_EQUAL, groups = { Create.class, Update.class })
	private List<Integer> roles;

}
