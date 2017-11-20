import model.Gender;
import model.MafiaPlayer;
import model.MafiaPlayerMetadata;
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

public class MafiaPlayerTest {
    private MafiaPlayer mafiaPlayer;
    private Validator validator;

    @Before
    public void before() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        MafiaPlayerMetadata mafiaPlayerMetadata = new MafiaPlayerMetadata("Alex", "Sopov", calendar, "mail@mail.com", Gender.Male);
        mafiaPlayer = new MafiaPlayer("nick", 0, new ArrayList<>(), mafiaPlayerMetadata);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void canNotSetInvalidNick() {
        mafiaPlayer.setNick("looooooooooooooooooooooooooooongNick");
        Set<ConstraintViolation<MafiaPlayer>> constraintViolations = validator.validate(mafiaPlayer);

        Assert.assertEquals(1, constraintViolations.size() );
        Assert.assertEquals("Length of nick must be in range[4, 15]", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void canNotSetInvalidWinRate() {
        mafiaPlayer.setWinRate(101);
        Set<ConstraintViolation<MafiaPlayer>> constraintViolations = validator.validate(mafiaPlayer);

        Assert.assertEquals(1, constraintViolations.size() );
        Assert.assertEquals("Win rate can not be greater than 100", constraintViolations.iterator().next().getMessage());

        mafiaPlayer.setWinRate(-1);
        constraintViolations = validator.validate(mafiaPlayer);

        Assert.assertEquals(1, constraintViolations.size() );
        Assert.assertEquals("Win rate can not be less than 0", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void canPerformCascadeValidating() {
        mafiaPlayer.getMafiaPlayerMetadata().setGender(null);
        Set<ConstraintViolation<MafiaPlayer>> constraintViolations = validator.validate(mafiaPlayer);

        Assert.assertEquals(1, constraintViolations.size() );
        Assert.assertEquals("Gender can not be null", constraintViolations.iterator().next().getMessage());
    }
}
