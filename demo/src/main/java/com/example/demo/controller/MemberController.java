package com.example.demo.controller;

import com.example.demo.constants.Constants;
import com.example.demo.customUserDetails.CustomUserDetails;
import com.example.demo.dto.MemberDto;
import com.example.demo.dto.Reply;
import com.example.demo.entity.Member;
import com.example.demo.general.GeneralMethods;
import com.example.demo.models.MemberFilterModel;
import com.example.demo.models.PlayerFilterModel;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService ;

    // get mapping to get members
    @PreAuthorize("hasRole('superAdmin','admin','manager')")
    @PostMapping("/get")
    public ResponseEntity<?> getMember(Authentication authentication, @RequestBody(required = false)  MemberFilterModel memberFilterModel){
        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();



        if (memberFilterModel == null || memberFilterModel.isEmpty() ){ // means user wants to view its profile
            MemberDto memberDto = new MemberDto() ;
            GeneralMethods.copyObject(member,memberDto);
            return ResponseEntity.ok(memberDto) ;

        }else { // for filter or to get some records from the member_tbl
            Reply<List<MemberDto>>  reply = memberService.getMember(member , memberFilterModel);

            if(reply.getData()== null && reply.getData().isEmpty()){
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(reply.getMessage());
            }else{
                return ResponseEntity.ok(reply.getData());
            }
        }

    }

    @PreAuthorize("hasRole('superAdmin','admin','manager')")
    @PostMapping("/createUpdate")
    public ResponseEntity<?> createUpdateMember(@RequestParam(required = false)  boolean create,@RequestBody List<Member> members , Authentication authentication){
        Member loginMember = ((CustomUserDetails) authentication.getPrincipal()).getMember();

        Reply<List<Member>> reply = new Reply<>();
        if (loginMember.getMemberType()== Constants.MemberType.SUPER_ADMIN )
           reply =  memberService.createUpdateMember(members ,create) ;
        else {
            // this check use used to update own profile by the members
            if(members.size()==1 && loginMember.getPhoneNo().equals(members.get(0).getPhoneNo()  ) && !create ){
                reply = memberService.createUpdateMember(members ,create) ;
            }
            else {
                reply.setMessage("Error while updating the data ");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reply.getMessage());

            }
        }

        return  reply.getData()==null   ? ResponseEntity.status(HttpStatus.CONFLICT).body(reply.getMessage())// IF TRUE
                                        : reply.getData().isEmpty() // ELSE
                                                    ? ResponseEntity.ok("Data saved successfully ") // if true
                                                    : ResponseEntity.status(HttpStatus.CONFLICT).body(reply.getMessage() + reply.getData());

    }


    @PreAuthorize("hasRole('superAdmin')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMember(@RequestParam List<Integer> ids) {
        Reply<?> reply = new Reply<>();
        reply = memberService.deleteMember(ids);

        return reply.getOtherInfo().get("successful") == null
                ? ResponseEntity.status(HttpStatus.CONFLICT).body(" 0 data deleted")
                : ResponseEntity.ok("Data deleted successfully ");


    }

}
