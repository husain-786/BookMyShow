package com.bookMyShow.entities;

import com.bookMyShow.enums.SeatType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "showseats")
@Data
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String seatNo;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private int price;      // vary with type of seat,,.....

    private boolean isAvailable;

    private boolean isFoodAttached;

    @ManyToOne
    @JoinColumn
    private Show show;
}
