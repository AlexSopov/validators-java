package model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.*;

public class MafiaGame {
    @NotNull (message = "Id must not be null")
    @Getter @Setter private UUID gameId;

    @PastOrPresent(message = "Game start date maus be less or equual to present")
    @Getter @Setter private Calendar gameStartDate;

    @Valid
    @NotNull (message = "Geme players can not be null")
    @Size(max = 4, min = 4, message = "Mafia game must contain all 4 game roles")
    @Getter @Setter private Map<MafiaRole, ArrayList<MafiaPlayer>> gamePlayers;

    @NotNull (message = "Game must consist winner")
    @Getter @Setter private MafiaRole winner;

    @Getter @Setter
    @Size(min = 3, message = "Mafia game has at least 3 logs")
    @Valid
    private ArrayList<MafiaGameLog> gameLogs;

    public MafiaGame() {
        gameId = UUID.randomUUID();
        gamePlayers = new HashMap<>();
        gameLogs = new ArrayList<>();
    }
}
