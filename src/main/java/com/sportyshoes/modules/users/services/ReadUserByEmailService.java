package com.sportyshoes.modules.users.services;

import com.sportyshoes.modules.users.dto.UserDto;
import com.sportyshoes.modules.users.entity.User;
import com.sportyshoes.modules.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ReadUserByEmailService {


    private final UserRepository userRepository;

    @Autowired
    public ReadUserByEmailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Optional<UserDto> execute(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        UserDto userDto=null;
        if(userOptional.isPresent()) {
            userDto=userOptional.get().toDto();
        }

        return Optional.ofNullable(userDto);
    }

}
