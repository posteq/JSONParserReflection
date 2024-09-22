package by.clevertec.util;

import by.clevertec.domain.Customer;
import by.clevertec.domain.Order;
import by.clevertec.domain.Product;
import by.clevertec.helper.CreatorStructure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class JsonSerializersTest {

    private static ObjectMapper objectMapper;
    private final JsonSerializers jsonSerializer = new JsonSerializers();

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void shouldReturnJSON() throws IllegalAccessException, JsonProcessingException {
        //given
        Customer customer = CreatorStructure.create();

        //when
        String myJson = jsonSerializer.toJson(customer);
        String jacksonJson = objectMapper.writeValueAsString(customer);

        //then
        assertEquals(jacksonJson, myJson);

    }

    @ParameterizedTest
    @MethodSource("order")
    void shouldReturnOrderInJsonFormat(UUID id, List<Product> products, OffsetDateTime createDate) throws IllegalAccessException, JsonProcessingException {
        //given
        Order order = new Order(id, products, createDate);

        //when
        String myJson = jsonSerializer.toJson(order);
        String jacksonJson = objectMapper.writeValueAsString(order);

        //then
        assertEquals(jacksonJson, myJson);
    }

    @ParameterizedTest
    @MethodSource("customer")
    void shouldReturnCustomerInJsonFormat(UUID id, String firstName, String lastName, LocalDate dateBirth, List<Order> orders) throws JsonProcessingException, IllegalAccessException {
        //given
        Customer customer = new Customer(id, firstName, lastName, dateBirth, orders);

        //when
        String myJson = jsonSerializer.toJson(customer);
        String jacksonJson = objectMapper.writeValueAsString(customer);

        //then
        assertEquals(jacksonJson, myJson);
    }


    static Stream<Arguments> order() {
        return Stream.of(
                Arguments.of(UUID.randomUUID(),
                        List.of(new Product(UUID.randomUUID(), "XXX", 1000.0, Map.of())),
                        OffsetDateTime.now()),

                Arguments.of(UUID.randomUUID(), List.of(), OffsetDateTime.now()),

                Arguments.of(null,
                        List.of(new Product(UUID.randomUUID(), "Car", 1700.0, Map.of())),
                        OffsetDateTime.now().plusDays(10)),

                Arguments.of(UUID.randomUUID(), List.of(
                                new Product(UUID.randomUUID(), "Apple", 540.0, Map.of()),
                                new Product(UUID.randomUUID(), "Grape", 190.0, Map.of())),
                        OffsetDateTime.now().minusDays(5)),

                Arguments.of(null, null, null)
        );
    }

    static Stream<Arguments> customer() {
        return Stream.of(
                Arguments.of(UUID.randomUUID(), "John", "Snow", LocalDate.now(),
                        List.of(new Order(UUID.randomUUID(), List.of(
                                new Product(UUID.randomUUID(), "Laptop", 1000.0, Map.of())),
                                OffsetDateTime.now()))),

                Arguments.of(UUID.randomUUID(), "Jane", "Smith",
                        LocalDate.of(1985, 3, 10), List.of()),

                Arguments.of(null, null, null, LocalDate.of(2000, 1, 1), null),

                Arguments.of(UUID.randomUUID(), "Michael", "Johnson", LocalDate.now().plusDays(5),
                        List.of(new Order(UUID.randomUUID(), List.of(
                                new Product(UUID.randomUUID(), "Apple", 500.0, Map.of())),
                                OffsetDateTime.now().minusDays(10)))),

                Arguments.of(UUID.randomUUID(), "Alice", "Williams", null, null)
        );
    }

}