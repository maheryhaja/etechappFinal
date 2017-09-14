package mg.etech.mobile.etechapp.commun.utils.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mahery.haja on 13/09/2017.
 */

public class SimpleDateUtils {

    public static String format(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static int getAge(Date birthDate) {
        Date actualDate = new Date();
        return actualDate.getYear() - birthDate.getYear();
    }
}
