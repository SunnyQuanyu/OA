package cn.lcl.exception.enums;

public enum ResultEnum {
    /**
     * 0-100: 通用异常
     * 101-200: 用户异常
     * 201-300: 角色权限异常
     * 301-400: 小组权限异常
     * 401-500: 标签异常
     * 501-600: 事务一场
     * 定义了枚举实例，它的属性是类的私有变量，然后可以通过使用    该类的名称.枚举实例名.get(属性)
     */
    SUCCESS(0, "成功"),
    UNKONW_ERROR(1, "未知错误"),
    MISS_FIELD(2, ""), // 确实字段，错误信息由外层添加
    FILE_UPLOAD_FAILED(3, "文件上传失败"), // 确实字段，错误信息由外层添加
    GET_WX_TOKEN_FAILED(4,"获取微信token失败"),
    SEND_WX_NOTE_FAILED(5,"发送微信通知失败"),
    SEND_WX_NOTE_FAILED_IN_HTTP(6,"发送微信通知时发送微信请求失败"),
    NOT_AUTHENTICATION(101, "未登录"),
    USER_INFO_NOT_INTEGRITY(102, "用户信息不完整"),
    USER_LIST_HAS_REPEAT(103, "用户列表中与已有用户有重复"),
    USER_INSERT_FAILED(104, "用户列表中与已有用户有重复"),
    USER_NOT_FOUND(105, "用户没有找到"),
    USER_PASSWORD_FAILED(106, "用户密码错误"),
    USERS_FIND_ERROR(107, "用户密码错误"),
    USER_SAVE_OPENID_FAILED_IN_WX(108, "用户保存open_idwx部分失败"),
    USER_SAVE_OPENID_FAILED_IN_DATABASE(109, "用户保存open_id数据库部分失败"),
    PERMISSION_NOT_FOUND(201, "此权限没有找到"),
    ROLE_NOT_FOUND(202, "此角色没有找到"),
    DELETE_ROLE_PERMISSION_FAILED(203, "删除此角色的此权限失败"),
    DELETE_USER_ROLE_FAILED(204, "删除此用户的此角色失败"),
    ROLE_ALREADLY_HAS_THIS_PERMISSION(206, "此角色已经有此权限"),
    TEAM_MEMBER_NOT_FOUND(302, "此小组用户对应关系已经存在"),
    TEAM_MEMBER_INSERT_ERROR(303, "此小组用户对应关系插入失败"),
    TEAM_MEMBER_DELETE_ERROR(304, "此小组用户对应关系删除失败"),
    TEAM_NOT_FOUND(305, "小组没有找到"),
    TEAM_UPDATE_FAILED(306, "小组更新失败"),
    THING_NOT_FOUND(501,"事务未找到"),
    THING_HAS_FINISHED(502,"事务已经回执"),
    THING_AND_RECEIVER_NOT_FOUND(503,"事务接受者关系未找到"),
    THING_QUESTION_INSERT_FAILED(504,"问题插入失败"),
    THING_QUESTION_MUST_HAVE_OPTION(505,"问题必须要有选项"),
    QUESTION_THING_ID_NOT_NULL(506,"问题d必须要事务id"),
    THING_QUESTION_TYPE_NOT_NULL(507,"问题必须要有类型"),
    ANSWER_INSERT_FAILED(508,"问题保存失败"),
    THING_NOT_HAVE_TO_FINISH(509,"事务不需要完成"),
    THING_NOT_FINISHED(510,"事务未完成"),
    THING_TEAM_ID_NOT_NULL(511,"事务teamId不能为空"),
    THING_RECEIVERIDS_NOT_NULL(511,"事务receiverIds不能为空"),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /*属性已经枚举列出,只有get方法,去获取属性,不再设置,是通过  枚举实例名.get属性()*/
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
