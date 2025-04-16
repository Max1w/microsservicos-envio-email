package com.ms.user.services.interfaces;

import com.ms.user.models.UserModel;
import com.ms.user.repositories.IUserRepository;

public interface IUserService {
    UserModel save(UserModel userModel);
}
