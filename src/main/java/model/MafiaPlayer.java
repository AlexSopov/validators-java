package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@AllArgsConstructor
public class MafiaPlayer {

    @NotNull
    @Size(min = 4, max = 15, message = "Length of nick must be in range[4, 15]")
    @Getter @Setter private String nick;

    @Min(value = 0, message = "Win rate can not be less than 0")
    @Max(value = 100, message = "Win rate can not be greater than 100")
    @Getter @Setter private double winRate;

    @NotNull
    @Valid
    @Getter @Setter private ArrayList<MafiaGame> games;

    @Valid
    @NotNull
    @Getter @Setter private MafiaPlayerMetadata mafiaPlayerMetadata;
}
