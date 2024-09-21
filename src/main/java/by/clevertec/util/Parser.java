package by.clevertec.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Parser {
    public Object parse(String inputJson){
        inputJson = inputJson.trim();
        if(inputJson.startsWith("{")) {
            return parseObject(inputJson);
        }
        if (inputJson.startsWith("[")) {
            return parseList(inputJson);
        }
        if (inputJson.startsWith("\"")) {
            return parseJustString(inputJson);
        }
        if (inputJson == null || inputJson.isEmpty() || inputJson.equals("null")) {
            return null;
        }else {
            return inputJson;
        }
    }


    private String parseJustString(String inputJson) {
        return inputJson.substring(1,inputJson.length()-1).trim();
    }


    private Object parseList(String inputJson) {
        ArrayList<Object> result = new ArrayList<>();
        inputJson = inputJson.substring(1,inputJson.length()-1).trim();
        String[] strings = parseString(inputJson);

        for(String field : strings){
            String value = field.trim();
            result.add(parse(value));
        }

        return result;
    }

    private Object parseObject(String inputJson) {
        HashMap<String,Object> result = new LinkedHashMap<>();
        inputJson = inputJson.substring(1,inputJson.length()-1).trim();
        String[] strings = parseString(inputJson);

        for(String field : strings){
            String[] trimField = field.split(":",2);
            String key = trimField[0].trim();
            Object value = parse(trimField[1].trim());
            result.put(key.substring(1,key.length()-1),value);
        }

        return result;
    }

    private String[] parseString(String inputJson) {

        int countNester = 0;
        StringBuilder field = new StringBuilder();
        List<String> arrayFields = new ArrayList<>();

        for(int i = 0;i < inputJson.length();i++){
            char charAt = inputJson.charAt(i);
            if( charAt == '[' || charAt == '{')
                countNester++;
            if (charAt == ']' || charAt == '}')
                countNester--;
            if (countNester == 0 && charAt == ',') {
                arrayFields.add(field.toString().trim());
                field.setLength(0);
            }
            else
                field.append(inputJson.charAt(i));
        }

        if(field.length() > 0)
            arrayFields.add(field.toString().trim());
        return arrayFields.toArray(new String[0]);
    }
}
