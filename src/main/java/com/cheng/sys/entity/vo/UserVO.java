package com.cheng.sys.entity.vo;

import lombok.Data;

/**
 * @author : Aaron
 * @date : 2023/6/29 11:25
 */
@Data
public class UserVO {

    private Integer id;

    private String username;

    private String email;

    private String phone;

    private Integer status;

    private String avatar;
}
