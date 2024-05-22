package com.nitinrana.movieticketbooking.dto;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.nitinrana.movieticketbooking.constant.MtbConstants;
import com.nitinrana.movieticketbooking.model.Show;
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
public class MovieDto implements MtbConstants {

	@NotNull(groups = Update.class)
	private Integer movieId;

	@NotBlank(groups = { Create.class, Update.class })
	@Pattern(regexp = REGEXP_NO_LEAD_TRAIL_SPACES, message = MSG_NO_LEAD_TRAIL_SPACES, groups = { Create.class,
			Update.class })
	@Size(max = 150, message = MSG_VALUE_LESS_THAN_EQUAL, groups = { Create.class, Update.class })
	private String movieName;

	@Pattern(regexp = REGEXP_NO_LEAD_TRAIL_SPACES, message = MSG_NO_LEAD_TRAIL_SPACES, groups = { Create.class,
			Update.class })
	@Size(max = 500, message = MSG_VALUE_LESS_THAN_EQUAL, groups = { Create.class, Update.class })
	private String description;

	@NotNull(groups = { Create.class, Update.class })
	@Min(value = 30, groups = { Create.class, Update.class })
	@Max(value = 210, groups = { Create.class, Update.class })
	private Integer durationInMins;

	@NotBlank(groups = { Create.class, Update.class })
	@Pattern(regexp = REGEXP_NO_LEAD_TRAIL_SPACES, message = MSG_NO_LEAD_TRAIL_SPACES, groups = { Create.class,
			Update.class })
	@Size(max = 50, message = MSG_VALUE_LESS_THAN_EQUAL, groups = { Create.class, Update.class })
	private String genre;

	@NotBlank(groups = { Create.class, Update.class })
	@Pattern(regexp = REGEXP_NO_LEAD_TRAIL_SPACES, message = MSG_NO_LEAD_TRAIL_SPACES, groups = { Create.class,
			Update.class })
	@Size(max = 50, message = MSG_VALUE_LESS_THAN_EQUAL, groups = { Create.class, Update.class })
	private String language;

	@NotNull(groups = { Create.class, Update.class })
	private Date releaseDate;
}
