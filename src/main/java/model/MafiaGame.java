package model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.*;

public class MafiaGame {
    @Getter
    private UUID gameId;

    @Getter @Setter
    @NotNull
    @PastOrPresent
    private Calendar gameStartDate;

    @Getter
    @Valid
    @NotNull
    @NotEmpty
    private Map<MafiaRole, ArrayList<MafiaPlayer>> gamePlayers;

    @Getter
    @Valid
    @NotNull
    @NotEmpty
    private Map<MafiaPlayer, Boolean> playersStatus;

    @Getter
    @Setter
    private MafiaRole winner;

    @Getter
    @Setter
    private ArrayList<MafiaGameLog> gameLogs;

    @Getter
    @Setter
    @NotNull
    private MafiaGameStatus mafiaGameStatus;


    public MafiaGame() {
        gameId = UUID.randomUUID();
        mafiaGameStatus = MafiaGameStatus.WaitingForStart;
        gamePlayers = new HashMap<>();
        playersStatus = new HashMap<>();
        gameLogs = new ArrayList<>();
    }

    public int getPlayersCount() {
        int count = 0;
        for (MafiaRole role : gamePlayers.keySet()) {
            count += gamePlayers.get(role).size();
        }

        return count;
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        resultString.append("Players:\n");

        for (ArrayList<MafiaPlayer> mafiaPlayersList : getGamePlayers().values()) {
            for (MafiaPlayer mafiaPlayer : mafiaPlayersList) {
                resultString.append(mafiaPlayer).append("\n");
            }
        }

        resultString.append("Logs:\n");
        for (MafiaGameLog log : getGameLogs()) {
            resultString.append(log).append("\n");
        }

        return  resultString.toString();
    }
}
