package by.clevertec;


import by.clevertec.domain.Customer;
import by.clevertec.helper.CreatorStructure;
import by.clevertec.util.JsonSerializers;


public class Main {
    public static void main(String[] args) throws Exception {
        Customer customer = CreatorStructure.create();

        JsonSerializers serializer = new JsonSerializers();
        String json = serializer.toJson(customer);
        System.out.println("Serialized JSON: " + json);

    }
}
