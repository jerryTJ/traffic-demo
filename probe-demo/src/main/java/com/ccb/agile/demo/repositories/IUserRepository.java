package com.ccb.agile.demo.repositories;

import com.ccb.agile.demo.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {
}
