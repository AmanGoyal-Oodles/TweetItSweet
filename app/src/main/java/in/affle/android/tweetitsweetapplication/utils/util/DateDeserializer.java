package in.affle.android.tweetitsweetapplication.utils.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateDeserializer implements JsonDeserializer<DateTime> {

    @Override
    public DateTime deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        if (element == null)
            return null;
        String date = element.getAsString();
        if (date != null) {

            //"2018-09-26T11:48:06.181Z"
            //2017-11-22 17:51:16
            SimpleDateFormat sdf = new SimpleDateFormat("E MMM d HH:mm:ss Z yyyy");
            //sdf.setTimeZone(TimeZone.getTimeZone("IST"));
           /* TimeZone tz = TimeZone.getTimeZone("Asia/Kolkata");
            DateTimeZone dateTimeZoneINDIA = DateTimeZone.forTimeZone(tz);*/
            try {
                //  return new DateTime(sdf.parse(date)).withZone(DateTimeZone.forTimeZone(TimeZone.getDefault()));
                //  return new LocalDateTime(sdf.parse(date).getTime()).toDateTime(DateTimeZone.forTimeZone(TimeZone.getDefault()));
                return new DateTime(sdf.parse(date).getTime());
                //.withZoneRetainFields(DateTimeZone.UTC)
                //.withZone(DateTimeZone.getDefault());
            } catch (ParseException e) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("MMM d HH:mm:ss Z yyyy");
                try {
                    //  return new DateTime(sdf.parse(date)).withZone(DateTimeZone.forTimeZone(TimeZone.getDefault()));
                    //  return new LocalDateTime(sdf.parse(date).getTime()).toDateTime(DateTimeZone.forTimeZone(TimeZone.getDefault()));
                    return new DateTime(sdf1.parse(date).getTime())
                            /*.withZoneRetainFields(DateTimeZone.UTC)*/
                            .withZone(DateTimeZone.getDefault());
                } catch (ParseException ex) {
                    return null;
                }
            }
        } else return null;
    }
}