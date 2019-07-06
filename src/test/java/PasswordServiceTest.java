import static org.junit.Assert.*;
import org.junit.Test;
import static com.password.validation.service.PasswordService.ERROR_LETTER_AND_DIGIT;
import static com.password.validation.service.PasswordService.ERROR_PASSWORD_CASE;
import static com.password.validation.service.PasswordService.ERROR_PASSWORD_LENGTH;
import static com.password.validation.service.PasswordService.ERROR_PASSWORD_SEQUENCE_REPEATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertFalse;
import java.util.Set;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.password.validation.config.AppConfig;
import com.password.validation.service.PasswordService;

public class PasswordServiceTest {
    private static PasswordService passwordService;

    @BeforeClass
    public static void getServiceFromIOC() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        passwordService = ctx.getBean(PasswordService.class);
    }

    @Test
    public void testContainsOnlyLowercaseLetters() {
        // assert
        Set<String> result = passwordService.validate("abcde");

        // verify
        assertFalse(result.contains(ERROR_PASSWORD_CASE));
    }

    @Test
    public void testContainsUppercaseLetters() {
        // assert
        Set<String> result = passwordService.validate("Abcde");

        // verify
        assertTrue(result.contains(ERROR_PASSWORD_CASE));
    }

    @Test
    public void testContainsBothLetterAndDigit() {
        // assert
        Set<String> result = passwordService.validate("a0");

        // verify
        assertFalse(result.contains(ERROR_LETTER_AND_DIGIT));
    }

    @Test
    public void testContainsBothDigitAndLetter() {
        // assert
        Set<String> result = passwordService.validate("0a");

        // verify
        assertFalse(result.contains(ERROR_LETTER_AND_DIGIT));
    }

    @Test
    public void testContainsOnlyLetters() {
        // assert
        Set<String> result = passwordService.validate("a");

        // verify
        assertThat(result, hasItem(ERROR_LETTER_AND_DIGIT));
    }

    @Test
    public void testContainsOnlyDigits() {
        // assert
        Set<String> result = passwordService.validate("0");

        // verify
        assertThat(result, hasItem(ERROR_LETTER_AND_DIGIT));
    }

    @Test
    public void testSize5orMore() {

        // assert
        Set<String> result = passwordService.validate("Prabha Srinivasan  1");

        // verify
        assertThat(result, hasItem(ERROR_PASSWORD_LENGTH));
    }

    @Test
    public void testSizeLessThan5() {

        // assert
        Set<String> result = passwordService.validate("Pr 2");

        // verify
        assertTrue(result.contains(ERROR_PASSWORD_LENGTH));
    }

    @Test
    public void testSize12orLess() {

        // assert
        Set<String> result = passwordService.validate("123456789112");

        // verify
        assertFalse(result.contains(ERROR_PASSWORD_LENGTH));
    }

    @Test
    public void testSizeMoreThan12() {

        // assert
        Set<String> result = passwordService.validate("1234567891123");

        // verify
        assertThat(result, hasItem(ERROR_PASSWORD_LENGTH));
    }

    @Test
    public void testSequenceNotViolated() {

        // assert
        Set<String> result = passwordService.validate("abcde12345");

        // verify
        assertFalse(result.contains(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatLetters() {

        // assert
        Set<String> result = passwordService.validate("abab");

        // verify
        assertThat(result, hasItem(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatSingleLetter() {

        // assert
        Set<String> result = passwordService.validate("aa");

        // verify
        assertFalse(result.contains(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatLettersAndDigits() {

        // assert
        Set<String> result = passwordService.validate("ab1ab1");

        // verify
        assertThat(result, hasItem(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatLettersNotAtFront() {

        // assert
        Set<String> result = passwordService.validate("prefixabab");

        // verify
        assertThat(result, hasItem(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

    @Test
    public void testSequenceRepeatLettersNotAtBack() {

        // assert
        Set<String> result = passwordService.validate("ababpostfix");

        // verify
        assertTrue(result.contains(ERROR_PASSWORD_SEQUENCE_REPEATED));
    }

}



