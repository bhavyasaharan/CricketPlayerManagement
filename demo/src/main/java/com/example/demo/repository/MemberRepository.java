package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member,Integer>, JpaSpecificationExecutor<Member> {

    List<Member> findByMemberType(int userType);

    Member findByUserName(String userName);

    Member findByPhoneNo(String mobile);

    Member findByPhoneNoAndMemberTypeIn(String phoneNo , List<Integer> adminManagerTypeList);
    List<Member> findByMemberTypeIn(List<Integer> adminManagerTypeList );

    @Query(value = "delete from user_tbl where userId in :ids ", nativeQuery = true)
    List<Member> deleteById(  @Param("ids") List<Integer> ids) ;


    /*
    *  @Query(value = "select * from cricketers where id=:id and name= :Pname and country =:Pcountry", nativeQuery = true)
    List<Player> fetchExactPlayer(@Param("id") Integer id , @Param("Pname") String name , @Param("Pcountry") String country);*/
}
