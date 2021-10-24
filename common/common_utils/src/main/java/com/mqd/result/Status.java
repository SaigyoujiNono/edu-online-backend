package com.mqd.result;

public enum Status {
    OK(20000,"成功"),

    FAILED(40001,"失败"),
    ERROR(40002,"错误"),
    PARAMETER_ERROR(40003,"参数错误"),
    ADD_FAILED(40010,"添加失败"),
    REMOVE_FAILED(40011,"删除失败"),
    UPDATE_FAILED(40012,"更新失败"),
    FILE_MAXSIZE_OVERFLOW(40100,"文件大小超过最大值!");


    public final int code;
    public final String msg;
    Status(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
