package bullsandcowsapi.data;

import bullsandcowsapi.models.Game;

import java.util.List;

public interface GameDao {
    List<Game> getAllGames();
    Game getGameById(int gameId);
    Game addGame(Game game);
    void updateGame(Game round);
    void deleteGame();
}
