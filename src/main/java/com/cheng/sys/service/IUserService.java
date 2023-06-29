package com.cheng.sys.service;

import com.cheng.common.vo.Result;
import com.cheng.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheng.sys.entity.request.LoginRequest;
import com.cheng.sys.entity.request.RegisterRequest;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aaron
 * @since 2023-04-29
 */
public interface IUserService extends IService<User> {

    Map<String, Object> login(LoginRequest loginRequest);

    Map<String, Object> getUserInfo(String token);

    void logout(String token);

    Result<String> register(RegisterRequest registerRequest);
}
