package bullsandcowsapi.data;

import bullsandcowsapi.models.Game;
import bullsandcowsapi.models.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class RoundDaoDB implements RoundDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Round> getAllRounds(){
        final String SELECT_ALL_ROUND = "SELECT * FROM round";
        return jdbc.query(SELECT_ALL_ROUND, new RoundDaoDB.RoundMapper());
    }

    @Override
    public List<Round> getAllRoundsByGameId(int gameId) {
        try {
            final String SELECT_ROUNDS_BY_GAMEID = "SELECT * FROM round "
                    + "WHERE game_id = ? ORDER BY guess_time";
            List<Round> rounds = jdbc.query(SELECT_ROUNDS_BY_GAMEID, new RoundMapper(), gameId);
            return rounds;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Round getRoundById(int roundId) {
        try {
            final String SELECT_ROUND_BY_ID = "SELECT * FROM round WHERE round_id = ?";
            return jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), roundId);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public Round addRound(Round round) {
        final String INSERT_ROUND = "INSERT INTO round(game_id, guess, result) VALUES(?,?,?)";
        jdbc.update(INSERT_ROUND, round.getGameId(), round.getGuess(), round.getResult());

        int newRoundId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newRoundId);
        return getRoundById(newRoundId);
    }

    @Override
    public void deleteRound(){
        final String ALTER_TABLE ="ALTER TABLE round AUTO_INCREMENT = 1";
        jdbc.execute(ALTER_TABLE);

        final String DELETE_ROUND = "DELETE FROM round WHERE game_id < 1000";
        jdbc.update(DELETE_ROUND);
    }

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("round_id"));
            round.setGameId(rs.getInt("game_id"));
            round.setGuess(rs.getString("guess"));

            Timestamp timestamp = rs.getTimestamp("guess_time");
            round.setGuessTime(timestamp.toLocalDateTime());

            round.setResult(rs.getString("result"));
            return round;
        }
    }

}