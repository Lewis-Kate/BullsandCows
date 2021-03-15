package bullsandcowsapi.data;

import bullsandcowsapi.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GameDaoDB implements GameDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Game> getAllGames(){
        final String SELECT_ALL_GAMES = "SELECT * FROM game";
        return jdbc.query(SELECT_ALL_GAMES, new GameMapper());
    }

    @Override
    public Game getGameById(int gameId) {
        try {
            final String SELECT_GAME_BY_ID = "SELECT * FROM game WHERE game_id = ?";
            return jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), gameId);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public Game addGame(Game game) {
        final String INSERT_GAME = "INSERT INTO game(answer) VALUES (?)";
        jdbc.update(INSERT_GAME, game.getAnswer());

        int newGameId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newGameId);
        return game;
    }

    @Override
    public void updateGame(Game game) {
        final String UPDATE_GAME = "UPDATE game SET finished = ? WHERE game_id = ?";
        jdbc.update(UPDATE_GAME, game.isFinished(), game.getGameId());
    }

    @Override
    public void deleteGame(){
        final String ALTER_TABLE = "ALTER TABLE game AUTO_INCREMENT = 1";
        jdbc.execute(ALTER_TABLE);

        final String DELETE_GAME = "DELETE FROM game WHERE game_id < 1000";
        jdbc.update(DELETE_GAME);
    }


    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("game_id"));
            game.setAnswer(rs.getString("answer"));
            game.setFinished(rs.getBoolean("finished"));
            return game;
        }
    }

}