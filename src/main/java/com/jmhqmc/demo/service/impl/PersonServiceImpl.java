package com.jmhqmc.demo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jmhqmc.demo.Constants;
import com.jmhqmc.demo.dao.PersonDao;
import com.jmhqmc.demo.domain.Person;
import com.jmhqmc.demo.service.PersonService;
import com.jmhqmc.demo.service.RedisCacheService;

import net.sf.json.JSONObject;

@Service("personService")
public class PersonServiceImpl implements PersonService {
	@Resource(name="personDao")
	private PersonDao personDao;
	
	@Resource
	private RedisCacheService redisCacheService;

	//@CachePut(value="person",key="#person.getId()")  
	public void insert(Person person, String collectionName) {
		String id = UUID.randomUUID().toString().replace("-", "");
		System.out.println(id);
		person.setId(id);
		personDao.insert(person, collectionName);
		
		redisCacheService.cachePut(person.getId(), person);
	}

	public Person findOne(Map<String, Object> params, String collectionName) {
		return personDao.findOne(params, collectionName);
	}

	public JSONObject findAll(String collectionName) {
		List<Person> list = personDao.findAll(collectionName);

		JSONObject datas = new JSONObject();
		// 设置总共有多少条记录
		datas.put(Constants.TOTAL, list.size());
		// 设置当前页的数据
		datas.put(Constants.ROWS, list);
		return datas;
	}

	public void update(Person person, String collectionName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", person.getId());
		map.put("age", person.getAge());
		map.put("username", person.getUsername());
		personDao.update(map, collectionName);
		
	}

	public void createCollection(String collectionName) {
		personDao.createCollection(collectionName);
	}

	//@CacheEvict(value="person",key="'id_'+#ids")  
	public void remove(String ids, String collectionName) {
		String[] strs = ids.split(",");
		for (int i = 0; i < strs.length; i++) {
			String id = strs[i];
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			personDao.remove(map, collectionName);
			redisCacheService.cacheRemove(id);
		}
	}

	public JSONObject findPerson(Person person, String collectionName) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Person> list = null;
		if ( person != null && person.getUsername() !=null && !person.getUsername().trim().equals("")) {
			map.put("username", person.getUsername());
			list = personDao.findPersonByName(map, collectionName);
		} else {
			list = personDao.findAll(collectionName);
		}
		
		JSONObject datas = new JSONObject();
		// 设置总共有多少条记录
		datas.put(Constants.TOTAL, list.size());
		// 设置当前页的数据
		datas.put(Constants.ROWS, list);
		return datas;
	}

}
