package com.atguigu.common.config.exception;

import com.atguigu.common.result.Result;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

//    全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(){
        return  Result.fail().message("执行全局异常处理......");
    }

////    特定异常处理
//    @ExceptionHandler(ArithmeticException.class)
//    @ResponseBody
//    public Result error(ArithmeticException e){
//        e.printStackTrace();
//        return  Result.fail().message("执行特定异常处理......");
//    }


//    @ExceptionHandler(GuiguException.class)
//    @ResponseBody
//    public Result error(GuiguException e){
//        e.printStackTrace();
//        return Result.fail().message(e.getMsg()).code(e.getCode());
//    }
}
