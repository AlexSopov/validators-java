import model.Gender;
import model.MafiaPlayerMetadata;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Calendar;
import java.util.Set;

public class MafiaPlayerMetadataTest {
    private MafiaPlayerMetadata mafiaPlayerMetadata;
    private Validator validator;

    @Before
    public void before() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        mafiaPlayerMetadata = new MafiaPlayerMetadata("Alex", "Sopov", calendar, "mail@mail.com", Gender.Male);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void canNotCreateInvalieEmail() {
        mafiaPlayerMetadata.setMailAddress("alex");
        Set<ConstraintViolation<MafiaPlayerMetadata>> constraintViolations = validator.validate(mafiaPlayerMetadata);

        Assert.assertEquals(1, constraintViolations.size() );
        Assert.assertEquals("Email is invalid", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void canNotCreateTooShortFirstName() {
        mafiaPlayerMetadata.setFirstName("a");
        Set<ConstraintViolation<MafiaPlayerMetadata>> constraintViolations = validator.validate(mafiaPlayerMetadata);

        Assert.assertEquals(1, constraintViolations.size() );
        Assert.assertEquals("First name must consist o at least 2 characters", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void canNotCreateTooShortSecondName() {
        mafiaPlayerMetadata.setSecondName("a");
        Set<ConstraintViolation<MafiaPlayerMetadata>> constraintViolations = validator.validate(mafiaPlayerMetadata);

        Assert.assertEquals(1, constraintViolations.size() );
        Assert.assertEquals("Second name must consist o at least 2 characters", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void canNotCreateNullName() {
        mafiaPlayerMetadata.setFirstName(null);
        Set<ConstraintViolation<MafiaPlayerMetadata>> constraintViolations = validator.validate(mafiaPlayerMetadata);

        Assert.assertEquals(1, constraintViolations.size() );
    }

    @Test
    public void cantCreateRegisterFromFuture() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        mafiaPlayerMetadata.setDateRegistered(calendar);
        Set<ConstraintViolation<MafiaPlayerMetadata>> constraintViolations = validator.validate(mafiaPlayerMetadata);

        Assert.assertEquals(1, constraintViolations.size() );
        Assert.assertEquals("Date registered can not be from future", constraintViolations.iterator().next().getMessage());
    }
}
