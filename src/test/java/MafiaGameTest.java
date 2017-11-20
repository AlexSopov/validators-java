import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;


public class MafiaGameTest {
    private MafiaGame mafiaGame;
    private Validator validator;

    @Before
    public void before() {
        mafiaGame = new MafiaGame();

        MafiaPlayer currentGeneratedPlayer;
        ArrayList<MafiaPlayer> players = new ArrayList<>();
        ArrayList<MafiaPlayer> playersPerRole;

        for (int i = 0; i < 10; i++) {
            currentGeneratedPlayer = new MafiaPlayer("Player " + i, 0, new ArrayList<>(),
                    new MafiaPlayerMetadata("PlayerN " + i,
                            "PlayerSN " + i,
                            Calendar.getInstance(),
                            "player" + i + "@player.com",
                            i % 2 == 0 ? Gender.Male : Gender.Female));

            players.add(currentGeneratedPlayer);
        }

        mafiaGame.getGamePlayers().put(MafiaRole.Mafia, new ArrayList<>());
        mafiaGame.getGamePlayers().put(MafiaRole.Don, new ArrayList<>());
        mafiaGame.getGamePlayers().put(MafiaRole.Peaceful, new ArrayList<>());
        mafiaGame.getGamePlayers().put(MafiaRole.Sheriff, new ArrayList<>());

        playersPerRole = mafiaGame.getGamePlayers().get(MafiaRole.Mafia);
        playersPerRole.add(players.get(0));
        playersPerRole.add(players.get(1));

        playersPerRole = mafiaGame.getGamePlayers().get(MafiaRole.Don);
        playersPerRole.add(players.get(2));

        playersPerRole = mafiaGame.getGamePlayers().get(MafiaRole.Sheriff);
        playersPerRole.add(players.get(3));

        playersPerRole = mafiaGame.getGamePlayers().get(MafiaRole.Peaceful);
        playersPerRole.add(players.get(4));
        playersPerRole.add(players.get(5));
        playersPerRole.add(players.get(6));
        playersPerRole.add(players.get(7));
        playersPerRole.add(players.get(8));
        playersPerRole.add(players.get(9));

        mafiaGame.setGameStartDate(Calendar.getInstance());

        mafiaGame.getGameLogs().add(new MafiaGameLog(0, players.get(0), "Log 1"));
        mafiaGame.getGameLogs().add(new MafiaGameLog(1, players.get(0), "Log 2"));
        mafiaGame.getGameLogs().add(new MafiaGameLog(2, players.get(0), "Log 3"));

        mafiaGame.setWinner(MafiaRole.Mafia);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void canNotCrateNullUuid() {
        mafiaGame.setGameId(null);
        Set<ConstraintViolation<MafiaGame>> constraintViolations = validator.validate(mafiaGame);

        Assert.assertEquals(1, constraintViolations.size() );
        Assert.assertEquals("Id must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void canNotCrateFutureGame() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 5);

        mafiaGame.setGameStartDate(calendar);
        Set<ConstraintViolation<MafiaGame>> constraintViolations = validator.validate(mafiaGame);

        Assert.assertEquals(1, constraintViolations.size() );
        Assert.assertEquals("Game start date maus be less or equual to present", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void gameMustContainAllGameRoles() {
        mafiaGame.getGamePlayers().remove(MafiaRole.Mafia);
        Set<ConstraintViolation<MafiaGame>> constraintViolations = validator.validate(mafiaGame);

        Assert.assertEquals(1, constraintViolations.size() );
        Assert.assertEquals("Mafia game must contain all 4 game roles", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void winnerMastExists() {
        mafiaGame.setWinner(null);
        Set<ConstraintViolation<MafiaGame>> constraintViolations = validator.validate(mafiaGame);

        Assert.assertEquals(1, constraintViolations.size() );
        Assert.assertEquals("Game must consist winner", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void mafiaGameHasEnoughLogs() {
        mafiaGame.getGameLogs().remove(2);
        Set<ConstraintViolation<MafiaGame>> constraintViolations = validator.validate(mafiaGame);

        Assert.assertEquals(1, constraintViolations.size() );
        Assert.assertEquals("Mafia game has at least 3 logs", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void gamePlayersCantBeNull() {
        mafiaGame.setGamePlayers(null);
        Set<ConstraintViolation<MafiaGame>> constraintViolations = validator.validate(mafiaGame);

        Assert.assertEquals(1, constraintViolations.size() );
        Assert.assertEquals("Geme players can not be null", constraintViolations.iterator().next().getMessage());
    }
}
