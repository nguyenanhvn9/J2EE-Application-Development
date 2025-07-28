package com.techshop.service;

import com.techshop.model.Address;
import com.techshop.model.User;
import com.techshop.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAddressesByUser(User user) {
        return addressRepository.findByUser(user);
    }

    public Address getAddress(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}