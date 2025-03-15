package com.example.demo.oldPlayerController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.example.demo.playerEntity.Player;
import com.example.demo.playerService.PlayerService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/players")
public class OldPlayerController {

    @Autowired
    private PlayerService playerService ;

    @GetMapping
    public List<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    @GetMapping("/name/{name}")
    public List<Player> getPlayerByName(@PathVariable String name){
        return playerService.getPlayerByName(name);
    }

    @GetMapping("/id/{id}")
    public Player getPlayerById(@PathVariable int id){
        return playerService.getPlayerById(id);
    }

    @GetMapping("/country/{country}")
    public List<Player> getPlayerByCountry(@PathVariable String country){
        return playerService.getPlayerByCountry(country);

    }


    @GetMapping("/find/{dob}/{country}")
    // need to correct this method
    public List<Player> getByDOBandCountry(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob, @PathVariable String country){
        return playerService.getByDOBandCountry(dob,country);
    }


    /***************post mapping***********************/

    @PostMapping()
    public Player addPlayers(@RequestBody Player player){
        return playerService.addPlayer(player);
    }

    @PostMapping("/update")
    public Boolean updatePlayer(@RequestBody List<Player> players){
        return  playerService.updatePlayer(players);
    }

    @PostMapping("/delete")
    public String deletePlayer(@RequestParam(required = false) Integer id,
                               @RequestParam(required = false) String name) {
        return playerService.deletePlayer(id,name);
    }

}
