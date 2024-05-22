package com.nitinrana.movieticketbooking.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "show_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Show {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer showId;

	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@ManyToOne
	@JoinColumn(name = "theatre_id")
	private Theatre theatre;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	@OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
	private List<Seat> seats;

	@OneToMany(mappedBy = "show", cascade = CascadeType.REMOVE)
	private List<Booking> bookings;

	private Integer price;
}
