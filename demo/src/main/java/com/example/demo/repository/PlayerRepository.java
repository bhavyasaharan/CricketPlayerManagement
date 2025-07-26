package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Integer> {

    @Override
    Page<Player> findAll(Pageable pageable);

   // Optional<Player> findByPlayerNameAndDateOfBirth(String playerName , String country );
    Optional<Player> findByIdAndAssignedTo(int Playerid, Integer managerid);
    List<Player> findByCountry(String country) ;
    List<Player> findByPlayerName(String name);
    List<Player> findByAssignedTo(int assignedManager);
    List<Player> findByPlayerNameAndAssignedTo(String name,int id);

   //  @Query(value = "SELECT * FROM cricketers WHERE date_of_birth = :dobParam", nativeQuery = true)
   // List<Player> fetchByDob(@Param("dobParam") Date birthDate);

    @Query(value = "select * from cricketers where id=:id and name= :Pname and country =:Pcountry", nativeQuery = true)
    List<Player> fetchExactPlayer(@Param("id") Integer id , @Param("Pname") String name , @Param("Pcountry") String country);
}
