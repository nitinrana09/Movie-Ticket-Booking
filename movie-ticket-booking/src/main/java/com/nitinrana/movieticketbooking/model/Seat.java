package com.nitinrana.movieticketbooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer seatId;

	private Integer seatNumber;

	@ManyToOne
	@JoinColumn(name = "show_id")
	private Show show;

	@ManyToOne
	@JoinColumn(name = "booking_id")
	private Booking booking;

	public Seat(Integer seatNumber, Show show) {
		this.seatNumber = seatNumber;
		this.show = show;
	}

}