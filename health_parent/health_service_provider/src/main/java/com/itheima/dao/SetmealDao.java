package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    //添加
    public void add(Setmeal setmeal);

    //添加中间表
    public void setSetmealAndCheckGroup(Map map);

    //分页查询
    public Page<CheckGroup> selectByCondition(String queryString);
    //根据id查询
    public Setmeal findById(Integer id);

    //查询中间组id
    public List<Integer> findCheckGroupIdIdsBySetmealId(Integer id);

    //编辑
    public void edit(Setmeal setmeal);

    //删除中间表
    public void deleteAssociation(Integer id);

    //根据id删除
    public void deleteById(Integer id);
}
