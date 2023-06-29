package com.cheng.sys.entity.request;

import lombok.Data;

/**
 * @author : Aaron
 * @date : 2023/6/29 11:23
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
