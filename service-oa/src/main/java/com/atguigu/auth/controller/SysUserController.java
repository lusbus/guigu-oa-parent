package com.atguigu.auth.controller;


import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.AssginRoleVo;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-06-06
 */
@Api(tags = "用户管理系统")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
//    注入SysUserService
    @Autowired
    private SysUserService sysUserService;




//    用户条件分页查询
    @ApiOperation("用户分页查询")
    @GetMapping(value = "{page}/{limit}")
    public Result index(@PathVariable("page")Long page
                            , @PathVariable("limit")Long limit
                            , SysUserQueryVo sysUserQueryVo){
//        创建page对象 传入参数 page  limit
        Page<SysUser> page1=new Page<>(page,limit);

//        封装条件，不为空则封装
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper=new LambdaQueryWrapper<>();

        String username = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();

        if (!StringUtils.isEmpty(username)){
             lambdaQueryWrapper.like(SysUser::getUsername, username);
        }
        if (!StringUtils.isEmpty(createTimeBegin)){
//             lambdaQueryWrapper.ge()  大于等于
            lambdaQueryWrapper.ge(SysUser::getCreateTime, createTimeBegin);
        }
        if (!StringUtils.isEmpty(createTimeEnd)){
//            lambdaQueryWrapper.le()  小于等于
            lambdaQueryWrapper.le(SysUser::getCreateTime, createTimeEnd);
        }

//        调用方式实现条件分页查询
        Page<SysUser> page2 = sysUserService.page(page1, lambdaQueryWrapper);

        return Result.ok(page2);
    }

    //        查询所有角色
    @ApiOperation("查询所有角色")//http://localhost:8000/doc.html
    @GetMapping(value = "/findAll")
    public Result findAll(){

//        调用service方法
        List<SysUser> list = sysUserService.list();
        return Result.ok(list);
    }

    //    添加角色
    @ApiOperation("添加角色")
    @PostMapping("save")
    public Result save(@RequestBody SysUser sysUser){

//        调用service方法
        Boolean is_success = sysUserService.save(sysUser);
        if (is_success)
            return Result.ok();
        else
            return Result.fail();
    }


    //    查询角色-根据id查询
    @ApiOperation("根据id获取角色")
    @GetMapping("get/{id}")
    public Result get(@PathVariable("id") Long id){
//        调用Service方法
        SysUser byId = sysUserService.getById(id);
        String userName = byId.getUsername();

//        不为空返回成功
        if (!StringUtils.isEmpty(userName))
            return Result.ok(byId);
        else
            return Result.fail();
    }


    //    修改角色
    @ApiOperation("修改角色")
    @PutMapping("update")
    public Result update(@RequestBody SysUser sysUser){
//        调用service方法
        boolean is_success = sysUserService.updateById(sysUser);

        if (is_success)
            return Result.ok();
        else
            return Result.fail();
    }


    //    根据id删除
    @ApiOperation("删除角色")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable("id") Long id){
//        调用service方法
        boolean removeById = sysUserService.removeById(id);
        if (removeById)
            return Result.ok();
        else
            return Result.fail();
    }


    //    批量删除
//    前端用数组传递
    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){
        //        调用service方法
        boolean is_success = sysUserService.removeByIds(idList);
        if (is_success)
            return Result.ok();
        else
            return Result.fail();
    }
}

