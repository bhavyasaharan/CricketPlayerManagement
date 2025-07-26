package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
//import lombok.Data;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "players_tbl")
public class Player {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId ;

    @Column(name = "PlayerName")
    private String playerName;
    @Column(name = "Date_Of_Birth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth ;
    @Column(name = "Country")
    private String country ;
    @Column(name = "TestMatch")
    private String testMatch;
    @Column(name = "ODI")
    private String odi ;
    @Column(name = "T20")
    private String t20 ;
    @Column(name = "AssignedTo")
    private Integer assignedTo ;

    @Override
    // this is written to print the list using sout withod any need of loop in the player service class for the deletion method
    public String toString(){
        return "Player -> {" +
                "id=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", date_of_birth=" + dateOfBirth +
                ", country='" + country + '\'' +
                '}';
    }
}


