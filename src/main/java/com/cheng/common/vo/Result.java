package com.cheng.common.vo;

import com.cheng.sys.common.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Aaron
 * @date : 2023/4/29 15:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result <T>{
    private Integer code;
    private String message;
    private String description;
    private T data;


    public Result(Integer code,String message,T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Integer code,String message,String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }


    public static <T> Result<T> success() {
        return new Result<>(20000,"success",null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(20000,"success",data);
    }

    public static <T> Result<T> success(String message,T data) {
        return new Result<>(20000,message,data);
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(20000,message,null);
    }


    public static <T> Result<T> fail(StatusCode statusCode,String description) {
        return new Result<>(statusCode.getCode(), statusCode.getMessage(),description);
    }


    public static<T>  Result<T> fail(Integer code, String message){
        return new Result<>(code,message,null);
    }

}
