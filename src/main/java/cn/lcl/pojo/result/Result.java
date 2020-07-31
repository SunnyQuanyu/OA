package cn.lcl.pojo.result;

import lombok.Data;

/**
 * 返回给前端的最外层对象
 */
@Data
public class Result {

    private Integer code;

    private String msg;

    private Object data;

}
