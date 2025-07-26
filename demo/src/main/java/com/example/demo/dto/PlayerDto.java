package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class PlayerDto {

    private Integer playerId ;
    private String playerName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth ;
    private String country ;
    private String testMatch;
    private String odi ;
    private String t20 ;
    private Integer assignedTo ;

}
