package com.example.carrental.api;

import com.example.carrental.model.Rental;
import com.example.carrental.repository.RentalRepository;
import com.example.carrental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalRestController {

    private final RentalRepository rentalRepository;
    private final RentalService rentalService;

    @GetMapping
    public List<Rental> getAll() {
        return rentalRepository.findAll();
    }

    @PostMapping
    public Rental createRental(@RequestParam Long carId,
                               @RequestParam Long customerId,
                               @RequestParam Long pickupLocationId,
                               @RequestParam Long dropoffLocationId,
                               @RequestParam String startDate,
                               @RequestParam String endDate) {
        return rentalService.createRental(
                carId,
                customerId,
                pickupLocationId,
                dropoffLocationId,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }
}
