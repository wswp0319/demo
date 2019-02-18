package com.jmhqmc.demo.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.jmhqmc.demo.dao.PersonDao;
import com.jmhqmc.demo.domain.Person;

@Repository("personDao")
public class PersonDaoImpl implements PersonDao {
	@Resource
	@Qualifier("mongoTemplate")
	private MongoTemplate mongoTemplate;

	public void insert(Person object, String collectionName) {
		mongoTemplate.insert(object, collectionName);
	}

	public Person findOne(Map<String, Object> params, String collectionName) {
		return mongoTemplate.findOne(new Query(Criteria.where("username").is(params.get("username"))), Person.class,
				collectionName);
	}

	public List<Person> findAll(String collectionName) {
		return mongoTemplate.findAll(Person.class, collectionName);
	}

	public void update(Map<String, Object> params, String collectionName) {
		Update update = new Update();
		if (params.get("username") != null) {
			update.set("username", params.get("username"));
		}
		if (params.get("age") != null) {
			update.set("age", params.get("age"));
		}
		mongoTemplate.upsert(new Query(Criteria.where("id").is(params.get("id"))), update, Person.class,
				collectionName);
	}

	public void createCollection(String collectionName) {
		mongoTemplate.createCollection(collectionName);
	}

	public void remove(Map<String, Object> params, String collectionName) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(params.get("id"))), Person.class, collectionName);
	}

	public List<Person> findPersonByName(Map<String, Object> params, String collectionName) {
		Query query = new Query();
		String username = (String) params.get("username");
		query.addCriteria(Criteria.where("username").regex(username));

		List<Person> person = mongoTemplate.find(query, Person.class, collectionName);
		return person;
	}
}