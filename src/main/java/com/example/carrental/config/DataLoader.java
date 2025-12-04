package com.example.carrental.config;

import com.example.carrental.model.*;
import com.example.carrental.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CarRepository carRepository;
    private final LocationRepository locationRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) {
        Location lublin = locationRepository.save(
                Location.builder()
                        .name("Oddział Lublin")
                        .city("Lublin")
                        .address("ul. Spacerowa 10")
                        .build()
        );

        Car car1 = Car.builder()
                .brand("Toyota")
                .model("Corolla")
                .productionYear(2020)
                .dailyPrice(new BigDecimal("150.00"))
                .available(true)
                .location(lublin)
                .build();

        Car car2 = Car.builder()
                .brand("BMW")
                .model("3")
                .productionYear(2019)
                .dailyPrice(new BigDecimal("250.00"))
                .available(true)
                .location(lublin)
                .build();

        carRepository.save(car1);
        carRepository.save(car2);

        customerRepository.save(
                Customer.builder()
                        .firstName("Jan")
                        .lastName("Kowalski")
                        .email("jan@example.com")
                        .phone("123456789")
                        .build()
        );

        employeeRepository.save(
                Employee.builder()
                        .firstName("Anna")
                        .lastName("Nowak")
                        .position("Konsultant")
                        .email("anna@example.com")
                        .build()
        );
    }
}
