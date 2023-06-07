package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.service.SysRoleService;
import com.atguigu.auth.service.SysUserRoleService;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    //    1 查询所有角色 和 当前用户所属角色
    @Override
    public Map<String, Object> findRoleDtaByUserId(Long userId) {

        //1 查询所有角色，返回List集合
        List<SysRole> sysRoles
                = baseMapper.selectList(null);

        //2 根据userid查询角色用户关系表，查询userid对应的所有id
        LambdaQueryWrapper<SysUserRole> wrapper
                = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId,userId);
        List<SysUserRole> existUserRoleList
                = sysUserRoleService.list(wrapper);

        //从获取的list中将所有user对应的roleId取出 存放list
        List<Long> existRoleIdList = existUserRoleList.stream()
                                .map(c -> c.getRoleId())
                                .collect(Collectors.toList());

        //3 根据查询所有角色id，找到对应角色信息
        List<SysRole> list=new ArrayList<>();
        for (SysRole sysRole : sysRoles) {
            if (existRoleIdList.contains(sysRole.getId())){
                list.add(sysRole);
            }
        }


        //4 把得到的集合封装成map 返回


        return null;
    }

    //    2 为用户分配角色
    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {



    }
}
