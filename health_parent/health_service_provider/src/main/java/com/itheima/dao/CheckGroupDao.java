package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;
import java.util.Map;

/**
 * 持久层Dao接口
 */
public interface CheckGroupDao {
    //新增
    public void add(CheckGroup checkGroup);

    //设置检查组检查项
    public void setCheckGroupAndCheckItem(Map map);

    //分页查询
    public Page<CheckItem> selectByCondition(String queryString);

    //根据id查询
    public CheckGroup findById(Integer id);

    //查询中间组id
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    //编辑
    public void edit(CheckGroup checkGroup);

    //删除中间表
    public void deleteAssociation(Integer id);

    //根据id删除
    public void deleteById(Integer id);

    //查询所有
    public List<CheckGroup> findAll();

    //查询检查组id
    public long findCountByCheckGroupId(Integer id);
}
