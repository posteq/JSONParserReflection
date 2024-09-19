package util;

import by.clevertec.domain.Customer;
import by.clevertec.helper.CreatorStructure;
import by.clevertec.util.JsonSerializers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import util.helper.LocalDateSerialize;
import util.helper.OffsetDateTimeSerializer;
import util.helper.refactor.ToPrettyPrint;
import util.helper.writer.WriteToJson;

import java.time.LocalDate;
import java.time.OffsetDateTime;


class JsonSerializersTest {

    ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .registerModule(new JavaTimeModule());

    @Test
    void toJson() throws JsonProcessingException, IllegalAccessException {
        Customer customer = CreatorStructure.create();

        String jsonJack = objectMapper
                .writeValueAsString(customer);

        System.out.println(jsonJack);
        System.out.println("~~~~~~~~~~~");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerialize())
                .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeSerializer())
                .setPrettyPrinting()
                .create();
        String jsonGson = gson.toJson(customer);
        System.out.println(jsonGson);
        System.out.println("~~~~~~~~~~~");
        JsonSerializers serializer = new JsonSerializers();
        String jsonMy = serializer.toJson(customer);
        System.out.println(jsonMy);
        String jsonMuPretty = ToPrettyPrint.print(jsonMy);
        System.out.println(jsonMuPretty);
//        assertEquals(jsonGson, jsonMuPretty);

        WriteToJson.write("jsonGson.json",jsonGson);
        WriteToJson.write("jsonJack.json",jsonJack);
        WriteToJson.write("jsonMuPretty.json",jsonMuPretty);
    }
}