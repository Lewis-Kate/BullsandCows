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
class RoundDaoDBTest {

    @Autowired
    RoundDao roundDao;

    @Autowired
    GameDao gameDao;

    public RoundDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {

            roundDao.deleteRound();
            gameDao.deleteGame();
    }

    @Test
    public void testGetAllRounds(){

        //roundDao.deleteRound();
        //gameDao.deleteGame();

        Game game = new Game();
        game.setAnswer("5678");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGuess("1234");
        round.setResult("e:0:p:0");
        round.setGameId(game.getGameId());
        round = roundDao.addRound(round);

        Round round2 = new Round();
        round2.setGuess("5678");
        round2.setResult("e:4:p:0");
        round2.setGameId(game.getGameId());
        round2 = roundDao.addRound(round2);

        List<Round> rounds = roundDao.getAllRoundsByGameId(game.getGameId());

        assertEquals(2, rounds.size());
        assertTrue(rounds.contains(round));
        assertTrue(rounds.contains(round2));

    }

    @Test
    public void testGetAllRoundsByGameId() {
        //roundDao.deleteRound();
        //gameDao.deleteGame();

        Game game = new Game();
        game.setAnswer("5678");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGuess("1234");
        round.setResult("e:0:p:0");
        round.setGameId(game.getGameId());
        round = roundDao.addRound(round);

        Round round2 = new Round();
        round2.setGuess("5678");
        round2.setResult("e:4:p:0");
        round2.setGameId(game.getGameId());
        round2 = roundDao.addRound(round2);

        List<Round> rounds = roundDao.getAllRoundsByGameId(game.getGameId());

        assertEquals(2, rounds.size());
        assertNotNull(round = roundDao.getRoundById(round.getRoundId()));
    }

    @Test
    public void testGetRoundById() {
        //roundDao.deleteRound();
        //gameDao.deleteGame();

        Game game = new Game();
        game.setAnswer("5678");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGuess("1234");
        round.setResult("e:0:p:0");
        round.setGameId(game.getGameId());
        round = roundDao.addRound(round);

        Round round2 = new Round();
        round2.setGuess("5678");
        round2.setResult("e:4:p:0");
        round2.setGameId(game.getGameId());
        round2 = roundDao.addRound(round2);


        Round fromDao = roundDao.getRoundById(round.getRoundId());
        assertEquals(round, fromDao);
    }

    @Test
    public void testAddRound() {
        //roundDao.deleteRound();
        //gameDao.deleteGame();


        Game game = new Game();
        game.setAnswer("5678");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGuess("5678");
        round.setResult("e:4:p:0");
        round.setGameId(game.getGameId());
        round = roundDao.addRound(round);

        Round fromDao = roundDao.getRoundById(round.getRoundId());
        assertEquals(round, fromDao);

    }
}