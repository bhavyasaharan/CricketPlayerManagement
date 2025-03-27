package com.example.demo.playerController;

import com.example.demo.playerEntity.Player;
import com.example.demo.playerService.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
public class PlayerController {

    @Autowired
    private PlayerService playerService ;

    @GetMapping
    public ResponseEntity<?> getPlayer(@RequestParam(required = false) Integer id ,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String country

    ){


        if(id != null && name != null && country != null){
            List<Player> players= playerService.exactMatch(id,name,country);
            return players.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Database is empty")
                    : ResponseEntity.ok(players);
        } else if (id != null) {
           Player player= playerService.getPlayerById(id);
            System.out.println("player = "+ player);
           return player.getId()==0
                   ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player don't exist with id = "+id)
                   : ResponseEntity.ok(player);
        } else if (name != null) {
            List<Player> players= playerService.getPlayerByName(name);
            return players.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No match found with this name")
                    : ResponseEntity.ok(players);
        } else if (country != null) {
            List<Player> players= playerService.getPlayerByCountry(country);
            return players.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No match found from this country")
                    : new ResponseEntity<>(players,HttpStatus.OK);
        } else if ((id == null && name == null && country == null)) {
//            Page<Player> players=playerService.getAllPlayers(pageable);
//            return players.isEmpty()
//                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty DB")
//                    : ResponseEntity.ok(players);

            List<Player> players=playerService.getAllPlayers();
            return players.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty DB")
                    : ResponseEntity.ok(players);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("try again with correct credentials");

    }

    @PostMapping
    public ResponseEntity<?> addPlayers(@RequestBody Player player){

         Player p = playerService.addPlayer(player);
         if (p== null){
             return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Player with the given id already exist");
         }else {
             return ResponseEntity.status(HttpStatus.OK).body(p+ " -> Added successfuly ");
         }

    }


    @PutMapping
    public ResponseEntity<?> updatePlayer(@RequestBody List<Player> players){

       Boolean p = playerService.updatePlayer(players);
       return p
               ? ResponseEntity.ok("Updated successfully ")
               : (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NOT_FOUND).body("playernotfounfd");
    }

    @DeleteMapping
    public ResponseEntity<?> deletePlayer(@RequestParam(required = false) Integer id,
                               @RequestParam(required = false) String name) {
        String str = playerService.deletePlayer(id,name);
        return ResponseEntity.ok(str);
    }
}
