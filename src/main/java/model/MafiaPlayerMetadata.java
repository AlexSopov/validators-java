package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Calendar;

@AllArgsConstructor
public class MafiaPlayerMetadata {
    @Size(min = 2, message = "First name must consist o at least 2 characters")
    @NotNull
    @Getter @Setter private String firstName;

    @Size(min = 2, message = "Second name must consist o at least 2 characters")
    @NotNull
    @Getter @Setter private String secondName;

    @PastOrPresent(message = "Date registered can not be from future")
    @Getter @Setter private Calendar dateRegistered;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message = "Email is invalid")
    @Getter @Setter private String mailAddress;

    @NotNull(message = "Gender can not be null")
    @Getter @Setter private Gender gender;
}
