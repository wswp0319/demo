package com.jmhqmc.demo.dao;

import java.util.List;
import java.util.Map;

import com.jmhqmc.demo.domain.Person;

public interface PersonDao extends BaseDao<Person> {
	List<Person> findPersonByName(Map<String, Object> params, String collectionName);
}