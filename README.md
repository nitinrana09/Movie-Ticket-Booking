# Movie Ticket Booking Application
Movie Ticket Booking is a Backend Application developed using Java and Spring Boot Framework. It allows Admins to manage Movies, Theatres, Shows and Shows’ seats, and Users to book and manage Movie Bookings. JWT Authentication using Spring Security and Email Service for sending mails in case of Adding, Updating or Deleting Bookings are implemented as well.
## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Spring Security
- Eclipse
- Postman
## Features
- **ADMIN**
  - Movie: Admin can Add, Update, Get list of Movies, Get a Movie and Delete Movies.
  - Theatre: Admin can Add, Update, Get list of Theatres, Get a Theatre and Delete Theatres.
  - Show: Admin can Add, Update, Get list of upcoming Shows, Get list of all Shows, Get a Show, Get list of upcoming Shows by Movie, Get list of upcoming Shows by Theatre, Get list of upcoming Shows by Movie and City, Get list of upcoming Shows by Movie, City and Date, and Delete Shows.
  - Seat: Admin can Get Available Seats By Show
  - User: Admin can Create, Login, Update, Get list of Users, Get User by Email and Disable User.
- **USER**
  - Movie: User can Get list of Movies and Get a Movie.
  - Theatre: User can Get list of Theatres and Get a Theatre.
  - Show: User can Get list of upcoming Shows, Get a Show, Get list of upcoming Shows by Movie, Get list of upcoming Shows by Theatre, Get list of upcoming Shows by Movie and City, Get list of upcoming Shows by Movie, City and Date.
  - Seat: User can Get Available Seats By Show
  - Booking: User can Book Show, Update Booking, Get list of All Bookings, Get a Booking, Get list of upcoming Confirmed Bookings and Cancel Booking. 
  - User: User can Create, Login, Update, Get User by Email and Disable User.
- User Authentication and Authorization are implemented using JWT and Spring Security for increased data privacy and security.
- Email is sent to User when the Show is Booked, Booking is Updated or Deleted containing necessary details about Booking.
- Serializable Transaction Isolation level is used in Add and Create Booking to avoid getting a particular seat to be booked by more than one user.
- Data transfer between API calls and server is done through Data Transfer Objects (DTOs).
- Proper Validations have been implemented.
- Proper Exception Handling has been done. Exact and user understandable error description is sent in API Response.
- Different layers have been implemented: Controller, Service, Repository, Data Transfer Object (DTO)
- Code is maintainable, understandable and readable.
## API Endpoints
- **Movie**
  - GET /movie/getMovies : Get list of all Movies
  - GET /movie/getMovie/{movieId} : Get a Movie
  - POST /movie/addMovie : Add a Movie
  - PUT /movie/updateMovie : Update a Movie
  - DELETE /movie/deleteMovie/{movieId} : Delete a Movie
- **Theatre**
  - GET /theatre/getTheatres : Get list of all Theatres
  - GET /theatre/getTheatre/{theatreId} : Get a Theatre
  - POST /theatre/addTheatre : Add a Theatre
  - PUT /theatre/updateTheatre : Update a Theatre
  - DELETE /theatre/deleteTheatre/{theatreId} : Delete a Theatre
- **Show**
  - GET /show/getShows : Get list of upcoming Shows
  - GET /show/getAllShows : Get list of All Shows
  - GET /show/getShow/{showId} : Get a Show
  - GET /show/getShowsByMovieId?movieId=1 : Get list of upcoming Shows by Movie Id
  - GET /show/getShowsByTheatreId?theatreId=1 : Get list of upcoming Shows by Theatre Id
  - GET /show/getShowsByMovieIdCity?movieId=1&&city=Delhi : Get list of upcoming Shows by Movie Id and City
  - GET /show/getShowsByMovieIdCityDate?movieId=1&&city=Delhi&&date=2024-01-01 : Get list of upcoming Shows by Movie Id, City and Date
  - POST /show/addShow : Add a Show
  - PUT /show/updateShow : Update a Show
  - DELETE /show/deleteShow/{showId} : Delete a Show
- **Seat**
  - GET /seat/getAvailableSeatsByShowId/{showId} : Get list of Available Seats of a Show by Show Id
- **Booking**
  - GET /booking/getBookings : Get list of all Bookings for user
  - GET /booking/getBooking/{bookingId} : Get a Booking for user
  - GET /booking/getBookingsUpcomingConfirmed : Get list of upcoming and Confirmed Bookings for user
  - POST /booking/bookShow : Book a Show
  - PUT /booking/updateBooking : Update a Booking
  - DELETE /booking/cancelBooking/{bookingId} : Cancel a Booking
- **User**
  - GET /user/getUsers : Get list of all Users
  - GET /user/getUser/{email} : Get a User by Email
  - POST /user/createUser : Create a User
  - PUT /user/updateUser : Update a User
  - DELETE /user/disableUser/{password} : Disable a User
  - POST /user/login : Login User
## Model Classes / Entities
- Movie
- Theatre
- Show
- Seat
- Booking
- Role
- User
## EER Diagram
![image](https://github.com/nitinrana09/Movie-Ticket-Booking/assets/61656356/19e6c48e-eaa4-40fb-a749-e93517378c72)
## How to get started and use Movie Ticket Booking Application
- Clone Movie Ticket Booking Application.
- Configure Database settings in "application.properties" file.
- Initialize user name of email in "userName", password in "password" and email in "from" in file "EmailServiceImpl.java". 
