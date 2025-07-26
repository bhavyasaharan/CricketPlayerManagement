package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@Table(name = "member_tbl")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;
    @Column(nullable = false, unique = true)
    private String userName;
    private String name;
    private String password;
    @Column(name = "phoneNo", length = 10,nullable = false, unique = true)
    private String phoneNo;
    private Integer memberType;
    private Integer reportingTo ;
    private Boolean activeStatus ;


    public void setUserId(int userId) {
        if (userId == 0)
            this.memberId = userId;
    }

}
