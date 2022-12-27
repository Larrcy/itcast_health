package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 体检套餐服务实现类
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;

    //新增套餐
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            setSetmealAndCheckGroup(setmeal, checkgroupIds);
        }
        //将图片名称保存到Redis
        savePic2Redis(setmeal.getImg());
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
        Page<CheckGroup> page = setmealDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckGroup> rows = page.getResult();
        return new PageResult(total, rows);
    }

    //将图片名称保存到Redis
    private void savePic2Redis(String pic) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, pic);
    }

    //抽取setSetmealAndCheckGroup方法
    private void setSetmealAndCheckGroup(Setmeal setmeal, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("setmeal_id", setmeal.getId());
                map.put("checkgroup_id", checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }

    //根据id查询
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    //查询组id
    public List<Integer> findCheckGroupIdIdsBySetmealId(Integer id) {
        return setmealDao.findCheckGroupIdIdsBySetmealId(id);
    }

    //编辑
    public void edit(Setmeal setmeal, Integer[] checkGroupIds) {
        //1.检查组基本信息
        setmealDao.edit(setmeal);
        //2.清理检查项和检查组的关联关系
        setmealDao.deleteAssociation(setmeal.getId());
        //3.重新建立检查项和检查组的关联关系
        setSetmealAndCheckGroup(setmeal, checkGroupIds);
        //将图片名称保存到Redis
        savePic2Redis(setmeal.getImg());
    }

    //根据id删除
    public void deleteById(Integer id) {
        //个人认为套餐目前属于最高层可以删除 所以就加上了删除中间关系表
        setmealDao.deleteAssociation(id);
        setmealDao.deleteById(id);
    }

}