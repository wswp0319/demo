package com.jmhqmc.demo.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.jmhqmc.demo.dao.BaseDao;
import com.jmhqmc.demo.domain.Person;

@Repository("mongoDao")
public class MongoDaoImpl implements BaseDao<Object> {
	@Resource
	@Qualifier("mongoTemplate")
	private MongoTemplate mongoTemplate;

	public void insert(Object object, String collectionName) {
		mongoTemplate.insert(object, collectionName);
	}

	public Object findOne(Map params, String collectionName) {
		return mongoTemplate.findOne(new Query(Criteria.where("username").is(params.get("username"))), Person.class,
				collectionName);
	}

	public List findAll(String collectionName) {
		return mongoTemplate.findAll(Person.class, collectionName);
	}

	public void update(Map params, String collectionName) {
		// TODO Auto-generated method stub

	}

	public void createCollection(String collectionName) {
		mongoTemplate.createCollection(collectionName);
	}

	public void remove(Map params, String collectionName) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(params.get("id"))), Person.class, collectionName);
	}

}
