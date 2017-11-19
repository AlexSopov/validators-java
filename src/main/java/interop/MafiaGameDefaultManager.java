package interop;

import lombok.Getter;
import model.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
Manager of mafia game. Starts, ends mafia game. Makes results of game.
NOTE: It is not valid mafia game class. It has no real rules. Is's created just
to demonstrate how does it MAY work.
 **/
public class MafiaGameDefaultManager {
    @Getter @NotNull private MafiaGame currentMafiaGame;

    MafiaGameDefaultManager() {
        currentMafiaGame = new MafiaGame();
    }

    public boolean attachPlayer(MafiaPlayer mafiaPlayer) {
        int playersCount = currentMafiaGame.getPlayersCount();

        if (playersCount < 10){
            MafiaRole statusForNextPlayer = getStatusForNextPlayer();
            ArrayList<MafiaPlayer> playersForRole = currentMafiaGame.getGamePlayers().get(statusForNextPlayer);

            if (playersForRole == null) {
                playersForRole = new ArrayList<>();
                currentMafiaGame.getGamePlayers().put(statusForNextPlayer, playersForRole);

            }

            playersForRole.add(mafiaPlayer);
            currentMafiaGame.getPlayersStatus().put(mafiaPlayer, true);
            generateNextRound(mafiaPlayer);

            if (++playersCount == 10) {
                startGame();
            }

            return true;
        }

        return false;
    }
    public void generateNextRound(MafiaPlayer logForPlayer) {

        if (currentMafiaGame.getMafiaGameStatus() == MafiaGameStatus.Finished) {
            return;
        }

        ArrayList<MafiaGameLog> gameLogs = currentMafiaGame.getGameLogs();
        if (currentMafiaGame.getMafiaGameStatus() == MafiaGameStatus.WaitingForStart) {
            gameLogs.add(new MafiaGameLog(0, logForPlayer, "Attach player: "));
        }
        else {
            Boolean killedPlayerStatus = currentMafiaGame.getPlayersStatus().get(logForPlayer);

            if (killedPlayerStatus) {
                gameLogs.add(new MafiaGameLog(gameLogs.get(gameLogs.size() - 1).getRoundNumber() + 1, logForPlayer, "Killed player"));
                killedPlayerStatus = Boolean.FALSE;
            }
            else {
                gameLogs.add(new MafiaGameLog(gameLogs.get(gameLogs.size() - 1).getRoundNumber() + 1, logForPlayer, "Mafia has missed"));
            }
        }

        if (currentMafiaGame.getMafiaGameStatus() == MafiaGameStatus.InProcess && isGameFinish()) {
            currentMafiaGame.setMafiaGameStatus(MafiaGameStatus.Finished);
            gameLogs.add(new MafiaGameLog(gameLogs.get(gameLogs.size() - 1).getRoundNumber() + 1, null, "End of game"));
        }
    }

    private void startGame() {
        currentMafiaGame.setGameStartDate(Calendar.getInstance());
        currentMafiaGame.setMafiaGameStatus(MafiaGameStatus.InProcess);
    }
    private MafiaRole getStatusForNextPlayer() {
        Map<MafiaRole, Integer> playersPerRole = getPlayersPerStatus();

        if (playersPerRole.get(MafiaRole.Don) < 1) {
            return MafiaRole.Don;
        }
        else if (playersPerRole.get(MafiaRole.Mafia) < 2) {
            return MafiaRole.Mafia;
        }
        else if (playersPerRole.get(MafiaRole.Sheriff) < 1) {
            return MafiaRole.Sheriff;
        }
        else {
            return MafiaRole.Peaceful;
        }
    }
    private boolean isGameFinish() {
        ArrayList<MafiaGameLog> logs = currentMafiaGame.getGameLogs();
        return logs.get(logs.size() - 1).getRoundNumber() > 5;
    }
    private Map<MafiaRole, Integer> getPlayersPerStatus() {
        Map<MafiaRole, Integer> playersPerRole = new HashMap<>();
        playersPerRole.put(MafiaRole.Don, 0);
        playersPerRole.put(MafiaRole.Sheriff, 0);
        playersPerRole.put(MafiaRole.Peaceful, 0);
        playersPerRole.put(MafiaRole.Mafia, 0);

        Integer currentValueOfType;
        for (MafiaRole mafiaRole : currentMafiaGame.getGamePlayers().keySet()) {
            currentValueOfType = playersPerRole.get(mafiaRole);
            currentValueOfType = currentValueOfType + 1;
        }

        return playersPerRole;
    }
}
