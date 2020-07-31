package cn.lcl.exception;

import cn.lcl.exception.enums.ResultEnum;

/**
 * 自定义的异常，通过枚举类ResultEnum来构造
 * 继承RunTimeException，其继承了Exception，可进行事务回滚
 */
public class MyException extends RuntimeException {
    private Integer code;

    /**
     * 给构造函数，使用结果枚举类进行初始化,此类的作用是,使异常的返回信息 也是统一格式
     * 自定义异常格式
     * 当我们要抛自己的异常时，直接用枚举类就行了
     *
     * @param resultEnum
     */
    public MyException(ResultEnum resultEnum) {
        // 对应祖宗Throwable类 的 detailMessage字段
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
