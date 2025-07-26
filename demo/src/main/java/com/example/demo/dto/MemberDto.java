package com.example.demo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class MemberDto {

    private int memberId;
    private String userName;
    private String name;
    @Length(max = 10)
    private String phoneNo;
    private Integer memberType;
    private Integer reportingTo ;
    private Boolean activeStatus ;


}
