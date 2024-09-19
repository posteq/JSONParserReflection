package util.helper;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerialize implements JsonSerializer<LocalDate> {

    @Override
    public JsonElement serialize(LocalDate src, Type srcType, JsonSerializationContext context) {
        if(src == null){
            return null;
        }
        String formattedLocalDate = src.format(DateTimeFormatter.ISO_LOCAL_DATE);
        return new JsonPrimitive(formattedLocalDate);
    }
}
