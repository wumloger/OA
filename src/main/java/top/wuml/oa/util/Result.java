package top.wuml.oa.util;

import lombok.Data;

@Data
public class Result {
    private String msg;
    private int code;
    private Object data;

    public Result(String msg, int code) {
        this.msg = msg;
        this.code = code;

    }

    public Result(Object data, int code) {
        this.data = data;
        this.code = code;
    }

    public static Result success(Object data){
        return new Result(data,1);
    }

    public static Result error(String msg){
        return new Result(msg,0);
    }
}
