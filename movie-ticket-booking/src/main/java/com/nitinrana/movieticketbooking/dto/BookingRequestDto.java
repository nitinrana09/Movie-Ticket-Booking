package com.nitinrana.movieticketbooking.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nitinrana.movieticketbooking.constant.MtbConstants;
import com.nitinrana.movieticketbooking.validation.Create;
import com.nitinrana.movieticketbooking.validation.Update;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto implements MtbConstants{

	@NotNull(groups = Update.class)
	Integer bookingId;

	@NotNull(groups = { Create.class, Update.class })
	Integer showId;

	@NotNull(groups = { Create.class, Update.class })
	@Size(min=1, message=MSG_VALUE_GREATER_THAN_EQUAL,groups = { Create.class, Update.class })
	List<Integer> seats;
}
