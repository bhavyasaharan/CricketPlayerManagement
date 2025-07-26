package com.example.demo.service;

import com.example.demo.constants.Constants;
import com.example.demo.dto.MemberDto;
import com.example.demo.dto.Reply;
import com.example.demo.entity.Member;
import com.example.demo.general.GeneralMethods;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.models.MemberFilterModel;
import com.example.demo.repository.MemberRepository;
import com.example.demo.specification.MemberSpecification;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MemberService {
    private final MemberRepository memberRepository ;
    private final MemberMapper memberMapper;


    public MemberService(MemberMapper memberMapper,MemberRepository memberRepository) {
        this.memberMapper = memberMapper;
        this.memberRepository =memberRepository;

    }


    // done
    public Reply<List<MemberDto>> getMember(Member member, MemberFilterModel memberFilterModel){
        Reply<List<MemberDto>> reply  = new Reply<>();

        switch(member.getMemberType()){
            // show all admin and Manager to all superadmin
            case 1 : // for superAdmin
                List<Integer> allowedType = List.of(Constants.MemberType.ADMIN , Constants.MemberType.MANAGER);

                List<Integer> filterMemberTypeList = memberFilterModel.getMemberType().stream().filter(allowedType::contains).toList() ;
                memberFilterModel.setMemberType( !filterMemberTypeList.isEmpty() ? filterMemberTypeList :  Collections.singletonList(-1)); // need to update MemberSpecification also
               // filter the admin and manager seperately in controller accordingly
                memberFilterModel.setActiveStatus(true);
                break ;

            // show only assigned managers to admin
            case 2 : // for Admin
                memberFilterModel.setMemberType(Collections.singletonList(Constants.MemberType.MANAGER));
                allowedType = List.of( Constants.MemberType.MANAGER);

                filterMemberTypeList = memberFilterModel.getMemberType().stream().filter(allowedType::contains).toList() ;
                memberFilterModel.setMemberType( !filterMemberTypeList.isEmpty() ?filterMemberTypeList :  Collections.singletonList(-1)); // need to update MemberSpecification also

                memberFilterModel.setActiveStatus(true);
                memberFilterModel.setReportingTo(Collections.singletonList(member.getMemberId()));
                break;
/*
            case 3 :// for member to see there own profiles
                memberFilterModel.setMemberId(member.getMemberId());*/

            default:
                reply.setMessage("NO Member Found");
                return reply ;


        }
        //----------------

        List<Member> members = memberRepository.findAll(MemberSpecification.build(memberFilterModel));
      //  memberDtos = CopyObject.copyObject(members,memberDtos);
      //  GeneralMethods.copyObject(members,memberDtos);
        if (members.isEmpty())
            reply.setMessage("NO Matched member found");

        reply.setData(memberMapper.toDtoList(members));
        return reply ;
    }





    // create update both admin and member
    public Reply<List<Member>> createUpdateMember(List<Member> members , boolean create  ){

        Reply<List<Member>> reply =new Reply<>() ;


        List<String> existingMemberMobileList = memberRepository.findByMemberTypeIn(List.of(Constants.MemberType.MANAGER,Constants.MemberType.ADMIN))
                .stream().map(Member::getPhoneNo).toList() ;


        List<Member> savedMemberList = new ArrayList<>();
        List<Member> unsavedMemberList = new ArrayList<>();

        List<Integer> allowedTypes = List.of(Constants.MemberType.SUPER_ADMIN, Constants.MemberType.ADMIN, Constants.MemberType.MANAGER);

        Map<String,Object> errorMap =new HashMap<>();
        // create member
        if (create){
            for(Member member : members){
                if( existingMemberMobileList.contains(member.getPhoneNo())  ){
                    errorMap.put("duplicate id : "+member.getPhoneNo(), member);
                    if(!allowedTypes.contains(member.getMemberType()) )
                        errorMap.put("wrong member Type: "+member.getPhoneNo() , member);
                        unsavedMemberList.add(member);

                }else{

                    savedMemberList.add(member);
                }
            }
        }else{
            // for update members
            for(Member updatedMember : members){
                if( existingMemberMobileList.contains(updatedMember.getPhoneNo()) && allowedTypes.contains(updatedMember.getMemberType() != null ? updatedMember.getMemberType() : 0 )  ){
                    Member savedMember= memberRepository.findByPhoneNoAndMemberTypeIn(updatedMember.getPhoneNo(),List.of(Constants.MemberType.SUPER_ADMIN,
                            Constants.MemberType.ADMIN, Constants.MemberType.MANAGER));
                    updatedMember.setMemberId(savedMember.getMemberId());
                    GeneralMethods.copyObject(updatedMember,savedMember);
                    savedMemberList.add(savedMember) ;
                }else{
                    unsavedMemberList.add(updatedMember);
                }
            }
        }
        try{
            memberRepository.saveAll(savedMemberList);
        }catch (Exception e){
            System.err.println(e);
            reply.setMessage("Something went wrong with the data while saving it ");
            return reply ;
        }

        reply.setMessage(unsavedMemberList.size() + " records failed to save ");
        reply.setData(unsavedMemberList);

        return reply ;
    }





    public Reply< Map<String, List<Integer>>> deleteMember(List<Integer> memberIds){
        Reply<Map<String, List<Integer>>> reply = new Reply<>() ;

        List<Integer> existingMemberIds = memberRepository.findByMemberTypeIn(List.of(Constants.MemberType.MANAGER,Constants.MemberType.ADMIN))
                .stream().map(Member::getMemberId).toList() ;


        List<Integer> deletedAdminList = new ArrayList<>();
        List<Integer> unsavedAdminList = new ArrayList<>();

        for(Integer id :memberIds){
            if( existingMemberIds.contains(id) ){
                deletedAdminList.add(id);
            }else{
                unsavedAdminList.add(id);// list of those admins which not found in db
            }
        }
        memberRepository.deleteAllById(deletedAdminList);

        Map<String,Object> result = new HashMap<>();
        result.put("successful",deletedAdminList );
        result.put("unsuccessful", unsavedAdminList);

        reply.setOtherInfo(result);

        return reply ;
    }


}
