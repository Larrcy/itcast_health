package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import javax.management.Query;
import javax.naming.ldap.PagedResultsControl;
import java.util.List;

//由health_backend的CheckItemController 到此
//CheckItemController 服务接口
public interface CheckItemService {
    //添加
    public  void add(CheckItem checkItem);
    //分页查询
    public PageResult pageQuery(QueryPageBean queryPageBean);
    //根据删除
    public void deleteById(Integer id);
    //编辑检查项
    public void edit(CheckItem checkItem);

    //根据id查询
   public CheckItem findById(Integer id);

   //checkgroup下的查询所有
    public List<CheckItem> findAll();
}
