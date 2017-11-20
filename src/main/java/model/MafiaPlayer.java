package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.ArrayList;

@AllArgsConstructor
public class MafiaPlayer {
    @Getter @Setter
    private String nick;

    @Getter @Setter
    private double winRate;

    @Getter @Setter
    private ArrayList<MafiaGame> games;

    @Getter @Setter @Valid
    private MafiaPlayerMetadata mafiaPlayerMetadata;
}
