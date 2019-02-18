package com.jmhqmc.demo.service;

import com.jmhqmc.demo.domain.Person;

import net.sf.json.JSONObject;

public interface PersonService extends BaseService<Person>{
	JSONObject findPerson(Person person,String collectionName);
}
