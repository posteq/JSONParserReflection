package by.clevertec.util;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class JsonSerializers {

    public String toJson(Object obj) throws IllegalAccessException {

        if (obj == null) {
            return "null";
        }

        StringBuilder json = new StringBuilder();
        Class<?> clazz = obj.getClass();

        if ( obj instanceof Number ) {
            return obj.toString();
        } else if (obj instanceof String || clazz.isPrimitive()) {
            return "\"" + obj + "\"";

        } else if (obj instanceof Collection) {
            return collectionToJson((Collection<?>) obj);
        } else if (obj instanceof Map) {
            return mapToJson((Map<?, ?>) obj);
        } else if (obj instanceof UUID) {
            return uuidToJson((UUID) obj);
        }else if (obj instanceof LocalDate) {
            return localDateToJson((LocalDate) obj);
        } else if (obj instanceof OffsetDateTime) {
            return offsetDateTimeToJson((OffsetDateTime) obj);
        }else {
            json.append("{");
            Field[] fields = clazz.getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {

                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(obj);
                json.append("\"").append(field.getName()).append("\": ").append(toJson(value));

                if (i < fields.length - 1) {
                    json.append(",");
                }

            }
            json.append("}");
        }
        return json.toString();
    }

    private String offsetDateTimeToJson(OffsetDateTime obj) {
        return "\"" + obj.format(DateTimeFormatter.ISO_DATE_TIME) + "\"";
    }

    private String localDateToJson(LocalDate obj) {
        return "\"" + obj + "\"";
    }

    private String collectionToJson(Collection<?> collection) throws IllegalAccessException {
        StringBuilder json = new StringBuilder();
        json.append("[");
        int i = 0;
        for (Object item : collection) {
            json.append(toJson(item));
            if (i < collection.size() - 1) {
                json.append(",");
            }
            i++;
        }
        json.append("]");
        return json.toString();
    }

    private String mapToJson(Map<?, ?> map) throws IllegalAccessException {
        StringBuilder json = new StringBuilder();
        json.append("{");
        int i = 0;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\":").append(toJson(entry.getValue()));
            if (i < map.size() - 1) {
                json.append(",");
            }
            i++;
        }
        json.append("}");
        return json.toString();
    }

    private String uuidToJson(UUID uuid){
        return "\"" + uuid + "\"";
    }
}