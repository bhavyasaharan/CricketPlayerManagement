package com.example.demo.service;

import com.example.demo.customUserDetails.CustomUserDetails;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImplementation implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = null ;
        try{
            member = memberRepository.findByUserName(username);
        }
        catch (Exception e){
            System.err.println("Exception while fetching the data for authentication,***** Member Not Found ****");
        }

        if (member != null){
            // these line of code is need when u dont have the customUserDetails class ,which is customized according to get the details of your user/entity that u need
            /*
            String pass = member.getPassword();
            String rol = String.valueOf(member.getUserType());

            UserDetails userDetails = User.builder().username(member.getUserName()).password(pass)
                    .roles(rol)
                    .build();*/

            return new CustomUserDetails(member);
        }

        throw new UsernameNotFoundException("Member not found ");

    }
}
