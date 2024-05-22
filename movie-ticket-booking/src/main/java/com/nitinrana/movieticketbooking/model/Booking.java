package com.nitinrana.movieticketbooking.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookingId;

	@ManyToOne
	@JoinColumn(name = "show_id")
	private Show show;

	private Integer bookingSeatCount;

	@OneToMany(mappedBy = "booking", cascade = CascadeType.PERSIST)
	private List<Seat> bookedSeats;

	private Date bookingDate;

	private String status;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
