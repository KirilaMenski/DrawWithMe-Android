package by.ansgar.drawwithme.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by kirila on 28.3.17.
 */

public class DateUtils {

    private static final String DATE_FORMAT = "dd MMM yyyy kk:mm";
    private static final String TIME_FORMAT = "kk:mm";

    public static String getDate(){
        String date;
        Calendar calendar = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat(TIME_FORMAT);
        date = format.format(calendar.getTime());
        return date;
    }

}
