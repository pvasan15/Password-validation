import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.password.validation.service.PasswordService.ERROR_LETTER_AND_DIGIT;
import static com.password.validation.service.PasswordService.ERROR_PASSWORD_CASE;
import static com.password.validation.service.PasswordService.ERROR_PASSWORD_LENGTH;
import static com.password.validation.service.PasswordService.ERROR_PASSWORD_SEQUENCE_REPEATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertFalse;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.password.validation.config.AppConfig;
import com.password.validation.service.PasswordService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PasswordServiceTest {
    
    @Autowired
    private PasswordService passwordService;

    @Test
    public void testContainsOnlyLowercaseLetters() {
        // set
        Set<String> result = passwordService.validate("abcde");

        // assert
        assertFalse(result.contains(ERROR_PASSWORD_CASE));
    }

    @Test
    public void testContainsUppercaseLetters() {
        // set
        Set<String> result = passwordService.validate("Abcde");

        // assert
        assertTrue(result.contains(ERROR_PASSWORD_CASE));
    }

    @Test
    public void testContainsBothLetterAndDigit() {
        // set
        Set<String> result = passwordService.validate("a0");

        // assert
        assertFalse(result.contains(ERROR_LETTER_AND_DIGIT));
    }

    @Test
    public void testContainsBothDigitAndLetter() {
        // set
        Set<String> result = passwordService.validate("0a");

        // assert
        assertFalse(result.contains(ERROR_LETTER_AND_DIGIT));
    }

    @Test
    public void testContainsOnlyLetters() {
        // set
        Set<String> result = passwordService.validate("a");

        // assert
        assertThat(result, hasItem(ERROR_LETTER_AND_DIGIT));
    }

    @Test
    public void testContainsOnlyDigits() {
        // set
        Set<String> result = passwordService.validate("0");

        // assert
        assertThat(result, hasItem(ERROR_LETTER_AND_DIGIT));
    }

    @Test
    public void testSize5orMore() {

        // set
        Set<String> result = passwordService.validate("Prabha Srinivasan  1");

        // assert
        assertThat(result, hasItem(ERROR_PASSWORD_LENGTH));
    }

    @Test
    public void testSizeLessThan5() {

        // set
        Set<String> result = passwordService.validate("Pr 2");

        // assert
        assertTrue(result.contains(ERROR_PASSWORD_LENGTH));
    }

    @Test
    public void testSize12orLess() {

        // set
        Set<String> result = passwordService.validate("123456789112");

        // assert
        assertFalse(result.contains(ERROR_PASSWORD_LENGTH));
    }

    @Test
    public void testSizeMoreThan12() {

        // set
        Set<String> result = passwordService.validate("1234567891123");

        // assert
        assertThat(result, hasItem(ERROR_PASSWORD_LENGTH));
    }

    @Test
    public void testSequenceNotViolated() {

        // set
        Set<String> result = passwordService.validate("abcde12345");

        // assert
        assertFalse(result.contains(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatLetters() {

        // set
        Set<String> result = passwordService.validate("abab");

        // assert
        assertThat(result, hasItem(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatSingleLetter() {

        // set
        Set<String> result = passwordService.validate("aa");

        // assert
        assertFalse(result.contains(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatLettersAndDigits() {

        // set
        Set<String> result = passwordService.validate("ab1ab1");

        // assert
        assertThat(result, hasItem(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatLettersNotAtFront() {

        // set
        Set<String> result = passwordService.validate("prefixabab");

        // assert
        assertThat(result, hasItem(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatLettersNotAtBack() {

        // set
        Set<String> result = passwordService.validate("ababpostfix");

        // assert
        assertTrue(result.contains(ERROR_PASSWORD_SEQUENCE_REPEATED));
    } 

}



