package com.sportyshoes.modules.users.services;

import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.products.entity.Product;
import com.sportyshoes.modules.products.repository.ProductRepository;
import com.sportyshoes.modules.users.dto.UserDto;
import com.sportyshoes.modules.users.entity.User;
import com.sportyshoes.modules.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ReadUserService {


    private final UserRepository userRepository;

    @Autowired
    public ReadUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Optional<UserDto> execute(Long id) {

        Optional<User> userOptional = userRepository.findById(id);

        UserDto userDto=null;
        if(userOptional.isPresent()) {
            userDto=userOptional.get().toDto();
        }

        return Optional.ofNullable(userDto);
    }

}
