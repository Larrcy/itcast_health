package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.Utils.QiniuUtils;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
体检套餐管理
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private JedisPool jedisPool;

    //图片上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        try {
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(lastIndexOf - 1);
            //使用UUID随机产生文件名称，防止同名文件覆盖
            String fileName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            //图片上传成功
            Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS);
            result.setData(fileName);
            //将上传图片名称存入Redis，基于Redis的Set集合存储
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            //图片上传失败
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @Reference
    private SetmealService setmealService;

    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.add(setmeal, checkgroupIds);
        } catch (Exception e) {
            //新增套餐失败
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        //新增套餐成功
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }


    //检查组分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = setmealService.pageQuery(queryPageBean);
        return pageResult;

    }
    //根据id查询
    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id) {
        try {
            Setmeal setmeal = setmealService.findById(id);
            //新增成功
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);

        } catch (Exception e) {
            //新增失败
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    //查询中间关系表
    @RequestMapping("/findCheckGroupIdIdsBySetmealId")
    public List<Integer> findCheckGroupIdIdsBySetmealId(@RequestParam("id") Integer id) {
        try {
            List<Integer> list = setmealService.findCheckGroupIdIdsBySetmealId(id);
            //新增成功
            return list;

        } catch (Exception e) {
            //新增失败
            return new ArrayList<>();
        }
    }

    //编辑套餐
    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal, @RequestParam("checkgroupIds") Integer[] checkGroupIds) {
        try {
            setmealService.edit(setmeal, checkGroupIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            setmealService.deleteById(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }

}
