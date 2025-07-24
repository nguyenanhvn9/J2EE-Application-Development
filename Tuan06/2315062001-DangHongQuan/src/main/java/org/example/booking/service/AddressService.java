package org.example.booking.service;

import org.example.booking.Response.AddressRepository;
import org.example.booking.model.Address;
import org.example.booking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }
    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
    }
    public Address findByIdAndUser(Long id, User user) {
        return addressRepository.findByIdAndUserId(id, user.getId()).orElse(null);
    }


}

