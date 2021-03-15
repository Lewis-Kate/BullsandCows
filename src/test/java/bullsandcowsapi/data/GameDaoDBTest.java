package bullsandcowsapi.data;

import bullsandcowsapi.TestApplicationConfiguration;
import bullsandcowsapi.models.Game;
import bullsandcowsapi.models.Round;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class GameDaoDBTest {
    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    public GameDaoDBTest() {

    }

    @BeforeEach
    public void setUp() {

        roundDao.deleteRound();
        gameDao.deleteGame();

}



    @Test
    public void testGetAllGames() {
        //roundDao.deleteRound();
        //gameDao.deleteGame();


        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Game game2 = new Game();
        game2.setAnswer("5678");
        game2.setFinished(false);
        game2 = gameDao.addGame(game2);

        List<Game> games = gameDao.getAllGames();

        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
    }

    @Test
    public void testGetGameById() {
        Game game = new Game();
        game.setGameId(5);
        game.setAnswer("1234");
        game.setFinished(false);
        gameDao.addGame(game);

        Game game2 = new Game();
        game.setGameId(6);
        game.setAnswer("5678");
        game.setFinished(false);
        gameDao.addGame(game);

        Game fromDao  = gameDao.getGameById(game.getGameId());
        assertEquals(game, fromDao);

    }

    @Test
    public void testAddGetGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Game fromDao = gameDao.getGameById(game.getGameId());
        assertEquals(game, fromDao);
    }

    @Test
    public void testUpdateGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Game fromDao = gameDao.getGameById(game.getGameId());

        assertEquals(game, fromDao);

        game.setFinished(true);

        gameDao.updateGame(game);

        assertNotEquals(game, fromDao);

        fromDao = gameDao.getGameById(game.getGameId());

        assertEquals(game, fromDao);
    }


}