package com.mjs.currencyweb.server.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjs.currencyweb.server.business.ActivityBO;
import com.mjs.currencyweb.server.dao.historyRepository.ActivityRepository;
import com.mjs.currencyweb.server.model.Result;

@Component
public class ActivityBOimpl implements ActivityBO {

  @Autowired
  private ActivityRepository activityRepository;


  public void save(Result result) {
    activityRepository.save(result);
  }

  @Override
  public List<Result> getLastActivities() {
    return activityRepository.findAll();
  }
}
