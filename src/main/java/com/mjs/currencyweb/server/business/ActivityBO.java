package com.mjs.currencyweb.server.business;

import java.util.List;

import com.mjs.currencyweb.server.model.Result;

public interface ActivityBO {

  void save(Result result);

  List<Result> getLastActivities();
}
