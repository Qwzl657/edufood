package com.edufood.edufood.service;

import com.edufood.edufood.entity.Dish;
import com.edufood.edufood.entity.Order;
import com.edufood.edufood.entity.OrderItem;
import com.edufood.edufood.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(String email, String name, String password) {
        log.info("Регистрация пользователя: {}", email);

        if (userRepository.findByEmail(email).isPresent()) {
            log.warn("Email уже занят: {}", email);
            throw new RuntimeException("Email уже используется");
        }

        User user = User.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
        log.info("Пользователь зарегистрирован: {}", email);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
