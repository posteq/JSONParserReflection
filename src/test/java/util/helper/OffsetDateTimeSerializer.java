package util.helper;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeSerializer implements JsonSerializer<OffsetDateTime>
{

    @Override
    public JsonElement serialize(OffsetDateTime src, Type typeOfSrc, JsonSerializationContext context)
    {
        if(src == null){
            return null;
        }
        String formattedDate = src.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return new JsonPrimitive(formattedDate);
    }

}
