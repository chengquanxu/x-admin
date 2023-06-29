package com.cheng;

import com.cheng.common.utils.JwtUtil;
import com.cheng.sys.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author : Aaron
 * @date : 2023/5/6 14:10
 */
@SpringBootTest
public class JwtUtilTest {

    @Resource
    private JwtUtil jwtUtil;

    @Test
    public void testCreateJwt(){
        User user = new User();
        user.setUsername("zhangsan");
        user.setPhone("14523235858");
        String token = jwtUtil.createToken(user);
        System.out.println(token);
    }

    @Test
    public void testParseJwt(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5NDBhZjY2ZC02ODhlLTQzMzUtYjBkMC1lYjg3ZjFmOWExNjYiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiMTQ1MjMyMzU4NThcIixcInVzZXJuYW1lXCI6XCJ6aGFuZ3NhblwifSIsImlzcyI6InN5c3RlbSIsImlhdCI6MTY4MzM1NDA3MiwiZXhwIjoxNjgzMzU1ODcyfQ.PX334llfZ2XSUm7FrfcaRWTEphQKu3XZIGq25aQK1H0";
        Claims claims = jwtUtil.parseToken(token);
        System.out.println(claims);
    }

    @Test
    public void testParseJwt2(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5NDBhZjY2ZC02ODhlLTQzMzUtYjBkMC1lYjg3ZjFmOWExNjYiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiMTQ1MjMyMzU4NThcIixcInVzZXJuYW1lXCI6XCJ6aGFuZ3NhblwifSIsImlzcyI6InN5c3RlbSIsImlhdCI6MTY4MzM1NDA3MiwiZXhwIjoxNjgzMzU1ODcyfQ.PX334llfZ2XSUm7FrfcaRWTEphQKu3XZIGq25aQK1H0";
        User user = jwtUtil.parseToken(token, User.class);
        System.out.println(user);
    }
}
