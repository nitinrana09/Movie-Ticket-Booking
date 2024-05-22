package com.nitinrana.movieticketbooking.dto;

import com.nitinrana.movieticketbooking.constant.MtbConstants;
import com.nitinrana.movieticketbooking.validation.Create;
import com.nitinrana.movieticketbooking.validation.Update;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheatreDto implements MtbConstants {

	@NotNull(groups = Update.class)
	private Integer theatreId;

	@NotBlank(groups = { Create.class, Update.class })
	@Pattern(regexp = REGEXP_NO_LEAD_TRAIL_SPACES, message = MSG_NO_LEAD_TRAIL_SPACES, groups = { Create.class,
			Update.class })
	@Size(max = 50, message = MSG_VALUE_LESS_THAN_EQUAL, groups = { Create.class, Update.class })
	private String theatreName;

	@NotBlank(groups = { Create.class, Update.class })
	@Pattern(regexp = REGEXP_NO_LEAD_TRAIL_SPACES, message = MSG_NO_LEAD_TRAIL_SPACES, groups = { Create.class,
			Update.class })
	@Size(max = 50, message = MSG_VALUE_LESS_THAN_EQUAL, groups = { Create.class, Update.class })
	private String theatreCity;

	@NotNull(groups = { Create.class, Update.class })
	@Min(value = 20, groups = { Create.class, Update.class })
	@Max(value = 800, groups = { Create.class, Update.class })
	private Integer seatCount;
}
