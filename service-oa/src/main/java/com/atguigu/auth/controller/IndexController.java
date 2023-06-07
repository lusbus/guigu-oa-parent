package com.atguigu.auth.controller;

import com.atguigu.common.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "后台登录管理系统")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    //login
    @PostMapping(value = "login")
    public Result login(){
//      {"code":20000,"data":{"token":"admin-token"}}
        Map<String, Object> map =new HashMap<>();
        map.put("token","admin-token");
        return Result.ok(map);

    }

    //info
    @GetMapping(value = "info")
    public Result info(){
/*      {"code":20000,
      "data":{"roles":["admin"],"introduction":"I am a super administrator",
              "avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
              "name":"Super Admin"}}
*/
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        return Result.ok(map);
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }


}
