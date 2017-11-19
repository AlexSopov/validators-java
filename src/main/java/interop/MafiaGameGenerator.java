package interop;

import model.Gender;
import model.MafiaGame;
import model.MafiaPlayer;
import model.MafiaPlayerMetadata;

import java.util.ArrayList;
import java.util.Calendar;

public class MafiaGameGenerator {
    public MafiaGame generateGame() {
        MafiaGameDefaultManager mafiaGameDefaultManager = new MafiaGameDefaultManager();

        ArrayList<MafiaPlayer> mafiaPlayers = new ArrayList<>();
        MafiaPlayer currentGeneratedPlayer;
        for (int i = 0; i < 10; i++) {
            currentGeneratedPlayer = new MafiaPlayer("Player " + i, 0, new ArrayList<>(),
                    new MafiaPlayerMetadata("PlayerN " + i,
                            "PlayerSN " + i,
                            Calendar.getInstance(),
                            "player" + i + "@player.com",
                            i % 2 == 0 ? Gender.Male : Gender.Female));

            mafiaPlayers.add(currentGeneratedPlayer);
            mafiaGameDefaultManager.attachPlayer(currentGeneratedPlayer);
        }

        mafiaGameDefaultManager.generateNextRound(mafiaPlayers.get(5));
        mafiaGameDefaultManager.generateNextRound(mafiaPlayers.get(3));
        mafiaGameDefaultManager.generateNextRound(mafiaPlayers.get(3));
        mafiaGameDefaultManager.generateNextRound(mafiaPlayers.get(4));
        mafiaGameDefaultManager.generateNextRound(mafiaPlayers.get(7));
        mafiaGameDefaultManager.generateNextRound(mafiaPlayers.get(8));

        return mafiaGameDefaultManager.getCurrentMafiaGame();
    }
}
