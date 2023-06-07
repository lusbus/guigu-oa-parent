package com.atguigu.auth;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.service.impl.SysRoleServiceImpl;
import com.atguigu.model.system.SysRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class TestMpDemo1 {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleServiceImpl sysRoleService;



    @Test
    @Transactional
    void sellectAll(){
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        System.out.println(sysRoles);
       sysRoleService.list();
    }

    @Test
     void InsertTest(){
        SysRole sysRole=new SysRole();
        sysRole.setRoleCode("staff");
        sysRole.setRoleName("刘海波");
        sysRole.setDescription("staff");

        int row=sysRoleMapper.insert(sysRole);
        System.out.println("row = "+ row);
    }

    @Test//删除
    void DeleteTest(){
        int row=sysRoleMapper.deleteById(11);
        System.out.println(row);
    }

    @Test
    void testQuery(){
        LambdaQueryWrapper<SysRole> queryWrapper=new LambdaQueryWrapper<>();
//        这里不能用bean中属性名称 roleName  只能用数据库表的列名称，
        queryWrapper.eq(SysRole::getRoleName,"CEO");

//        错误解决
//         QueryWrapper<SysRole> queryWrapper=new QueryWrapper<>();
//         queryWrapper.eq(SysRole::getRoleName,"CEO");会报错
//        解决办法
//       1.创建查询对象的时候，可以创建 LambdaQueryWrapper，就可以直接使用lambda表达式
//        2.创建了QueryWrapper，可以在QueryWrapper后加上 lambda。之后便可以使用lambda表达式！



        List<SysRole> sysRoles = sysRoleMapper.selectList(queryWrapper);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  "+sysRoles);
    }

    @Test
    void testQuery1(){
        QueryWrapper<SysRole> queryWrapper=new QueryWrapper<>();
//        这里不能用bean中属性名称 roleName  只能用数据库表的列名称，
        queryWrapper.eq("role_code","CEO");

        List<SysRole> sysRoles = sysRoleMapper.selectList(queryWrapper);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  "+sysRoles);
    }


    @BeforeEach
    void BeforeByEachTestMethod(){
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        sysRoles.forEach(sysRole -> {
            System.out.println("*************************************BeforeEach**************************************");
//            System.out.print("RoleName: "+sysRole.getRoleName());
//            System.out.println("RoleCode: "+sysRole.getRoleCode());
            System.out.println("ID      :"+sysRole.getId());
        });
    }


    @AfterEach
    void AfterByEachTestMethod(){
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        sysRoles.forEach(sysRole -> {
            System.out.println("************************************AfterEach****************************************");
//            System.out.print("RoleName: "+sysRole.getRoleName());
//            System.out.println("RoleCode: "+sysRole.getRoleCode());
            System.out.println("ID      :"+sysRole.getId());
        });
    }
}
