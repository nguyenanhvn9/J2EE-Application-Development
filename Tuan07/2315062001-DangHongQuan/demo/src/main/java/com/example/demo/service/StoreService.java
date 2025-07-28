package com.example.demo.service;

// package com.example.demo.service;

import com.example.demo.entity.Store;
import com.example.demo.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Optional<Store> getStoreById(Long id) {
        return storeRepository.findById(id);
    }

    @Transactional
    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }

    @Transactional
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }
}