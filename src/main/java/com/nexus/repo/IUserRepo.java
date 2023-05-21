package com.nexus.repo;

import com.nexus.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IUserRepo extends MongoRepository<Users, String> {

    @Query("{userRole:'?0'}")
    List<Users> findUserByRole(String userRole);
    @Query("{userType:'?0'}")

    List<Users> findUserByType(String userType);
}
