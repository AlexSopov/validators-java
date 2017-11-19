package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class MafiaGameLog {
    @Getter @Setter private int roundNumber;
    @Getter @Setter private MafiaPlayer mafiaPlayer;
    @Getter @Setter private String logMessage;

    @Override
    public String toString() {
        return "Round " + roundNumber + ": " + logMessage + (mafiaPlayer == null ? "" : mafiaPlayer);
    }
}
