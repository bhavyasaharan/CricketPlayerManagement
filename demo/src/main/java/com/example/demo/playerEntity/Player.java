package com.example.demo.playerEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
//import lombok.Data;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Data
@Table(name = "cricketers")
public class Player {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @Column(name = "PlayerName")
    private String playerName;
    @Column(name = "Date_Of_Birth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date_of_birth ;
    @Column(name = "Country")
    private String country ;
    @Column(name = "TestMatch")
    private String testMatch;
    @Column(name = "ODI")
    private String odi ;
    @Column(name = "T20")
    private String t20 ;

    @Override
    // this is written to print the list using sout withod any need of loop in the player service class for the deletion method
    public String toString(){
        return "Player -> {" +
                "id=" + id +
                ", playerName='" + playerName + '\'' +
                ", date_of_birth=" + date_of_birth +
                ", country='" + country + '\'' +
                ", testMatch='" + testMatch + '\'' +
                ", odi='" + odi + '\'' +
                ", t20='" + t20 + '\'' +
                '}';
    }
}


