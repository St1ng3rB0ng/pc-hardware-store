package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.dto.AddressDTO;
import com.pc_hardware_shop.demo.entity.Address;
import com.pc_hardware_shop.demo.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<Address>> getAddresses(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String postalCode) {

        if (customerId != null) {
            return ResponseEntity.ok(addressService.getAddressesByCustomerId(customerId));
        }
        if (city != null) {
            return ResponseEntity.ok(addressService.getAddressesByCity(city));
        }
        if (street != null) {
            return ResponseEntity.ok(addressService.getAddressesByStreet(street));
        }
        if (postalCode != null) {
            return ResponseEntity.ok(addressService.getAddressesByPostalCode(postalCode));
        }

        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        Address createdAddress = addressService.createAddress(addressDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable Long id) {
        addressService.deleteAddressById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Void> deleteAddressesByCustomerId(@PathVariable Long customerId) {
        addressService.deleteAddressesByCustomerId(customerId);
        return ResponseEntity.noContent().build();
    }
}