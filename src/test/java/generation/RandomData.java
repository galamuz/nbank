package generation;

import org.apache.commons.lang3.RandomStringUtils;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.util.Arrays;

public class RandomData {
    public static String getUserName(){

      return RandomStringUtils.secure().nextAlphabetic(10).toLowerCase();

    }

    public static String getPassword(){

        PasswordGenerator generator = new PasswordGenerator();

        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase, 1);
        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase, 1);
        CharacterRule digitRule     = new CharacterRule(EnglishCharacterData.Digit, 1);
       // CharacterRule specialRule   = new CharacterRule(EnglishCharacterData.Special, 1);

       return  generator.generatePassword(9,
                Arrays.asList(lowerCaseRule, upperCaseRule, digitRule))+"#$";

    }

    public static String getShortUserName(){

        return RandomStringUtils.secure().nextAlphabetic(2).toLowerCase();

    }

    public static String getLongUserName(){

        return RandomStringUtils.secure().nextAlphabetic(16).toLowerCase();

    }


}
