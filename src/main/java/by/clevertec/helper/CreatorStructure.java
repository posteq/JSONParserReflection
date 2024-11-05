package by.clevertec.helper;

import by.clevertec.domain.Customer;
import by.clevertec.domain.Order;
import by.clevertec.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

public class CreatorStructure {
    public static Customer create(){
        Map<UUID, BigDecimal> bearPrice = new LinkedHashMap<>();
        bearPrice.put(UUID.randomUUID(),new BigDecimal("2.14"));
        Product bear = new Product(UUID.randomUUID(), "Bear", 1.23,bearPrice );
        Map<UUID, BigDecimal> milkPrice = new LinkedHashMap<>();
        milkPrice.put(UUID.randomUUID(),new BigDecimal("2.14"));
        Product milk = new Product(UUID.randomUUID(), "Milk", 1.23,milkPrice );

        Order order1 = new Order(UUID.randomUUID(), new ArrayList<>(Arrays.asList(bear, milk)), OffsetDateTime.now());
        Order order2 = new Order(UUID.randomUUID(), new ArrayList<>(Arrays.asList(bear, milk)), OffsetDateTime.now().plusHours(1));

        return new Customer(UUID.randomUUID(), "Petr", "Sidorov", LocalDate.now(), List.of(order1,order2));
    }
}
