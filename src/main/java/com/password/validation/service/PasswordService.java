package com.password.validation.service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    public static final String ERROR_PASSWORD_LENGTH = "Password must be between 5 and 12 characters long.";
    public static final String ERROR_LETTER_AND_DIGIT = "Password must contain both a letter and a digit.";
    public static final String ERROR_PASSWORD_SEQUENCE_REPEATED = "Password must not contain any sequence of characters immediately followed by the same sequence.";

    private Pattern checkLetterAndDigit = Pattern.compile("^[a-z0-9]+$");
    private Pattern checkSequenceRepetition = Pattern.compile("(\\w{2,})\\1");

    public Set<String> validate(String password) {
        Set<String> failures = new HashSet<String>();
        checkLength(password, failures);
        checkLetterAndDigit(password, failures);
        checkSequenceRepetition(password, failures);
        return failures;
    }

    private void checkSequenceRepetition(String password, Set<String> failures) {
        Matcher matcher = checkSequenceRepetition.matcher(password);
        if (matcher.find()) {
            failures.add(ERROR_PASSWORD_SEQUENCE_REPEATED);
        }
    }

    private void checkLetterAndDigit(String password, Set<String> failures) {
        Matcher matcher = checkLetterAndDigit.matcher(password);
        if (!matcher.find()) {
            failures.add(ERROR_LETTER_AND_DIGIT);
        }

    }

    private void checkLength(String string, Set<String> failures) {
        if (string.length() < 5 || string.length() > 12) {
            failures.add(ERROR_PASSWORD_LENGTH);
        }
    }

}
