package com.mjs.currencyweb.mock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Profile("mock")
@RestController
@RequestMapping(value = "/mock/exchange")
public class OpenExchangeMock {

  public static final String MOCK_KEY = "d0a7d8aeaeca4f70a4bf377b4d15a72a";

  @GetMapping("/latest.json")
  ResponseEntity<String> fetchLatest(@RequestParam String app_id) throws IOException {
    if (app_id == null || !app_id.equalsIgnoreCase(MOCK_KEY)) {
      ResponseEntity responseEntity =
        new ResponseEntity(new HashMap<String, String>(), HttpStatus.BAD_REQUEST);
    }

    String content = new String(Files.readAllBytes(getFile("latest.json").toPath()));
    return new ResponseEntity(content, HttpStatus.OK);

  }

  @GetMapping("/currencies.json")
  ResponseEntity<String> fetchCurrencies(@RequestParam String appId) throws IOException {
    if (appId == null) {
      ResponseEntity responseEntity =
        new ResponseEntity(new HashMap<String, String>(), HttpStatus.BAD_REQUEST);
    }

    String content = new String(Files.readAllBytes(getFile("currencies.json").toPath()));
    return new ResponseEntity(content, HttpStatus.OK);
  }

  private File getFile(String fileName) {
    ClassLoader classLoader = this.getClass().getClassLoader();
    return new File(classLoader.getResource(fileName).getFile());
  }
}
