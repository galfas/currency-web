package com.mjs.currencyweb.server.dao.historyRepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mjs.currencyweb.server.model.Result;

public interface ActivityRepository extends MongoRepository<Result, String> {
}
