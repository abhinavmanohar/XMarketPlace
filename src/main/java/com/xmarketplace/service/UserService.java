package com.xmarketplace.service;

import com.xmarketplace.Entity.User;
import com.xmarketplace.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> GetUserById(int id) {
        return userRepository.findById(id);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public boolean updateUserWallet(int id, double amount) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setWalletBalance(user.get().getWalletBalance() + amount);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }
}
