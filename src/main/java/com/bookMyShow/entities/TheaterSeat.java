package com.bookMyShow.entities;

import com.bookMyShow.enums.SeatType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "theaterseats")
@Data
public class TheaterSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String seatNo;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @ManyToOne
    @JoinColumn
    private Theater theater;
}
