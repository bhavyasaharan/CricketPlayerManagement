package com.example.demo.models;

import com.example.demo.entity.Member;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class MemberFilterModel {

   // private Member member ;

    private List<Integer> memberId;
    private String userName;
    private String name;
    @Length(max = 10)
    private String phoneNo;
    private List<Integer> memberType;
    private List<Integer> reportingTo ;
    private Boolean activeStatus ;


    public boolean isEmpty() {
        return (memberId == null || memberId.isEmpty()) &&
                (userName == null || userName.isBlank()) &&
                (name == null || name.isBlank()) &&
                phoneNo == null &&
                (memberType == null || memberType.isEmpty()) &&
                (reportingTo == null || reportingTo.isEmpty()) &&
                activeStatus == null;
    }
}
