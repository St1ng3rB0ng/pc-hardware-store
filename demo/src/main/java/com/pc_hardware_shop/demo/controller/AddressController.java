package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.dto.AddressDTO;
import com.pc_hardware_shop.demo.entity.Address;
import com.pc_hardware_shop.demo.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

    @GetMapping("/customerId/{customerId}")
    public ResponseEntity<List<Address>> getAddressesByCustomerId(@PathVariable Long customerId) {
        List<Address> addresses = addressService.getAddressesByCustomerId(customerId);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping(path = "/city")
    public ResponseEntity<List<Address>> getAddressesByCity(@RequestParam String city) {
        List<Address> addresses = addressService.getAddressesByCity(city);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping(path = "/street")
    public ResponseEntity<List<Address>> getAddressesByStreet(@RequestParam String street) {
        List<Address> addresses = addressService.getAddressesByStreet(street);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping(path = "/postalCode")
    public ResponseEntity<List<Address>> getAddressesByPostalCode(@RequestParam String postalCode) {
        List<Address> addresses = addressService.getAddressesByPostalCode(postalCode);
        return ResponseEntity.ok(addresses);
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody AddressDTO addressDTO) {
        Address address = addressService.createAddress(addressDTO);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable Long id) {
        addressService.deleteAddressById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/customerId/{customerId}")
    public ResponseEntity<Void> deleteAddressesByCustomerId(@PathVariable Long customerId) {
        addressService.deleteAddressesByCustomerId(customerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/city")
    public ResponseEntity<Void> deleteAddressesByCity(@RequestParam String city) {
        addressService.deleteAddressesByCity(city);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/street")
    public ResponseEntity<Void> deleteAddressesByStreet(@RequestParam String street) {
        addressService.deleteAddressesByStreet(street);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/postalCode")
    public ResponseEntity<Void> deleteAddressesByPostalCode(@RequestParam String postalCode) {
        addressService.deleteAddressesByPostalCode(postalCode);
        return ResponseEntity.noContent().build();
    }
}
