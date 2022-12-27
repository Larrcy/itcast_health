package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;

import java.util.List;

/**
 * 体检套餐服务接口
 */
public interface SetmealService {
    //添加
    public void add(Setmeal setmeal, Integer[] checkgroupIds);

    //分页查询
    public PageResult pageQuery(QueryPageBean queryPageBean);

    //根据id查询
    public Setmeal findById(Integer id);

    //寻找中间组id
    public List<Integer> findCheckGroupIdIdsBySetmealId(Integer id);

    //编辑
    public void edit(Setmeal setmeal, Integer[] checkGroupIds);

    //根据id删除
    public void deleteById(Integer id);
}
