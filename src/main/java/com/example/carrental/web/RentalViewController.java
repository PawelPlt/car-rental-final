package com.example.carrental.web;

import com.example.carrental.repository.*;
import com.example.carrental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalViewController {

    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final LocationRepository locationRepository;
    private final RentalService rentalService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("rentals", rentalRepository.findAll());
        return "rentals";
    }

    @GetMapping("/new")
    public String newRentalForm(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("locations", locationRepository.findAll());
        return "rental-form";
    }

    @PostMapping
    public String createRental(@RequestParam Long carId,
                               @RequestParam Long customerId,
                               @RequestParam Long pickupLocationId,
                               @RequestParam Long dropoffLocationId,
                               @RequestParam String startDate,
                               @RequestParam String endDate,
                               Model model) {
        try {
            rentalService.createRental(
                    carId,
                    customerId,
                    pickupLocationId,
                    dropoffLocationId,
                    LocalDate.parse(startDate),
                    LocalDate.parse(endDate)
            );
            return "redirect:/rentals";
        } catch (Exception e) {
            model.addAttribute("cars", carRepository.findAll());
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("locations", locationRepository.findAll());
            model.addAttribute("errorMessage", e.getMessage());
            return "rental-form";
        }
    }
}
