package com.nitinrana.movieticketbooking.repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nitinrana.movieticketbooking.model.Show;
import com.nitinrana.movieticketbooking.model.Theatre;

public interface ShowRepository extends JpaRepository<Show, Integer> {

	/* gives lists of upcoming shows */
	List<Show> findByStartTimeAfterOrderByStartTimeAsc(LocalDateTime currentTime);

	/* gives upcoming show by id */
	Optional<Show> findByShowIdAndStartTimeAfter(Integer showId, LocalDateTime currentTime);

	/* check if upcoming show exists by show id */
	Boolean existsByShowIdAndStartTimeAfter(Integer showId, LocalDateTime currentTime);

	/* gives upcoming shows by movie id */
	@Query("from Show s where s.movie.movieId = :movieId and s.startTime>sysdate order by s.startTime")
	List<Show> findByMovieId(@Param("movieId") Integer movieId);

	/* gives upcoming shows by theatre id */
	@Query("from Show s where s.theatre.theatreId = :theatreId and s.startTime>sysdate order by s.startTime")
	List<Show> findByTheatreId(@Param("theatreId") Integer theatreId);

	/* gives upcoming shows by movie id and city */
	@Query("from Show s where s.movie.movieId = :movieId and s.theatre.theatreCity = :city and s.startTime>sysdate order by s.startTime")
	List<Show> findByMovieIdAndCity(@Param("movieId") Integer movieId, @Param("city") String city);

	/* gives upcoming shows by movie id, city and date */
	@Query("from Show s where s.movie.movieId = :movieId and s.theatre.theatreCity = :city and DATE(s.startTime)=:date and s.startTime>sysdate order by s.startTime")
	List<Show> findByMovieIdAndCityAndDate(@Param("movieId") Integer movieId, @Param("city") String city,
			@Param("date") Date date);

	/* gives count of upcoming shows by movie id */
	@Query("select count(*) from Show s where s.movie.movieId = :movieId and s.startTime>sysdate")
	long countByMovieId(@Param("movieId") Integer movieId);

	/* gives count of upcoming shows by theatre id */
	@Query("select count(*) from Show s where s.theatre.theatreId = :theatreId and s.startTime>sysdate")
	long countByTheatreId(@Param("theatreId") Integer theatreId);

	/* checks overlapping of slots while adding a show */
	@Query("select count(*) from Show s where s.theatre=:theatre and ((s.endTime<:endTime and :startTime<s.endTime) or (s.endTime>=:endTime and s.startTime<:endTime))")
	long checkSlotOverlap(@Param("theatre") Theatre theatreId, @Param("startTime") LocalDateTime startTime,
			@Param("endTime") LocalDateTime endTime);

	/* checks overlapping of slots while updating a show */
	@Query("select count(*) from Show s where s.theatre=:theatre and s.showId!=:showId and ((s.endTime<:endTime and :startTime<s.endTime) or (s.endTime>=:endTime and s.startTime<:endTime))")
	long checkSlotOverlapUpdate(@Param("theatre") Theatre theatreId, @Param("startTime") LocalDateTime startTime,
			@Param("endTime") LocalDateTime endTime, @Param("showId") Integer showId);
}
