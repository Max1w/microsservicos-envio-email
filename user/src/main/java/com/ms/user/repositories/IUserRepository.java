package com.ms.user.repositories;

import com.ms.user.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
}
