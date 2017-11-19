package model;

import lombok.AllArgsConstructor;

import java.util.Calendar;

@AllArgsConstructor
public class MafiaPlayerMetadata {
    String firstName;
    String secondName;
    Calendar dateRegistered;
    String mailAddress;
    Gender gender;
}
