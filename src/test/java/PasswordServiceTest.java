import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.password.validation.service.PasswordService.ERROR_LETTER_AND_DIGIT;
import static com.password.validation.service.PasswordService.ERROR_PASSWORD_LENGTH;
import static com.password.validation.service.PasswordService.ERROR_PASSWORD_SEQUENCE_REPEATED;
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
    // This method tests only valid passwords with different combinations of letters, digits and lengths to make sure they are all valid as per the requirements
    public void testValidPasswords() {
        // Test cases
        Set<String> result = passwordService.validate("@"); // lower case and numeric digits only of length between 5 and 12 with no repeated sequence 
        // with first 3 lower case letters followed by 3 digits.
        testValidPasswords(result);
        result = passwordService.validate("abc2abc"); // lower case and numeric digits only of length between 5 and 12 with no repeated sequence 
        // with first 3 lower case letters followed by a single digit and 3 lower case letters.
        testValidPasswords(result);
        result = passwordService.validate("a22bcdxxx"); // lower case and numeric digits only of length between 5 and 12 with no repeated sequence 
        // with 1 lower case letter followed by 2 digits and lower case letters.
        testValidPasswords(result);
        result = passwordService.validate("2zddokj4"); // lower case and numeric digits only of length between 5 and 12 with no repeated sequence 
        // with 1 digit followed by lower case letters and another digit
        testValidPasswords(result);
        result = passwordService.validate("123ab"); // lower case and numeric digits only of length between 5 and 12 with no repeated sequence
        // with 3 digits followed by lower case letters.
        testValidPasswords(result);
        result = passwordService.validate("22212345b222"); // lower case and numeric digits only of length between 5 and 12 with no repeated sequence
        // with digits, 1 letter followed by 3 digits.
        testValidPasswords(result);
    }
    
    public void testValidPasswords(Set<String> result) {
        // asserts
        assertFalse(result.contains(ERROR_LETTER_AND_DIGIT));
        assertFalse(result.contains(ERROR_PASSWORD_LENGTH));
        assertFalse(result.contains(ERROR_PASSWORD_SEQUENCE_REPEATED ));
    }
    
   /* @Test
    // This method tests invalid passwords with different combinations of letters, digits and lengths which are invalid according to the requirements
    public void testInValidPasswords() {
        // Test cases
        Set<String> result = passwordService.validate(""); // Mix of lower and upper cases
        testInValidPasswords(result);
        result = passwordService.validate("abcabc"); // repeated sequence 
        testInValidPasswords(result);
        result = passwordService.validate("abc2"); // less that 5 in length
        testInValidPasswords(result);
        result = passwordService.validate("abc2abcabc2abc"); // more than 12 in length
        testInValidPasswords(result);
        result = passwordService.validate("aAa123"); // Mix of lower and upper cases
        testInValidPasswords(result);
        result = passwordService.validate("abA123"); // Mix of lower and upper cases
        testInValidPasswords(result);
    }
    
    public void testInValidPasswords(Set<String> result) {
        // asserts
        assertFalse(result.contains(ERROR_LETTER_AND_DIGIT));
        assertFalse(result.contains(ERROR_PASSWORD_LENGTH));
        assertFalse(result.contains(ERROR_PASSWORD_SEQUENCE_REPEATED ));
    } */
}



