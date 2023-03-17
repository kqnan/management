package com.example.management.service.impl;

import com.example.management.domain.LoginStaff;
import com.example.management.pojo.Staff;
import com.example.management.service.LoginService;
import com.example.management.utils.CommonResult;
import com.example.management.utils.JwtUtil;
import com.example.management.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public CommonResult login(Staff staff) {
        System.out.println("nihao");
        System.out.println(staff);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(staff.getStaffAccount(),staff.getStaffPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        System.out.println("hi");
        if(Objects.isNull(authenticate)){
            return CommonResult.error(400,"用户名不存在");
        }
        LoginStaff loginStaff = (LoginStaff)authenticate.getPrincipal();
        String staffAccount = loginStaff.getStaff().getStaffAccount().toString();
        String jwt = JwtUtil.createJWT(staffAccount);
        Map<String,String> map = new HashMap<>();
        System.out.println("hello");
        map.put("token",jwt);
        redisCache.setCacheObject("login" + staffAccount,loginStaff);
        return CommonResult.success(map);
    }

    @Override
    public CommonResult logout() {
        //从SecurityContextHolder中的userid
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        LoginStaff loginUser = (LoginStaff) authentication.getPrincipal();
        String staffAccount = loginUser.getStaff().getStaffAccount();

        //根据userid找到redis对应值进行删除
        redisCache.deleteObject("login"+ staffAccount);
        return CommonResult.success();
    }

}