package bullsandcowsapi.controller;


import bullsandcowsapi.models.Game;
import bullsandcowsapi.models.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("/guessapi")
public class Controller {
    @Autowired
    GameRules game;


    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int createGame() {

        return game.newGame();
    }

    @PostMapping("/guess")
    public Round makeGuess(@RequestBody Round round) {

        return game.makeGuess(round);
    }

    @GetMapping("/game")
    public List<Game> getAllGames() {
        return game.getAllGames();
    }

    @GetMapping("/game/{game_id}")
    public Game getGameById(@PathVariable("game_id") int gameId) {

        return game.getGameById(gameId);
    }

    @GetMapping("/rounds/{game_id}")
    public List<Round> getRoundsForGame(@PathVariable("game_id") int gameId) {
        return game.getAllRoundsByGameId(gameId);
    }

}