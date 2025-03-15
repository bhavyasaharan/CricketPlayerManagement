package com.example.demo.playerRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.playerEntity.Player;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Integer> {

    List<Player> findByCountry(String country) ;
    List<Player> findByPlayerName(String name);

   //  @Query(value = "SELECT * FROM cricketers WHERE date_of_birth = :dobParam", nativeQuery = true)
   // List<Player> fetchByDob(@Param("dobParam") Date birthDate);

    @Query(value = "select * from cricketers where id=:id and name= :Pname and country =:Pcountry", nativeQuery = true)
    List<Player> fetchExactPlayer(@Param("id") Integer id , @Param("Pname") String name , @Param("Pcountry") String country);
}
