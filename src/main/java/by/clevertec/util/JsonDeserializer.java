package by.clevertec.util;

import by.clevertec.exeption.JsonException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JsonDeserializer {

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        JsonParser jsonParser = new JsonParser();
        Object jsonObject = jsonParser.parseInput(jsonString);
        return deserializeObject(jsonObject, clazz);
    }

    public <T>T deserializeObject(Object input , Class<T> targetClass){
        if(input == null)
            return null;
        if(input instanceof Map){
            return deserializeMap((Map<String, Object>) input , targetClass);
        }
        throw new JsonException("Illegal type of input object");
    }

    private <T> T deserializeMap(Map<String, Object> inputMap , Class<T> targetClass) {
        try {
            T result = targetClass.getDeclaredConstructor().newInstance();
            Field[] fields = targetClass.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (inputMap .containsKey(fieldName)) {
                    Object value = inputMap .get(fieldName);
                    Object parsedValue = parseValue(value, field.getType(), field.getGenericType());
                    field.set(result, parsedValue );
                }
            }

            return result;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new JsonException(e.getMessage());
        }
    }


    private Object parseValue(Object value, Class<?> fieldType, Type genericType) {
        if(value == null)
            return null;
        try {
            if(fieldType == (String.class)){
                return value.toString();
            }
            if(fieldType == int.class ||fieldType == Integer.class) {
                return Integer.parseInt(value.toString());
            }
            if(fieldType == double.class || fieldType == Double.class){
                return Double.parseDouble(value.toString());
            }
            if(fieldType == BigDecimal.class){
                return new BigDecimal(value.toString());
            }
            if(fieldType == BigInteger.class){
                return new BigInteger(value.toString());
            }
            if(fieldType == (UUID.class)){
                return UUID.fromString(value.toString());
            }
            if(fieldType == (LocalDate.class)){
                return LocalDate.parse(value.toString(), DateTimeFormatter.ISO_LOCAL_DATE);
            }
            if(fieldType == (OffsetDateTime.class)){
                return OffsetDateTime.parse(value.toString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            }
            if(fieldType == (Map.class)){
                Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
                Class<?> typeKey = (Class<?>) actualTypeArguments[0];
                Class<?> typeValue = (Class<?>) actualTypeArguments[1];
                return parseMap((Map<?, ?>)value,typeKey,typeValue);
            }
            if(fieldType == List.class){
                Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
                Class<?> listType = (Class<?>) actualTypeArguments[0];
                return parseList((List<?>) value, listType);
            }
            return deserializeObject(value, fieldType);
        }catch (IllegalArgumentException | DateTimeException e){
            throw new JsonException(e.getMessage());
        }

    }

    private Map<Object, Object> parseMap(Map<?, ?> jsonMap, Class<?> typeKey, Class<?> typeValue) {
        Map<Object,Object> result = new HashMap<>();
        for (Map.Entry<?, ?> entry : jsonMap.entrySet()) {
            Object key = parseValue( entry.getKey(), typeKey, typeKey);
            Object value = parseValue( entry.getValue(), typeValue, typeValue);
            result.put(key, value);
        }
        return result;
    }

    private List<Object> parseList(List<?> value, Class<?> listType) {

        List<Object> parseList = new ArrayList<>();
        for(Object field : value){
            parseList.add(parseValue( field, listType , listType));
        }
        return parseList;
    }


}
