package captain.cybot.adventure.backend.utility;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import captain.cybot.adventure.backend.validator.PasswordConstraintValidator;

@UtilityClass
@Slf4j
public class StringUtility {

    public static String GenerateRandomString(int length, boolean allowNumeric, boolean allowAlphabetical,
                                              boolean allowSpecialChars, boolean allowLowerCase,
                                              boolean allowUpperCase) {
        Map<String, String> chars = new HashMap<String, String>();
        String randomString = "";
        int currIndex = 0;
        if (allowNumeric) {
            for (int i = 48; i < 57; i++) {
                chars.put(Integer.toString(currIndex), Integer.toString(i));
                currIndex++;
            }
        }

        if (allowAlphabetical) {
            if (allowLowerCase) {
                for (int i = 97; i < 123; i++) {
                    chars.put(Integer.toString(currIndex), Integer.toString(i));
                    currIndex++;
                }
            }
            if (allowUpperCase) {
                for (int i = 65; i < 91; i++) {
                    chars.put(Integer.toString(currIndex), Integer.toString(i));
                    currIndex++;
                }
            }
        }

        if (allowSpecialChars) {
            for (int i = 33; i < 48; i++) {
                chars.put(Integer.toString(currIndex), Integer.toString(i));
                currIndex++;
            }
        }

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            randomString = randomString + ((char) (Integer.parseInt(chars.get(Integer.toString(random.nextInt(chars.size()))))));
        }

        return randomString;
    }

    public static String GenerateRandomPassword(int length) {
        String password = GenerateRandomString(length, true,true,true,true,true);
        int i = 0;
        while (!(new PasswordConstraintValidator()).isValid(password)) {
            password = GenerateRandomString(length, true,true,true,true,true);
            if (i > 10) {
                password = password.substring(0,length-4) + "aB!1";
                break;
            }
            i++;
        }
        return password;
    }
}
