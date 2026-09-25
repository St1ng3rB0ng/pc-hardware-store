package com.pc_hardware_shop.demo.repository;

import com.pc_hardware_shop.demo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAddressesByCustomerId(Long customerId);

    List<Address> findAddressesByCity(String city);

    List<Address> findAddressesByStreet(String street);

    List<Address> findAddressesByPostalCode(String postalCode);

    void deleteAddressByCustomerId(Long customerId);

    void deleteAddressByCity(String city);

    void deleteAddressByStreet(String street);

    void deleteAddressByPostalCode(String postalCode);

    boolean existsByCustomerId(Long customerId);

    boolean existsByCity(String city);

    boolean existsByStreet(String street);

    boolean existsByPostalCode(String postalCode);

    boolean existsByCustomerIdAndCityAndStreetAndPostalCode(Long customerId, String city, String street, String postalCode);
}
