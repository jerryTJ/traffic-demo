package com.ccb.agile.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coloring {
  private Integer id;
  private String name;
  private String version;
  private String domain;
  private String port;
  private String image;
  private String tag;
  private String color;
  private String chainId;
  private String hostName;
  private Coloring next;
}
