package com.leaf.service.impl;

import com.leaf.domain.ResponseResult;
import com.leaf.domain.dto.LoginUser;
import com.leaf.domain.entity.User;
import com.leaf.domain.vo.AdminUserInfoVo;
import com.leaf.domain.vo.UserInfoVo;
import com.leaf.service.AdminLoginService;
import com.leaf.service.MenuService;
import com.leaf.service.RoleService;
import com.leaf.utils.BeanCopyUtils;
import com.leaf.utils.JwtUtil;
import com.leaf.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisCache redisCache;
    @Value("${redis.redisLogin}")
    private String blogLogin;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //获取username生成token
        LoginUser principal = (LoginUser) authenticate.getPrincipal();
        String userName = principal.getUser().getUserName();
        String jwt = JwtUtil.createJWT(userName);
        //写入redis,60s*60一小时过期
        redisCache.setCacheObject(this.blogLogin + userName, principal, 60 * 60, TimeUnit.SECONDS);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);
        //返回
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取token，并解析
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        //获取username
        String userName = principal.getUser().getUserName();
        //删除redis里面的角色
        redisCache.deleteObject(this.blogLogin + userName);
        return ResponseResult.okResult();
    }

    /**
     * 获取用户权限和角色
     * @return
     */
    @Override
    public ResponseResult<AdminUserInfoVo> getInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        //获取用户信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取权限信息
        List<String> listMenu = menuService.getMenuByid(loginUser.getUser().getId());
        //返回角色信息
        List<String> roles=roleService.selectRoleKeyById(loginUser.getUser().getId());
        //封装返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(listMenu,roles,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }
}

