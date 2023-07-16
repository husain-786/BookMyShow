package com.bookMyShow.services;

import com.bookMyShow.dtos.requestDtos.TicketRequestDto;
import com.bookMyShow.dtos.responseDtos.TicketResponseDto;
import com.bookMyShow.entities.Show;
import com.bookMyShow.entities.ShowSeat;
import com.bookMyShow.entities.Ticket;
import com.bookMyShow.entities.User;
import com.bookMyShow.exceptions.ShowNotFoundException;
import com.bookMyShow.exceptions.UserNotFoundException;
import com.bookMyShow.repositories.ShowRepository;
import com.bookMyShow.repositories.TicketRepository;
import com.bookMyShow.repositories.UserRepository;
import com.bookMyShow.transformers.TicketTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    public TicketResponseDto bookTicket(TicketRequestDto ticketRequestDto) throws Exception, UserNotFoundException, ShowNotFoundException  {
        // checking whether the user is present or not.....
        Optional<User> userOptional = userRepository.findById(ticketRequestDto.getUserId());
        if (userOptional.isEmpty()){
            throw new UserNotFoundException("User details does not found");
        }
        User user = userOptional.get();

        // checking whether the show is present or not......
        Optional<Show> optionalShow = showRepository.findById(ticketRequestDto.getShowId());
        if (optionalShow.isEmpty()){
            throw new ShowNotFoundException("Show Details Not Found");
        }
        Show show = optionalShow.get();

        // Validations for the requested seats, whether the requestd seats are present or not....
        Boolean isValid = validateRequestedSeatsAvailability(show, ticketRequestDto.getRequestedSeats());
        if (!isValid){
            throw new Exception("Requested Seats are not available");
        }

        // converting booked seats into simple string....
        String bookedSeats = TicketTransformer.convertRequestedSeatsToString(ticketRequestDto.getRequestedSeats());

        // calculating the total ticket price.......
        Integer ticketPrice = calculateTotalPrice(show, ticketRequestDto.getRequestedSeats());
        System.out.println("ticketPrice: "+ticketPrice);
        // calculating the total price including meal.......
        Integer totalPrice = (ticketRequestDto.getHavaMeal())? (((ticketRequestDto.getMealPrice())*(ticketRequestDto.getRequestedSeats().size()))+ticketPrice): ticketPrice;

        // creating Ticket Entity......
        Ticket ticket = Ticket.builder()
                .bookedSeats(bookedSeats)
                .totalTicketPrice(totalPrice)
                .build();
        ticket = ticketRepository.save(ticket);
        //setting foreign key attributes.....
        ticket.setUser(user);
        ticket.setShow(show);

        // Doing bidirectional mapping.......
        user.getTicketList().add(ticket);
        show.getTicketList().add(ticket);
        userRepository.save(user);
        showRepository.save(show);

        // Converting EntityToDto for sending the required data to the client or user......
        TicketResponseDto ticketResponseDto = TicketTransformer.convertEntityToDto(show, ticket);

        // Sending mailTo the user.............
        sendingMail(user, ticketResponseDto);
        return ticketResponseDto;
    }

    public Boolean validateRequestedSeatsAvailability(Show show, List<String> requestedSeats){
        List<ShowSeat> showSeatList = show.getShowSeatList();

        // storing all seats at one place....
        List<String> listOfAvailableSeatNumbers= new ArrayList<>();
        for (ShowSeat showSeat: showSeatList){
            listOfAvailableSeatNumbers.add(showSeat.getSeatNo());
        }

        // checking whether the requested seats are withing the all seats or not.....
        for (String requestedSeat: requestedSeats){
            if (!listOfAvailableSeatNumbers.contains(requestedSeat)){
                return false;
            }
        }

        // checking whether the requested seats are currently available or not.......
        for (ShowSeat showSeat: showSeatList){
            if (requestedSeats.contains(showSeat.getSeatNo()) && !showSeat.isAvailable()){
                return false;
            }
        }

        return true;
    }

    public Integer calculateTotalPrice(Show show, List<String> requestedSeats){
        List<ShowSeat> showSeatList = show.getShowSeatList();
        int totalPrice = 0;
        for (ShowSeat showSeat: showSeatList){
            if (requestedSeats.contains(showSeat.getSeatNo())){
                totalPrice += showSeat.getPrice();
                // setting status of ticket to booked i.e., making isAvailable = true.......
                showSeat.setAvailable(false);
            }
        }
        return totalPrice;
    }

    public void sendingMail(User user,  TicketResponseDto ticketResponseDto){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        // creating mail body.....
        String body = "Hi, " + user.getName() + ",\n" + "\nYou have successfully booked a ticket. "
                + "Please find the follwing details below \n\n"
                + ticketResponseDto.toString()
                + "\n\nEnjoy the show!!!";

        simpleMailMessage.setSubject("Ticket Confirmed");
        simpleMailMessage.setText(body);
        simpleMailMessage.setFrom("husainnetwin@gmail.com");
        //mailMessage.setTo(user.getEmailId());
        simpleMailMessage.setTo("sajjad.husain.s.p@gmail.com");

        mailSender.send(simpleMailMessage);
    }
}
