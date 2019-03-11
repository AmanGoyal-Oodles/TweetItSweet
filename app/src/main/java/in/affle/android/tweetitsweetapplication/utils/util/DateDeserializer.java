package in.affle.android.tweetitsweetapplication.utils.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class DateDeserializer implements JsonDeserializer<DateTime> {


    @Override
    public DateTime deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        if (element == null)
            return null;
        String date = element.getAsString();
        if (date != null) {

            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("IST"));
            try {
                return new DateTime(sdf.parse(date).getTime());
            } catch (ParseException e) {
                return DateTime.parse(date);
                /*SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy",Locale.getDefault());
                try {
                    return new DateTime(sdf1.parse(date).getTime())
                            .withZone(DateTimeZone.getDefault());
                } catch (ParseException ex) {
                    return null;
                }*/
            }
        } else return new DateTime();
    }
}