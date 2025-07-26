package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PlayerFilterModel {

    private List<Integer> playerId ;
    private String playerName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth ;
    private List<String> country ;
    private String testMatch;
    private String odi ;
    private String t20 ;
    private List<Integer> assignedTo ;


    public boolean isEmpty() {
        return (playerId == null || playerId.isEmpty()) &&
                (playerName == null || playerName.isBlank()) &&
                (dateOfBirth == null ) &&
                odi == null &&
                (t20 == null || t20.isEmpty()) &&
                (country == null || country.isEmpty()) &&
                (assignedTo == null || assignedTo.isEmpty()) &&
                (testMatch == null || testMatch.isEmpty()) ;
    }
}
