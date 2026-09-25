package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.AddressDTO;
import com.pc_hardware_shop.demo.entity.Address;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.AddressRepository;
import com.pc_hardware_shop.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address by id '" + id + "' not found"));
    }

    @Transactional(readOnly = true)
    public List<Address> getAddressesByCustomerId(Long customerId) {
        if (!addressRepository.existsByCustomerId(customerId)) {
            throw new NotFoundException("Addresses by customer id '" + customerId + "' not found");
        }
        return addressRepository.findAddressesByCustomerId(customerId);
    }

    @Transactional(readOnly = true)
    public List<Address> getAddressesByCity(String city) {
        if (!addressRepository.existsByCity(city)) {
            throw new NotFoundException("Addresses by city '" + city + "' not found");
        }
        return addressRepository.findAddressesByCity(city);
    }

    @Transactional(readOnly = true)
    public List<Address> getAddressesByStreet(String street) {
        if (!addressRepository.existsByStreet(street)) {
            throw new NotFoundException("Addresses by street '" + street + "' not found");
        }
        return addressRepository.findAddressesByStreet(street);
    }

    public List<Address> getAddressesByPostalCode(String postalCode) {
        if (!addressRepository.existsByPostalCode(postalCode)) {
            throw new NotFoundException("Addresses by postal code '" + postalCode + "' not found");
        }
        return addressRepository.findAddressesByPostalCode(postalCode);
    }

    public Address createAddress(AddressDTO addressDTO) {
        if (!customerRepository.existsById(addressDTO.customerId())) {
            throw new IllegalArgumentException("Customer with id '" + addressDTO.customerId() + "' do not exists");
        }

        Address address = Address.builder()
                .customerId(addressDTO.customerId())
                .city(addressDTO.city())
                .street(addressDTO.street())
                .postalCode(addressDTO.postalCode())
                .build();

        return addressRepository.save(address);
    }

    public void deleteAddressById(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new NotFoundException("Addresses by id '" + id + "' not found");
        }
        addressRepository.deleteById(id);
    }

    public void deleteAddressesByCustomerId(Long customerId) {
        if (!addressRepository.existsByCustomerId(customerId)) {
            throw new NotFoundException("Addresses by customer id '" + customerId + "' not found");
        }
        addressRepository.deleteAddressByCustomerId(customerId);
    }

    public void deleteAddressesByCity(String city) {
        if (!addressRepository.existsByCity(city)) {
            throw new NotFoundException("Addresses by city '" + city + "' not found");
        }
        addressRepository.deleteAddressByCity(city);
    }

    public void deleteAddressesByStreet(String street) {
        if (!addressRepository.existsByStreet(street)) {
            throw new NotFoundException("Addresses by street'" + street + "' not found");
        }
        addressRepository.deleteAddressByStreet(street);
    }

    public void deleteAddressesByPostalCode(String postalCode) {
        if (!addressRepository.existsByPostalCode(postalCode)) {
            throw new NotFoundException("Addresses by postal code'" + postalCode + "' not found");
        }
        addressRepository.deleteAddressByPostalCode(postalCode);
    }
}
