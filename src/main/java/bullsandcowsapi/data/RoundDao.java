package bullsandcowsapi.data;

import bullsandcowsapi.models.Round;

import java.util.List;

public interface RoundDao {
    List<Round> getAllRounds();
    List<Round> getAllRoundsByGameId(int gameId);
    Round getRoundById(int roundId);
    Round addRound(Round round);
    void deleteRound();
}
