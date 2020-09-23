package com.proximyst.productfilter.controller;

import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ErrorController.ERROR_PATH)
public class ErrorController extends AbstractErrorController {
  protected static final String ERROR_PATH = "/error";

  @Autowired
  public ErrorController(ErrorAttributes attributes) {
    super(attributes, Collections.emptyList());
  }

  @RequestMapping
  public ResponseEntity<Map<String, Object>> error(HttpServletRequest req) {
    return new ResponseEntity<>(
        getErrorAttributes(req, ErrorAttributeOptions.defaults()),
        getStatus(req)
    );
  }

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }
}
