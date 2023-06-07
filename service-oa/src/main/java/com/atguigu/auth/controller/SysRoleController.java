package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysRoleService;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.LambdaMetafactory;
import java.util.List;
import java.util.Map;

@Api(tags = "角色管理系统")
@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

//    http://localhost:8000/admin/system/sysRole/(method.value)

//    注入Service
    @Autowired
    private SysRoleService sysRoleService;

    //    1 查询所有角色 和 当前用户所属角色
    @ApiOperation(value = "查询用户角色")
    @GetMapping(value = "/toAssign/{userId}")
    public Result toAssign(@PathVariable("userId")Long userId ){
        Map<String,Object> map =sysRoleService.findRoleDtaByUserId(userId);
        return Result.ok(map);
    }

    //    2 为用户分配角色
    @ApiOperation(value = "为用户分配角色")
    @PostMapping(value = "/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo){
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }

////    查询所有角色
//    @GetMapping(value = "/findAll")
//    public List<SysRole> findAll(){
//
////        调用service方法
//        List<SysRole> list = sysRoleService.list();
//        return list;
//    }

//        查询所有角色
    @ApiOperation("查询所有角色")//http://localhost:8000/doc.html
    @GetMapping(value = "/findAll")
    public Result findAll(){

//        调用service方法
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }


//SysRoleQueryVo条件分页查询对象
    @ApiOperation("条件分页查询")
    @GetMapping(value = "{page}/{limit}")
    public Result pageQueryRole(@PathVariable("page") Long page
                                , @PathVariable("limit") Long limit
                                , SysRoleQueryVo sysRoleQueryVo){

//        传递分页相关参数（page，limit），创建Page对象
        Page<SysRole> pageParam = new Page<>(page,limit);
//        int i=10/0;  //全局异常处理举例代码
//        封装条件，不为空封装

        LambdaQueryWrapper<SysRole> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if (!StringUtils.isEmpty(roleName)){
//            封装（模糊查询）
            lambdaQueryWrapper.like(SysRole::getRoleName,roleName);
        }

//          调用方法实现
        IPage<SysRole> page1 = sysRoleService.page(pageParam,lambdaQueryWrapper);

        return Result.ok(page1);
    }


//    添加角色
    @ApiOperation("添加角色")
    @PostMapping("save")
    public Result save(@RequestBody SysRole sysRole){

//        调用service方法
        Boolean is_success = sysRoleService.save(sysRole);
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
        SysRole byId = sysRoleService.getById(id);
        String roleName = byId.getRoleName();

//        不为空返回成功
        if (!StringUtils.isEmpty(roleName))
            return Result.ok(byId);
        else
            return Result.fail();
    }


//    修改角色
    @ApiOperation("修改角色")
    @PutMapping("update")
    public Result update(@RequestBody SysRole role){
//        调用service方法
        boolean is_success = sysRoleService.updateById(role);

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
        boolean removeById = sysRoleService.removeById(id);
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
        boolean is_success = sysRoleService.removeByIds(idList);
        if (is_success)
            return Result.ok();
        else
            return Result.fail();
    }
}



