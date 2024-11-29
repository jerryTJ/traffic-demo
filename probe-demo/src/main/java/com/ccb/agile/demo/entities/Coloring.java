package com.ccb.agile.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coloring {
  private String name;
  private String version;
  private String color;
  private String chainId;
  private String hostName;
  private Coloring next;
}
