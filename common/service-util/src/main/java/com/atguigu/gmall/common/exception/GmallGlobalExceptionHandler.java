package com.atguigu.gmall.common.exception;

import com.atguigu.gmall.common.execption.GmallException;
import com.atguigu.gmall.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@ResponseBody
//@ControllerAdvice//这是一个异常处理器
@RestControllerAdvice//聚合了上面俩个，说明这时一个异常处理器，出现任何异常由它负责
public class GmallGlobalExceptionHandler {

    /**
     * 专门处理业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler(GmallException.class)//里面写的是处理谁的异常
    public Result handleException(GmallException ex){
        Integer code = ex.getCode();
        String message = ex.getMessage();

        Result<Object> fail = Result.fail();
        fail.setCode(code);
        fail.setMessage(message);

        return fail;
    }

    /**
     * 处理其他系统运行期间的异常
     * @return
     */
    @ExceptionHandler(Throwable.class)//Throwable是最大的异常，说明这能处理任何异常
    public Result handleSysException(Throwable throwable){

        Result<Object> fail = Result.fail();
        fail.setMessage(throwable.getMessage());

        return fail;
    }
}
