package com.sportyshoes.modules.users.services;

import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.products.entity.Product;
import com.sportyshoes.modules.products.repository.ProductRepository;
import com.sportyshoes.modules.users.dto.UserDto;
import com.sportyshoes.modules.users.entity.User;
import com.sportyshoes.modules.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ReadAllUserService {


    private final UserRepository userRepository;

    @Autowired
    public ReadAllUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto>  execute() {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        userList.forEach(user -> userDtoList.add(user.toDto()));

        return userDtoList;
    }

}
