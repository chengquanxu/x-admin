package com.cheng.sys.entity.request;

import lombok.Data;

/**
 * @author : Aaron
 * @date : 2023/6/29 9:52
 */
@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String checkPassword;

    public RegisterRequest(String username,String password) {
        this.username = username;
        this.password = password;
    }
}
