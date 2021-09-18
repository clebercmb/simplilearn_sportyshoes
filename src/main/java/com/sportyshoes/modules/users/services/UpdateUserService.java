package com.sportyshoes.modules.users.services;

import com.sportyshoes.modules.users.dto.UserDto;
import com.sportyshoes.modules.users.entity.User;
import com.sportyshoes.modules.users.repository.UserRepository;
import com.sportyshoes.share.exceptions.SportyShoesException;
import com.sportyshoes.share.exceptions.SportyShoesResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateUserService {

    private final UserRepository userRepository;


    @Autowired
    public UpdateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserDto> execute(UserDto userDto) throws  SportyShoesResourceNotFoundException, SportyShoesException {

        if (userDto == null) {
            throw new SportyShoesResourceNotFoundException("Please provide a user to be found ");
        }

        Optional<User> userOptional = userRepository.findById(userDto.getId());
        if(userOptional.isEmpty()) {
            throw new SportyShoesResourceNotFoundException("User "+userDto.getId()+" not found");
        }

        Optional<User> userWithSameEmail = userRepository.findByEmail(userDto.getEmail());


        if(userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(userOptional.get().getId()) ) {
            throw new SportyShoesException("Email "+userDto.getEmail()+" already used. Try another one");
        }

        if(userDto.getPassword() == null || userDto.getPassword().length() == 0) {
            throw new SportyShoesException("Password must not be empty");
        }

        User user = userOptional.get();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setUserType(userDto.getUserType());
        user.setEmail(userDto.getEmail());

        userRepository.save(user);

        return Optional.ofNullable(user.toDto());
    }
}
