/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.github.dunwu.modules.security.security;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import io.github.dunwu.data.redis.RedisHelper;
import io.github.dunwu.modules.security.config.DunwuWebSecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

/**
 * @author /
 */
@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private final DunwuWebSecurityProperties properties;
    private final RedisHelper redisHelper;
    public static final String AUTHORITIES_KEY = "user";
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;

    public TokenProvider(DunwuWebSecurityProperties properties, RedisHelper redisHelper) {
        this.properties = properties;
        this.redisHelper = redisHelper;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getJwt().getBase64Secret());
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build();
        jwtBuilder = Jwts.builder()
                         .signWith(key, SignatureAlgorithm.HS512);
    }

    /**
     * ??????Token ????????????????????? Token ????????????????????????Redis ??????
     *
     * @param authentication /
     * @return /
     */
    public String createToken(Authentication authentication) {
        return jwtBuilder
            // ??????ID??????????????? Token ????????????
            .setId(IdUtil.simpleUUID())
            .claim(AUTHORITIES_KEY, authentication.getName())
            .setSubject(authentication.getName())
            .compact();
    }

    /**
     * ??????Token ??????????????????
     *
     * @param token /
     * @return /
     */
    Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        User principal = new User(claims.getSubject(), "******", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }

    public Claims getClaims(String token) {
        return jwtParser
            .parseClaimsJws(token)
            .getBody();
    }

    /**
     * @param token ???????????????token
     */
    public void checkRenewal(String token) {
        // ??????????????????token,??????token???????????????
        long time = redisHelper.getExpire(properties.getJwt().getOnlineKey() + token) * 1000;
        Date expireDate = DateUtil.offset(new Date(), DateField.MILLISECOND, (int) time);
        // ?????????????????????????????????????????????
        long differ = expireDate.getTime() - System.currentTimeMillis();
        // ?????????????????????????????????????????????
        if (differ <= properties.getJwt().getDetect()) {
            long renew = time + properties.getJwt().getRenew();
            redisHelper.expire(properties.getJwt().getOnlineKey() + token, renew, TimeUnit.MILLISECONDS);
        }
    }

    public String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader(properties.getJwt().getTokenHeader());
        if (requestHeader != null && requestHeader.startsWith(properties.getJwt().getTokenStartWith())) {
            return requestHeader.substring(7);
        }
        return null;
    }

}
