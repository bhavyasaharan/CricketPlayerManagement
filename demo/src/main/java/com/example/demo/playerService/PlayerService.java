package com.example.demo.playerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.playerEntity.Player;
import com.example.demo.playerRepo.PlayerRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    // get methods

    public List<Player> getAllPlayers(){
        return playerRepository.findAll();

    }

    public List<Player> exactMatch(Integer id ,String name, String country){
        List<Player> players = playerRepository.fetchExactPlayer(id,name,country);
        if(players.isEmpty()){
            return null ;
        }else{
            return players ;
        }
    }

    // for this we can also use data base query
    //@Transactional(readOnly = true)
    public List<Player> getPlayerByName(String name){
        List<Player> arr= playerRepository.findAll().stream()
                .filter(p -> name.equals(p.getPlayerName()) )
                .collect(Collectors.toList());

        return arr;
    }

    public Player getPlayerById(int id){
        Optional<Player> opt =  playerRepository.findById(id);
        Player playerById =new Player() ;

        if(opt.isPresent()){
            playerById = opt.get() ;
            return playerById;
        }else return playerById;

    }

    public List<Player> getPlayerByCountry(String country){

        return playerRepository.findByCountry(country) ;
    }

    public List<Player> getByDOBandCountry(LocalDate date, String country){
         return playerRepository.findAll()
                .stream()
                .filter(pl-> date.equals(pl.getDate_of_birth()) && country.equals(pl.getCountry()))
                 .collect(Collectors.toList());
         // .collect(Collectors.toList()) → Converts the filtered players back to a List<Player>.
         //  without .collect(), you cannot directly access or iterate over the elements as a list.
         // Lazy Execution – Operations are only performed when a terminal operation (like .collect(), .forEach(), .count()) is called.


    }



    // add and update methods

    public Player addPlayer(Player player){
        Optional<Player> optionalPlayer = playerRepository.findById(player.getId());
        if (optionalPlayer.isPresent())
            return null ;
        else {
            return playerRepository.save(player) ;
        }

    }

    public Boolean updatePlayer(List<Player> existedPlayers){

        boolean flag1=false ;
        for(Player existedPlayer : existedPlayers){
            flag1= true;
            Optional<Player> optionalExistingPlayer = playerRepository.findById(existedPlayer.getId()) ;
            if(optionalExistingPlayer.isPresent()){
                Player updatedPlayer = optionalExistingPlayer.get();
                updatedPlayer.setPlayerName(existedPlayer.getPlayerName());
                updatedPlayer.setDate_of_birth(existedPlayer.getDate_of_birth());

                playerRepository.save(updatedPlayer) ;

              //
            }else {
                System.out.println("Player with id = " + existedPlayer.getId() +" Not Exist");
            }

        }
        if (flag1==true)
                return true ;
        else return false ;

    }

    // instead of this way of deletion we can also make to different methods ,or we can override the method (for this we need two methods)
    public String deletePlayer(Integer id , String name ){// id is assigned as  wrapper class to compare it as null
        if(id==null ) {
            List<Player> playerByName = playerRepository.findByPlayerName(name);

            if (playerByName.size() == 1) {
                Optional<Player> p = playerRepository.findById(playerByName.get(0).getId());
                playerRepository.deleteById(playerByName.get(0).getId());
                return "Player with id = " + p.get().getId() + "and name = " + p.get().getPlayerName() + "deleted successfully";

            } else {
                // generally printing list by this way return only the instance of the list but as we have override the
                // toString() method for the Player it will return the List values as mentioned in the method
                System.out.println(playerRepository.findByPlayerName(name));
                return "More than one player is found with the given name";
            }
        } else if (id != null ) {
            playerRepository.deleteById(id);
            return "Player with id = " + id + "deleted Successfully";
        }

        return "Try again with the correct id or name.....DELETION FAILED " ;

    }
}
