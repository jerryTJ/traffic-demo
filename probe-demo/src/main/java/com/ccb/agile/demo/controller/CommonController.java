package com.ccb.agile.demo.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ccb.agile.demo.component.ProbeEvent;
import com.ccb.agile.demo.entities.User;
import com.ccb.agile.demo.services.IUserService;

/**
 * kubernetes根据接口返回的httpStatus 决定服务是否健康
 * 状态码: status
 * 健康: 200<=status<400
 * 非健康: status<200 or status >=400
 * 
 */
@Controller
public class CommonController {

  @Autowired
  IUserService userService;

  @Autowired
  ProbeEvent probeEvent;

  public static final Integer DEFAULT_USER_ID = 1;

  Logger logger = LoggerFactory.getLogger(CommonController.class);

  /**
   * startup probe
   * 用来标识程序是否已经启动，在该probe生效前，liveness和readness不可用
   * 集成了spring-boot-starter-actuator可以用 actuator/health代替
   */
  @RequestMapping(value = "health", method = RequestMethod.GET)
  public ResponseEntity<Health> health() {
    return ResponseEntity.ok().body(Health.up().build());
  }

  /**
   * liveness probe 
   * 用来标识是否需要重启pod。
   * 1、程序运行，但不能处理请求，需要重启才能对外提供服务。如线程死锁，数据库连接池被占满，
   * 2、程序长时间运行，进入中断状态，只能重启来解决
   * 检查内存、死锁、数据库链接
   * 集成了spring-boot-starter-actuator可用/actuator/health/liveness代替
   * 
   */
  @RequestMapping(value = "liveness", method = RequestMethod.GET)
  public ResponseEntity<Health> liveness() {
    LivenessState livenessState = probeEvent.getLivenessState();
    if (livenessState.equals(LivenessState.BROKEN)) {
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Health.down().build());
    }
    return ResponseEntity.ok().body(Health.up().build());
  }

  @RequestMapping(value = "liveness", method = RequestMethod.PUT)
  public ResponseEntity<Boolean> restartApp() {
    probeEvent.brokenLiveness();
    return ResponseEntity.ok().body(true);
  }

  /**
   * readiness probe
   * 用来标识应用可以接收外部流量
   * 检查依赖的服务是否可用
   * 集成了spring-boot-starter-actuator可用/actuator/health/readiness代替
   * @return
   */
  @RequestMapping(value = "readiness", method = RequestMethod.GET)
  public ResponseEntity<Health> readiness() {
    ReadinessState readinessState = probeEvent.getReadinessState();
    if (readinessState.equals(ReadinessState.REFUSING_TRAFFIC)) {
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Health.down().build());
    }
    return ResponseEntity.ok().body(Health.up().build());
  }

  /**
   * 模拟readiness停止接收外部请求
   * @return
   */
  @RequestMapping(value = "readiness", method = RequestMethod.PUT)
  public ResponseEntity<Boolean> stopTriffic() {
    probeEvent.stopTraffic();
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
  }

  @RequestMapping(value = "users/{userId}", method = RequestMethod.GET)
  public ResponseEntity<User> getUserInfo(@PathVariable Integer userId) {
    User user = userService.getUserById(userId);
    if (Objects.nonNull(user)) {
      logger.debug("user", "user name:%s", user.getName());
    }
    return ResponseEntity.ok(user);
  }

  @RequestMapping(value = "versions", method = RequestMethod.GET)
  public ResponseEntity<String> getVerson() {
    return ResponseEntity.ok("v1");
  }

}