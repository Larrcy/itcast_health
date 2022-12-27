package com.itheima.service;

import com.github.pagehelper.Page;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckGroupService {
    //添加
    public void add(CheckGroup checkGroup, Integer[] checkitemIds);

    //分页查询
    public PageResult pageQuery(QueryPageBean queryPageBean);

    //根据id查询
    public CheckGroup findById(Integer id);

    //寻找中间项中间表
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    //编辑
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    //根据id删除
    public void deleteById(Integer id);

    //查询所有检查项
    public List<CheckGroup> findAll();
}
