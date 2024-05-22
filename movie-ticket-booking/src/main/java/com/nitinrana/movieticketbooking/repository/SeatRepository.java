package com.nitinrana.movieticketbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.nitinrana.movieticketbooking.model.Seat;
import com.nitinrana.movieticketbooking.model.Show;

import org.springframework.data.jpa.repository.Query;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
	/* deletes Seats By show */
	void deleteByShow(Show show);

	/* check if booked seats exist by show and booking */
	Boolean existsByShowAndBookingNotNull(Show show);

	/* get seats by show id */
	@Query("from Seat s where s.show.showId = :showId and s.booking is null order by s.seatNumber")
	List<Seat> findAvailableSeatsByShowId(@Param("showId") Integer showId);

	/* get all seats booked by show id and seat numbers */
	@Query("select s.seatNumber from Seat s where s.show.showId = :showId and s.booking is not null and s.seatNumber in (:seatNumberList) order by s.seatNumber")
	List<Integer> findAllSeatNumberBookedByShowIdAndSeatNumbers(@Param("showId") Integer showId,
			@Param("seatNumberList") List<Integer> seatNumberList);

	/* get all seats by show id and seat numbers */
	List<Seat> findByShowAndSeatNumberIn(Show show, List<Integer> seatNumberList);

	/*
	 * get all seats booked by show id and seat numbers excluding previous booked
	 * seats
	 */
	@Query("select s.seatNumber from Seat s where s.show.showId = :showId and s.booking is not null and s.seatNumber in (:seatNumberList) and s.seatNumber not in (:existingBookedList) order by s.seatNumber")
	List<Integer> findAllSeatNumberBookedByShowIdAndSeatNumbersUpdate(@Param("showId") Integer showId,
			@Param("seatNumberList") List<Integer> seatNumberList,
			@Param("existingBookedList") List<Integer> existingBookedList);

}
