package cn.lcl.exception.handle;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.pojo.result.Result;
import cn.lcl.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandle {

    /**
     * 捕获Exception异常，返回Result
     *
     * @param e 将异常上转型接受
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result handle(Exception e) {
        if (e instanceof MyException) {
            // 下转型回来，e中包含MyException中的code,msg
            MyException myException = (MyException) e;
            return ResultUtil.error(myException.getCode(), myException.getMessage());
        } else {
            log.error("系统异常{}", e);
            return ResultUtil.error(ResultEnum.UNKONW_ERROR.getCode(), ResultEnum.UNKONW_ERROR.getMsg());
        }
    }
}
