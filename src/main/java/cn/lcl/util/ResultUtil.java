package cn.lcl.util;

import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.pojo.result.Result;
import org.springframework.validation.BindingResult;

import java.util.function.Supplier;

public class ResultUtil {

    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    /**
     * 失败带着返回参数
     *
     * @param code 失败码
     * @param msg  失败信息
     * @return 返回失败信息
     */
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 此方法是springboot valid的字段验证
     *
     * @param bindingResult springboot的验证结果
     * @param serviceMethod 验证成功后执行的业务逻辑
     * @return 验证失败的处理结果或验证成功的 successResult
     */
    public static Result vaildFieldError(BindingResult bindingResult, Supplier<Result> serviceMethod) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    ResultEnum.MISS_FIELD.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        } else {
            return serviceMethod.get();
        }
    }

}
