package com.mjs.currencyweb.server.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mjs.currencyweb.server.business.ActivityBO;
import com.mjs.currencyweb.server.model.Result;

@RestController
public class ActivityController extends BaseController {

  @Autowired
  ActivityBO activityBO;

  @RequestMapping("/activities")
  public List<Result> lastActitivities() throws IOException {
    return activityBO.getLastActivities();
  }
}
