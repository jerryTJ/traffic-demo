package com.ccb.agile.demo.component;

import org.springframework.beans.factory.annotation.Configurable;

import jakarta.annotation.PreDestroy;

@Configurable
public class PreDestory {

  @PreDestroy
  public void exitApp() {

  }

}
