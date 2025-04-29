package com.ccb.agile.demo.controller;

import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;

import com.ccb.agile.demo.entities.Coloring;

import reactor.core.publisher.Mono;

@Controller
public class ColoringTrafficController {
  Logger logger = LoggerFactory.getLogger(ColoringTrafficController.class);

  @Value("${NEXT_SERVER_URL")
  private String nextServerUrl;
  @Value("${DEPLOY_VERDION}")
  private String version;

  private final WebClient webClient;

  public ColoringTrafficController(WebClient.Builder webClientBuilder) {
    if (Objects.isNull(nextServerUrl)) {
      nextServerUrl = System.getenv("NEXT_SERVER_URL");
      logger.info("NEXT_SERVER_URL:%s".formatted(nextServerUrl));
    }
    this.webClient = webClientBuilder.baseUrl(nextServerUrl).build();
  }

  @RequestMapping(value = "/color/v1", method = RequestMethod.POST)
  public ResponseEntity<Coloring> getColResponseEntityV1(@RequestHeader Map<String, String> header,
      @RequestBody Coloring coloring) {
    logger.debug(String.format("coloring/v1 header:%s", header.toString()));
    String color = header.get("x-color");
    coloring.setColor(color);
    String chainId = header.get("x-chain");
    coloring.setChainId(chainId);
    coloring.setVersion(version);
    String hostName = header.get("host");
    coloring.setHostName(hostName);
    Mono<Coloring> v2 = webClient.post().uri("/color/v2").bodyValue(coloring).retrieve().bodyToMono(Coloring.class);
    coloring.setNext(v2.block());
    return ResponseEntity.ok(coloring);
  }

  @RequestMapping(value = "/color/v2", method = RequestMethod.POST)
  public ResponseEntity<Coloring> getColResponseEntityV2(@RequestHeader Map<String, String> header,
      @RequestBody Coloring coloring) {
    logger.debug(String.format("coloring/v2 header:%s", header.toString()));
    String color = header.get("x-color");
    coloring.setColor(color);
    String chainId = header.get("x-chain");
    coloring.setChainId(chainId);
    coloring.setVersion(version);
    String hostName = header.get("host");
    coloring.setHostName(hostName);
    Mono<Coloring> v3 = webClient.post().uri("/color/v3").bodyValue(coloring).retrieve().bodyToMono(Coloring.class);
    coloring.setNext(v3.block());
    return ResponseEntity.ok(coloring);
  }

  @RequestMapping(value = "/color/v3", method = RequestMethod.POST)
  public ResponseEntity<Coloring> getColResponseEntityV3(@RequestHeader Map<String, String> header,
      @RequestBody Coloring coloring) {
    logger.debug(String.format("coloring/v3 header:%s", header.toString()));
    String color = header.get("x-color");
    String chainId = header.get("x-chain");
    String hostName = header.get("host");
    coloring.setChainId(chainId);
    coloring.setColor(color);
    coloring.setHostName(hostName);
    coloring.setVersion("v3-1");
    coloring.setNext(null);
    return ResponseEntity.ok(coloring);
  }
}
