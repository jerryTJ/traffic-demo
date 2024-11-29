package com.ccb.agile.demo.services;

import java.util.Optional;

import com.ccb.agile.demo.entities.User;
import com.ccb.agile.demo.repositories.IUserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  IUserRepository userRepository;

  Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Override
  public User getUserById(Integer userId) {
    Optional<User> user = userRepository.findById(userId);
    if (user.isPresent()) {
      logger.debug("query user by", "%s", userId);
      User u = user.get();
      logger.debug("user name ", "%s", u.getName());
      return u;
    }
    return null;
  }

}
