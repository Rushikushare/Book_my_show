package com.cfs.bms.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.cfs.bms.dto.BookingDto;
import com.cfs.bms.dto.BookingRequestDto;
import com.cfs.bms.dto.MovieDto;
import com.cfs.bms.dto.PaymentDto;
import com.cfs.bms.dto.ScreenDto;
import com.cfs.bms.dto.SeatDto;
import com.cfs.bms.dto.ShowDto;
import com.cfs.bms.dto.ShowSeatDto;
import com.cfs.bms.dto.TheaterDto;
import com.cfs.bms.dto.UserDto;
import com.cfs.bms.exception.SeatUnavailableException;
import com.cfs.bms.model.Booking;
import com.cfs.bms.model.Payment;
import com.cfs.bms.model.Show;
import com.cfs.bms.model.ShowSeat;
import com.cfs.bms.model.User;
import com.cfs.bms.repository.BookingRepository;
import com.cfs.bms.repository.ShowRepository;
import com.cfs.bms.repository.ShowSeatRepository;
import com.cfs.bms.repository.UserRepository;

import jakarta.transaction.Transactional;

public class BookingService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public BookingDto createBooking(BookingRequestDto bookingRequest) {

        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new RespurceNotFoundException("User Not Found"));
    

    Show show= showRepository.findById(showRepository.getShowId()).orElseThrow(()->new RespurceNotFoundException("Sh Not Found"))

    List<ShowSeat> selectedSeats=ShowSeatRepository.findAllById(bookingRequest.getSeatIds());

    for(ShowSeat seat:selectedSeats)
    {
        if (!"AVAILABLE".equals(seat.getStatus())) {
            throw new SeatUnavailableException("Seat" + seat.getSeat().getSeatNumber() + "Not Available");
        }

        seat.setStatus("Locked");
    }
    showSeatRepository.saveAll(selectedSeats);

  Double  totalAmount=selectedSeats.stream()
                 .mapToDouble(ShowSeat::getPrice)
                 .sum();

      
    //payment
    Payment payment= new Payment();

    payment.setAmount(totalAmount);
    payment.setPaymentTime(LocalDateTime.now());
    payment.setPaymentMethod(bookingRequest.getPaymentMethod());
    payment.setStatus("SUCCESS");
    payment.setTransactionId(UUID.randomUUID().toString());
    


    //booking
    Booking booking =new Booking();

    booking.setUser(user);
    booking.setShow(show);
    booking.setBookingTime(LocalDateTime.now());
    booking.setStatus("CONFIRMED");
    booking.setTotalAmount(totalAmount);
        booking.setBookingNumber(UUID.randomUUID().toString());

    booking.setPayment(payment);

    Booking saveBooking=bookingRepository.save(booking);


    selectedSeats.forEach(seat->{
        seat.setStatus("BOOKED");
        seat.setBooking(saveBooking);
    });


     showSeatRepository.saveAll(selectedSeats);
     return    mapTOBookingDto(saveBooking.selectedSeats);




    }

    public BookingDto geBookingById()

    private BookingDto mapTOBookingDto(Booking booking, List<ShowSeat> seats) {

        BookingDto bookingDto = new BookingDto();
        bookingDto.setID(booking.getId());
        bookingDto.setBookingNumber(booking.getBookingNumber());
        bookingDto.setBookingTime(booking.getBookingTime());
        bookingDto.setTotalAmount(booking.getTotalAmount());

        //
        UserDto userDto = new UserDto();

        userDto.setId(booking.getUser().getID());
        userDto.setName(booking.getUser().getName());
        userDto.setEmail(booking.getUser().getEmail());
        userDto.setPhoneNumber(booking.getUser().getPhoneNumber());

        bookingDto.setUser(userDto);

        ShowDto showdto = new ShowDto();
        showdto.setId(booking.getShow().getId());
        showdto.setStartTime(booking.getShow().getStartTime());
        showdto.setEndTime(booking.getShow().getEndTime());

        MovieDto movieDto = new MovieDto();

        movieDto.setId(booking.getShow().getMovie().getId());
        movieDto.setTitle(booking.getShow().getMovie().getTitle());
        movieDto.setDescription(booking.getShow().getMovie().getDescription());
        movieDto.setLanguage(booking.getShow().getMovie().getLanguage());
        movieDto.setGenre(booking.getShow().getMovie().getGenre());
        movieDto.setDurationMins(booking.getShow().getMovie().getDurationMins());
        movieDto.setReleaseDate(booking.getShow().getMovie().getReleaseDate());
        movieDto.setPourl(booking.getShow().getMovie().getPosterUrl());
        showdto.setMovie(movieDto);

        ScreenDto screenDto = new ScreenDto();

        screenDto.setId(booking.getShow().getScreen().getId());
        screenDto.setname(booking.getShow().getScreen().getName());
        screenDto.setTotalSeats(booking.getShow().getScreen().getTotalSeats());

        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setId(booking.getShow().getScreen().getTheater().getID());
        theaterDto.setName(booking.getShow().getScreen().getTheater().getName());
        theaterDto.setAddress(booking.getShow().getScreen().getTheater().getAddress());
        theaterDto.setCity(booking.getShow().getScreen().getTheater().getCity());
        theaterDto.setTotalScreen(booking.getShow().getScreen().getTheater().getTotalScreen());

        screenDto.setTheater(theaterDto);
        showdto.setScreen(screenDto);
        bookingDto.setShow(showdto);

        List<ShowSeatDto> showseatdto=seats.stream()
                .map(seats->{
                     ShowSeatDto seatdto= new ShowSeatDto();
                     seatDto.setId(seat.getID());
                      seatDto.setStatus(seat.getStatus());
                      seatDto.setPrice(seat.getPrice());


                      SeatDto baseseatDto=new SeatDto();
                      baseseatDto.setId(seat.getSeat().getId());
                       baseseatDto.setSeatNumber(seat.getSeat().getSeatNumber());
                       baseseatDto.setSeatType(seat.getSeat().getSeatType());
                       baseseatDto.setBasePrice(seat.getSeat().getBasePrice());

                       return seatDto;
                       
                })Stream<ShowSeatDto>.collect(Collectors.toList());

                bookingDto.setSeats(showseatdto);


                if(booking.getPayment()!=null){
                       PaymentDto paymentDto=new PaymentDto();

                       paymentDto.setId(booking.getPayment().getId());
                       paymentDto.setAmount(booking.getPayment().getAmount());
                       paymentDto.setPaymentMethod(booking.getPayment().getPaymentMethod());
                       paymentDto.setPaymentTime(booking.getPayment().getPaymentTime());
                       paymentDto.setStatus(booking.getPayment().getStatus());
                       paymentDto.setTransactionId(booking.getPayment().getTransactionId());

                       bookingDto.setPayment(paymentDto);
   return bookingDto;
                }
        

    }
}
