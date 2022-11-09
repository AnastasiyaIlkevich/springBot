package com.botEx.springBot.repository;


import com.botEx.springBot.model.User;
import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<User,Long> {
}
