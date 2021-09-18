package com.sportyshoes.modules.users.services;

import com.sportyshoes.modules.users.dto.UserDto;
import com.sportyshoes.modules.users.entity.User;
import com.sportyshoes.modules.users.repository.UserRepository;
import com.sportyshoes.share.exceptions.SportyShoesResourceAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserService {

    private final UserRepository userRepository;

    @Autowired
    public CreateUserService(UserRepository userRepository) {
;
        this.userRepository = userRepository;
    }


    public UserDto execute(UserDto userDto) throws SportyShoesResourceAlreadyExistException {


        Optional<User> hasUserWithEmail = userRepository.findByEmail(userDto.getEmail());

        if(hasUserWithEmail.isPresent()) {
            throw new SportyShoesResourceAlreadyExistException("Email already used. Try another one");
        }

        User user = User.builder().
                name(userDto.getName()).
                email(userDto.getEmail()).
                password(userDto.getPassword()).
                userType(userDto.getUserType()).
                build();

        user = userRepository.save(user);
        return user.toDto();
    }

}
