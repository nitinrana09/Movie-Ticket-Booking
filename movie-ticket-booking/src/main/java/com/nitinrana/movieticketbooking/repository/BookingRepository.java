package com.nitinrana.movieticketbooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nitinrana.movieticketbooking.model.Booking;
import com.nitinrana.movieticketbooking.model.User;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

	/* get all bookings for user */
	List<Booking> findByUser(User user);

	/* get booking by id and user */
	Optional<Booking> findByBookingIdAndUser(Integer bookingId, User user);

	/* get upcoming bookings by status and user */
	@Query("from Booking b where b.show.startTime>sysdate and b.status=:bookingStatus and b.user = :user order by b.show.startTime")
	List<Booking> findUpcomingByShowDateAfterAndStatusAndUser(String bookingStatus, User user);

}
