package com.example.demo.service;

import com.example.demo.dto.AddressDto; // Import AddressDto
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils; // Vẫn có thể dùng BeanUtils cho các trường chung

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    // Lấy tất cả địa chỉ của một người dùng
    public List<Address> getAddressesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return addressRepository.findByUser(user);
    }

    // Lấy một địa chỉ theo ID
    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    // Thêm địa chỉ mới
    @Transactional
    public Address addAddress(Long userId, Address newAddress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        newAddress.setUser(user);
        newAddress.setCreatedAt(LocalDateTime.now());
        newAddress.setUpdatedAt(null); // Đảm bảo updated_at là null khi tạo mới

        // Logic xử lý địa chỉ mặc định khi thêm mới
        if (newAddress.isDefault()) {
            // Nếu địa chỉ mới được đánh dấu là mặc định, hãy hủy đặt mặc định cho các địa chỉ khác
            addressRepository.findByUser(user).forEach(addr -> {
                if (addr.isDefault()) {
                    addr.setDefault(false);
                    // Không cần lưu ngay, sẽ lưu cùng với địa chỉ mới trong cùng transaction
                }
            });
        } else {
            // Nếu không được đánh dấu mặc định, kiểm tra nếu đây là địa chỉ đầu tiên của người dùng
            // thì tự động đặt nó làm mặc định
            List<Address> existingAddresses = addressRepository.findByUser(user);
            if (existingAddresses.isEmpty()) {
                newAddress.setDefault(true);
            }
        }
        return addressRepository.save(newAddress);
    }

    // Trong AddressService.java
    @Transactional
    public Address updateAddress(Long userId, Long addressId, AddressDto updatedAddressDto, Boolean isDefaultFromForm) { // 4 THAM SỐ
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));

        if (!existingAddress.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access Denied: Address does not belong to this user.");
        }

        existingAddress.setAlias(updatedAddressDto.getAlias());
        existingAddress.setRecipientName(updatedAddressDto.getRecipientName());
        existingAddress.setPhoneNumber(updatedAddressDto.getPhoneNumber());
        existingAddress.setStreetAddress(updatedAddressDto.getStreetAddress());
        existingAddress.setWard(updatedAddressDto.getWard());
        existingAddress.setDistrict(updatedAddressDto.getDistrict());
        existingAddress.setCity(updatedAddressDto.getCity());

        existingAddress.setUpdatedAt(LocalDateTime.now());

        boolean newIsDefaultValue = (isDefaultFromForm != null && isDefaultFromForm);

        if (newIsDefaultValue && !existingAddress.isDefault()) {
            addressRepository.findByUser(existingAddress.getUser()).forEach(addr -> {
                if (addr.isDefault()) {
                    addr.setDefault(false);
                }
            });
            existingAddress.setDefault(true);
        } else if (!newIsDefaultValue && existingAddress.isDefault()) {
            existingAddress.setDefault(false);
            // TODO: (Quan trọng) Cần có logic để đảm bảo luôn có ít nhất một địa chỉ mặc định cho người dùng.
        }

        return addressRepository.save(existingAddress);
    }


    // Xóa địa chỉ
    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));

        // Đảm bảo địa chỉ thuộc về người dùng hiện tại
        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access Denied: Address does not belong to this user.");
        }

        // Không cho phép xóa địa chỉ mặc định
        if (address.isDefault()) {
            throw new RuntimeException("Cannot delete default address. Please set another address as default first.");
        }

        addressRepository.delete(address);
    }
}