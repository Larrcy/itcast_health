package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

//创建在dao包下
//由health_service_provider的CheckItemServiceImpl到此
public interface CheckItemDao {
    //添加
    public void add(CheckItem checkItem);
    //分页查询
    public Page<CheckItem> selectByCondition(String queryString);
    //删除
    public void deleteById(Integer id);
    //查询中间关系表
    public long findCountByCheckItemId(Integer checkItemId);
    //编辑
    public void edit(CheckItem checkItem);
    //根据id查询
    public CheckItem findById(Integer id);
    //checkgroup下的查询所有
    public List<CheckItem> findAll();

}
