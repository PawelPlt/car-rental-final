package com.example.carrental.service;

import com.example.carrental.model.*;
import com.example.carrental.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public Rental createRental(Long carId, Long customerId, Long pickupLocationId,
                               Long dropoffLocationId, LocalDate start, LocalDate end) {

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car not found: " + carId));

        if (!car.isAvailable()) {
            throw new IllegalStateException("Car is not available");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found: " + customerId));

        Location pickupLocation = locationRepository.findById(pickupLocationId)
                .orElseThrow(() -> new NotFoundException("Pickup location not found: " + pickupLocationId));

        Location dropoffLocation = locationRepository.findById(dropoffLocationId)
                .orElseThrow(() -> new NotFoundException("Dropoff location not found: " + dropoffLocationId));

        long days = ChronoUnit.DAYS.between(start, end);
        if (days <= 0) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        BigDecimal totalPrice = car.getDailyPrice().multiply(BigDecimal.valueOf(days));

        Rental rental = Rental.builder()
                .car(car)
                .customer(customer)
                .pickupLocation(pickupLocation)
                .dropoffLocation(dropoffLocation)
                .startDate(start)
                .endDate(end)
                .totalPrice(totalPrice)
                .status(RentalStatus.RESERVED)
                .build();

        car.setAvailable(false);
        carRepository.save(car);

        return rentalRepository.save(rental);
    }
}
