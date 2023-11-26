package com.adventure.assignment.userservice.repository;

import com.adventure.assignment.userservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
}
