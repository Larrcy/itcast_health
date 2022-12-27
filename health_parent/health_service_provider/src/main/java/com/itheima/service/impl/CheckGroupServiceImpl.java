package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
检查组服务
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    CheckGroupDao checkGroupDao;


    //添加检查组合，同时需要设置检查组合和检查项的关联关系
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        setCheckGroupAndCheckItem(checkGroup.getId(), checkitemIds);
    }

    //抽取setCheckGroupAndCheckItem方法
    public void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkgroup_id", checkGroupId);
                map.put("checkitem_id", checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

    //分页查询
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        //queryString为查询条件 其类型传至xml里的parameterType
        String queryString = queryPageBean.getQueryString();
        //完成分页查询基于mybatis框架提供的分页助手插件完成
        //利用PageHelper
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> page = checkGroupDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();
        return new PageResult(total, rows);
    }

    //根据id查询
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    //查询中间项id
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    //编辑
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.检查组基本信息
        checkGroupDao.edit(checkGroup);
        //2.清理检查项和检查组的关联关系
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //3.重新建立检查项和检查组的关联关系
        setCheckGroupAndCheckItem(checkGroup.getId(), checkitemIds);
    }

//根据id删除
    public void deleteById(Integer id) {
        //被套餐引用时不能删除
        long count = checkGroupDao.findCountByCheckGroupId(id);
        if (count > 0) {
            //当前检查组被引用,不能删除
            throw new RuntimeException("当前检查项被引用,不能删除");
        }
        //不被引用时可以删除
        checkGroupDao.deleteAssociation(id);
        checkGroupDao.deleteById(id);
    }

    //查询所有检查组
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

}