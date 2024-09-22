package by.clevertec.util;

import by.clevertec.exeption.JsonException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {

    public Object parseInput(String json){
        try {
            json = json.trim();
            if (json.isEmpty() || json.equals("null")) {
                return null;
            } else if (json.startsWith("{"))
                return createJsonObject(json);
            else if (json.startsWith("[")) {
                return createJsonArray(json);
            } else if (json.startsWith("\"")) {
                return createString(json);
            } else return createStringWithoutBrackets(json);
        }catch (JsonException message){
            System.err.println("Error during parse JSON :" + message);
            return null;
        }
    }

    private String createString(String inputJsonString) {
        if(inputJsonString.length()<2)
            throw new JsonException("Invalid length of JSON");
        return inputJsonString.substring(1,inputJsonString.length()-1).trim();
    }

    private String createStringWithoutBrackets(String inputJsonString) {
        return inputJsonString;
    }

    private List<Object> createJsonArray(String jsonArrayString) {
        List<Object> jsonList = new ArrayList<>();
        jsonArrayString = jsonArrayString.substring(1,jsonArrayString.length()-1).trim();
        String[] jsonElements = splitJsonElements(jsonArrayString);

        for(String field : jsonElements){
            String value = field.trim();
            jsonList.add(parseInput(value));
        }

        return jsonList;
    }

    private Map<String, Object> createJsonObject(String jsonObjectString) {
        Map<String,Object> jsonObject = new LinkedHashMap<>();
        jsonObjectString = jsonObjectString.substring(1,jsonObjectString.length()-1).trim();
        String[] jsonFields = splitJsonElements(jsonObjectString);

        for(String jsonField : jsonFields){
            String[] trimField = jsonField.split(":",2);
            String key = trimField[0].trim();
            Object value = parseInput(trimField[1].trim());
            jsonObject.put(key.substring(1,key.length()-1),value);
        }

        return jsonObject;
    }

    private String[] splitJsonElements(String inputJson) {

        int countNester = 0;
        StringBuilder currentField = new StringBuilder();
        List<String> elementsList = new ArrayList<>();

        for(int i = 0;i < inputJson.length();i++){
            char charAt = inputJson.charAt(i);
            if( charAt == '[' || charAt == '{')
                countNester++;
            if (charAt == ']' || charAt == '}')
                countNester--;
            if (countNester == 0 && charAt == ',') {
                elementsList.add(currentField.toString().trim());
                currentField.setLength(0);
            } else
                currentField.append(inputJson.charAt(i));
        }

        if(!currentField.isEmpty())
            elementsList.add(currentField.toString().trim());
        return elementsList.toArray(new String[0]);
    }
}
