package com.leaf.filter;

import com.alibaba.fastjson.JSON;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.dto.LoginUser;
import com.leaf.enums.AppHttpCodeEnum;
import com.leaf.utils.JwtUtil;
import com.leaf.utils.RedisCache;
import com.leaf.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 登录拦截器
 * 把前端请求传入的token给到security中
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    //token头
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${redis.redisLogin}")
    private String blogLogin;

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader(this.tokenHeader);
        if(!StringUtils.hasText(token)){
            //说明该接口不需要登录直接放行
            filterChain.doFilter(request,response);
            return;
        }
        //解析获取token
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            //token超时或token不合法
            //响应告诉前端需要重新登录
            e.printStackTrace();
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(responseResult));
            return;
        }
        String username = claims.getSubject();
        //从redis中获取用户信息
        LoginUser cacheObject = redisCache.getCacheObject(this.blogLogin + username);
        //如果获取不到
        if (Objects.isNull(cacheObject)){
            //说明登录过期，需要重新登录
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(responseResult));
            return;
        }
        //存入SecurityContextHolder
        //TODO 权限信息
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(cacheObject,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request,response);
    }
}
