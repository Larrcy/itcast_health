package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//由health_interface的CheckItemService到此

/*
检查项服务
 */
//用dubbo里的service注解
//使用事务注解的时候要明确服务的具体接口
@Service(interfaceClass = CheckItemService.class)
@Transactional//事务注解
public class CheckItemServiceImpl implements CheckItemService {
    //注入dao对象
    @Autowired
    private CheckItemDao checkItemDao;

    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    //检查项分页查询
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        //queryString为查询条件 其类型传至xml里的parameterType
        String queryString = queryPageBean.getQueryString();
        //完成分页查询基于mybatis框架提供的分页助手插件完成
        //利用PageHelper
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();
        return new PageResult(total, rows);
    }

    //根据id删除
    public void deleteById(Integer id) throws RuntimeException {
        long count = checkItemDao.findCountByCheckItemId(id);
        if (count > 0) {
            //当前检查项被引用,不能删除
         throw new RuntimeException("当前检查项被引用,不能删除");
        }
        checkItemDao.deleteById(id);
    }

    //编辑
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    //根据id查询
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

//查询所有检查项
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
