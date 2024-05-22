package com.nitinrana.movieticketbooking.constant;

public interface MtbConstants {

	/* Validation messages */
	public static final String MSG_NO_LEAD_TRAIL_SPACES = "must not contain leading or trailing spaces";
	public static final String MSG_FUTURE_VALUE = "must be a future value";
	public static final String MSG_VALUE_LESS_THAN_EQUAL = "size must be less than or equal to {max}";
	public static final String MSG_VALUE_GREATER_THAN_EQUAL = "size must be greater than or equal to {min}";

	/* Validation regexp */
	public static final String REGEXP_NO_LEAD_TRAIL_SPACES = "\\S.*\\S";

	/* Show constants */
	public static final Integer INTERVAL_TIME_IN_MINS = 15;

	/* Booking constants */
	public enum BOOKING_STATUS {
		CONFIRMED, CANCELED
	};

	/* Security constants */
	public static final String AUTH_ADMIN_USER = "hasAnyAuthority('ADMIN','USER')";
	public static final String AUTH_ADMIN = "hasAuthority('ADMIN')";
	public static final String AUTH_USER = "hasAuthority('USER')";

	public static final long JWT_TOKEN_VALIDITY = 60 * 60 * 1000;
	public static final String SECRET_KEY = "A298SDJ2309JJF9J39J29J39JF9J3FFNJKH3JHJKHJ3HJJ330FF0J084HFOJLJDKLFJ498J43000390JFJ0J03J03J03J03J03JFJHG";

	/* Email constants */
	public static final String BOOKING_SUBJECT = "Show Booking %s";
	public static final String BOOKING_BODY = "Hi %s,\n\nPlease find below details of %s Show Booking\n\nBooking Id: %d\nBooking Date: %s\nSeat Count: %d\nSeat(s): %s\nShow Start Time: %s\nMovie: %s\nTheatre: %s\n\nFor all Booking Details, go to MTB Application.\n\nThanks & Regards,\nMTB Team";
}
