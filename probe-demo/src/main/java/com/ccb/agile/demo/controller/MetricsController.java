package com.ccb.agile.demo.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;

@Controller
public class MetricsController {

  @Autowired
  MeterRegistry meterRegistry;

  private Counter COUNTER_CORE;

  private AtomicInteger APP_ONLINE_COUNT;

  @PostConstruct
  public void init() {
    COUNTER_CORE = meterRegistry.counter("app_requests_method_count", "method", "MetricsController.core");
    APP_ONLINE_COUNT = meterRegistry.gauge("app_online_count", new AtomicInteger(0));
  }

  @RequestMapping(value = "simulate", method = RequestMethod.GET)
  public ResponseEntity<Boolean> simulateRequest() {
    COUNTER_CORE.increment();
    return ResponseEntity.ok().body(true);
  }

  @RequestMapping(value = "online", method = RequestMethod.GET)
  public ResponseEntity<Boolean> simulateOnline() {
    APP_ONLINE_COUNT.set(1000);
    return ResponseEntity.ok(true);
  }
}