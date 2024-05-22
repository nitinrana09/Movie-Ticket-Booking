package com.nitinrana.movieticketbooking.exception;

public class MtbException extends RuntimeException {

	/* movie error description */
	public static final String MOVIE_NOT_EXIST = "Movie with Id: %d doesn't exist";

	/* theatre error description */
	public static final String THEATRE_NOT_EXIST = "Theatre with Id: %d doesn't exist";

	/* show error description */
	public static final String SHOW_NOT_EXIST = "Show with Id: %d doesn't exist";
	public static final String SHOW_OVERLAP = "Show timings overlapping";
	public static final String NOT_WITH_PENDING_SHOWS = "Can't %s %s with pending shows";
	public static final String SEAT_BOOKED = "Can't %s show. At least one seat is booked";

	/* seat error description */
	public static final String SEAT_NOT_EXIST = "Theatre with Id: %d have seat numbers between 1 and %d";
	public static final String SEATS_ALREADY_BOOKED = "Seat(s) with seat number: %s already booked";

	/* booking error description */
	public static final String BOOKING_NOT_EXIST = "Booking with Id: %d doesn't exist";
	public static final String STATUS_NOT_COMFIRMED = "Status of Booking with Id: %d isn't confirmed";
	public static final String BOOKING_SHOW_PAST_TIME = "Booked Show already happened";
	public static final String STATUS_ALREADY_CANCELED = "Status of Booking with Id: %d already canceled";
	public static final String EXISTING_NEW_SHOW = "While update Booking, show Id must be same as the time of Booking Show";

	/* user error description */
	public static final String USER_NOT_EXIST = "User with email: %s doesn't exist";
	public static final String USER_ALREADY_EXIST = "User with email: %s already exists";
	public static final String USER_NOT_ENABLED = "User with email: %s is disabled";

	/* role error description */
	public static final String ROLES_NOT_EXIST = "Role(s) with Id: %s don't exist";

	/* email error description */
	public static final String MAIL_NOT_SENT = "Couldn't send mail to %s";

	public MtbException(String errorMessage) {
		super(errorMessage);
	}
}
