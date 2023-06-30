package com.cheng.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheng.common.utils.JwtUtil;
import com.cheng.common.vo.Result;
import com.cheng.sys.entity.User;
import com.cheng.sys.entity.request.LoginRequest;
import com.cheng.sys.entity.request.RegisterRequest;
import com.cheng.sys.entity.vo.UserVO;
import com.cheng.sys.mapper.UserMapper;
import com.cheng.sys.service.IUserService;
import com.cheng.sys.utils.EmailGenerator;
import com.cheng.sys.utils.PasswordUtils;
import com.cheng.sys.utils.PhoneNumberGenerator;
import com.cheng.sys.utils.RegexValidator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cheng.sys.common.StatusCode.PARAMS_ERROR;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aaron
 * @since 2023-04-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Resource
    private RedisTemplate<Object,Object> redisTemplate;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public Map<String, Object> login(LoginRequest loginRequest) {

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        // 根据用户名查询
        lqw.eq(User::getUsername, loginRequest.getUsername());
        User loginUser = this.baseMapper.selectOne(lqw);
        // 如果结果不为空，并且密码和传入的是匹配的，则生成token，将用户信息存入redis
        if (loginUser != null && passwordEncoder.matches(loginRequest.getPassword(), loginUser.getPassword())) {
            //String key = "user:" + UUID.randomUUID();
            // 存入redis
            loginUser.setPassword(null);

            // 创建jwt
            String token = jwtUtil.createToken(loginUser);

            //redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.HOURS);
            Map<String,Object> data = new HashMap<>();
            data.put("token",token);
            return data;
        }
        return null;
    }

    /*@Override
    public Map<String, Object> login(User user) {

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        // 根据用户名和密码查询
        lqw.eq(User::getUsername, user.getUsername());
        lqw.eq(User::getPassword, user.getPassword());
        User loginUser = this.baseMapper.selectOne(lqw);

        // 如果结果不为空，则生成token，将用户信息存入redis
        if (loginUser != null) {
            String key = "user:" + UUID.randomUUID();

            // 存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.HOURS);


            Map<String,Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }


        return null;
    }*/

    @Override
    public Result<Integer> register(RegisterRequest registerRequest) {

        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String checkPassword = registerRequest.getCheckPassword();

        if (!(StrUtil.isAllNotBlank(username,password,checkPassword))) {
//            return Result.fail(40001,"输入不能为空");
            return Result.fail(PARAMS_ERROR,"输入不能为空");
        }

        if (RegexValidator.isValidUsername(username)) {
            return Result.fail(PARAMS_ERROR,"用户名不合法");
        }

        if (RegexValidator.isValidPassword(password)) {
            return Result.fail(PARAMS_ERROR,"密码不合法，至少6位");
        }

        if (RegexValidator.isValidPassword(checkPassword)) {
            return Result.fail(PARAMS_ERROR,"重复密码不合法，至少6位");
        }

        if (!(password.equals(checkPassword))) {
            return Result.fail(PARAMS_ERROR,"两次密码不一致");
        }

        // 判断用户是否已经存在
        User selectUser = baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (BeanUtil.isNotEmpty(selectUser)) {
            return Result.fail(PARAMS_ERROR,"用户名已存在");
        }

        // 对密码加密
        String encryptPassword = PasswordUtils.encryptPassword(password);

        // 存入数据库
        RegisterRequest registerUser = new RegisterRequest(username, encryptPassword);
        User user = BeanUtil.copyProperties(registerUser, User.class);
        user.setAvatar("https://pic1.imgdb.cn/item/645c8db60d2dde57779a7082.jpg"); // 设置默认的 avatar 值
        user.setStatus(1);
        user.setPhone(PhoneNumberGenerator.generateRandomPhoneNumber());
        user.setEmail(EmailGenerator.generateRandomEmail(username));
        save(user);

        return Result.success("注册成功",user.getId());
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        // 从redis查询token
        //Object obj = redisTemplate.opsForValue().get(token);
        UserVO loginUser;
        try {
            loginUser = jwtUtil.parseToken(token, UserVO.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 反序列化
        //User loginUser = JSON.parseObject(JSON.toJSONString(user),User.class);
        if(loginUser != null){
            Map<String, Object> data =  new HashMap<>();
            data.put("name",loginUser.getUsername());
            data.put("phone",loginUser.getPhone());
            data.put("email",loginUser.getEmail());
            data.put("avatar",loginUser.getAvatar());
            List<String> roleList = this.getBaseMapper().getRoleNamesByUserId(loginUser.getId());

            // 角色
            data.put("roles", roleList);
            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        //redisTemplate.delete(token);
    }

}
