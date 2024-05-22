package com.nitinrana.movieticketbooking.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nitinrana.movieticketbooking.constant.MtbConstants;
import com.nitinrana.movieticketbooking.validation.Create;
import com.nitinrana.movieticketbooking.validation.Update;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowRequestDto implements MtbConstants {

	@NotNull(groups = Update.class)
	private Integer showId;

	@NotNull(groups = { Create.class, Update.class })
	private Integer movieId;

	@NotNull(groups = { Create.class, Update.class })
	private Integer theatreId;

	@NotNull(groups = { Create.class, Update.class })
	@Future(message = MSG_FUTURE_VALUE, groups = { Create.class, Update.class })
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;

	@NotNull(groups = { Create.class, Update.class })
	private Integer price;
}
