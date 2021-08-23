package com.sportyshoes.modules.login.services;

import com.sportyshoes.modules.users.dto.UserDto;
import com.sportyshoes.modules.users.entity.User;
import com.sportyshoes.modules.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ValidateLoginService {


    private final UserRepository userRepository;

    @Autowired
    public ValidateLoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean execute(UserDto userToBeValidated) {

        Optional<User> userOptional = userRepository.findByEmail(userToBeValidated.getEmail());

        if(userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        return user.getEmail().equals(userToBeValidated.getEmail()) && user.getPassword().equals(userToBeValidated.getPassword());

    }

}
