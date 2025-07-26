package com.example.demo.mapper;


import com.example.demo.dto.MemberDto;
import com.example.demo.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;


//to convert membertbl to dto
@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberDto toDto(Member member);
    List<MemberDto> toDtoList(List<Member> members);
}


