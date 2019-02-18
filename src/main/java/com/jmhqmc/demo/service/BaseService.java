package com.jmhqmc.demo.service;

import java.util.Map;

import com.jmhqmc.demo.domain.Person;

import net.sf.json.JSONObject;

public interface BaseService<T> {
    //添加
    void insert(T object, String collectionName);

    //根据条件查找单个对象
    T findOne(Map<String, Object> params, String collectionName);

    //查找所有
    JSONObject findAll(String collectionName);

    //修改
    void update(Person person, String collectionName);

    //创建集合
    void createCollection(String collectionName);

    //根据条件删除
    void remove(String params, String collectionName);
}
