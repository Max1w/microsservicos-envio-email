package com.ms.user.services;

import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.IUserRepository;
import com.ms.user.services.interfaces.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    final IUserRepository userRepository;
    final UserProducer userProducer;

    public UserService(IUserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    @Transactional
    public UserModel save(UserModel userModel) {
        userModel = userRepository.save(userModel);
        userProducer.publishMessageEmail(userModel);
        return userModel;
    }

}
