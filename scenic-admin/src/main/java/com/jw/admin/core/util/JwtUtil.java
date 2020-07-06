package com.jw.admin.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
public class JwtUtil {
    public enum UserType {
        STAFF("员工"), USER("客户"), CHANNEL("渠道商"), CLIENTUSER("客户端用户");

        UserType(String val) {
            this.val = val;
        }

        private String val;

        public String getVal() {
            return val;
        }
    }

    private static String profiles = "";

    public static String getProfiles() {
        return profiles;
    }

    /**
     * 由字符串生成加密key
     */
    private static SecretKey generalKey() {
        String stringKey = getProfiles() + CommonConstant.JWT_SECRET_KEY;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     */
    public static String createJWT(UserType userType, String id, String subject, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .claim("userType", userType)
                .signWith(SignatureAlgorithm.HS256, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     */
    public static Claims parseJWT(String jwt) {
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    /**
     * 生成subject信息
     */
    public static String generalSubject(Object obj) {
        JSONObject jo = new JSONObject();
        jo.put("id", ReflectUtil.getValueByFieldName(obj, "id"));
        jo.put("account", ReflectUtil.getValueByFieldName(obj, "account"));
        jo.put("name", ReflectUtil.getValueByFieldName(obj, "name"));
        jo.put("avatar", ReflectUtil.getValueByFieldName(obj, "avatar"));
        jo.put("appId", ReflectUtil.getValueByFieldName(obj, "appId"));
        jo.put("appName", ReflectUtil.getValueByFieldName(obj, "appName"));
        jo.put("appUserId", ReflectUtil.getValueByFieldName(obj, "appUserId"));
        jo.put("appStaffId", ReflectUtil.getValueByFieldName(obj, "appStaffId"));
        jo.put("channelName", ReflectUtil.getValueByFieldName(obj, "channelName"));
        jo.put("authorizationInfoPerms", ReflectUtil.getValueByFieldName(obj, "authorizationInfoPerms"));
        jo.put("authorizationInfoRoles", ReflectUtil.getValueByFieldName(obj, "authorizationInfoRoles"));
        return jo.toJSONString();
    }

    /**
     * 生成客户端subject信息
     */
    public static String generalClientSubject(Object obj) {
        JSONObject jo = new JSONObject();
        jo.put("userId", ReflectUtil.getValueByFieldName(obj, "userId"));
        jo.put("openId", ReflectUtil.getValueByFieldName(obj, "openId"));
        jo.put("guid", ReflectUtil.getValueByFieldName(obj, "guid"));
        jo.put("trueName", ReflectUtil.getValueByFieldName(obj, "trueName"));
        jo.put("mobile", ReflectUtil.getValueByFieldName(obj, "mobile"));
        jo.put("certNo", ReflectUtil.getValueByFieldName(obj, "certNo"));
        jo.put("headSrc", ReflectUtil.getValueByFieldName(obj, "headSrc"));
        jo.put("bindMobile", ReflectUtil.getValueByFieldName(obj, "status"));
        jo.put("status", ReflectUtil.getValueByFieldName(obj, "channelName"));
        return jo.toJSONString();
    }


    public static JSONObject getSubject() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final String authorizationToken = request.getHeader(CommonConstant.JWT_HEADER_TOKEN_KEY);
        if (ValidatorUtil.isEmpty(authorizationToken)) {
            throw new SignatureException("token头缺失");
        }
        final Claims claims = parseJWT(authorizationToken);
        return JSONObject.parseObject(claims.getSubject());
    }

    public static <T> T getSubject(Class<T> obj) {
        return JSONObject.toJavaObject(getSubject(), obj);
    }

    public static <T> T getSubject(Object obj, Class<T> tClass) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorizationToken = request.getHeader(CommonConstant.JWT_HEADER_TOKEN_KEY);
        if (ValidatorUtil.isEmpty(authorizationToken)) {
            authorizationToken = "" + ReflectUtil.getValueByFieldName(obj, CommonConstant.JWT_HEADER_TOKEN_KEY);
        }
        if (ValidatorUtil.isEmpty(authorizationToken)) {
            throw new SignatureException("token头缺失");
        }
        final Claims claims = parseJWT(authorizationToken);
        return JSONObject.toJavaObject(JSONObject.parseObject(claims.getSubject()), tClass);
    }

    public static boolean isPermitted(String authStr) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final String authorizationToken = request.getHeader(CommonConstant.JWT_HEADER_TOKEN_KEY);
        if (ValidatorUtil.isEmpty(authorizationToken)) {
            throw new SignatureException("token头缺失");
        }
        final Claims claims = parseJWT(authorizationToken);
        JSONObject jobj = JSON.parseObject(claims.getSubject());
        JSONArray authorizationInfoPerms = jobj.getJSONArray("authorizationInfoPerms");
        return authorizationInfoPerms != null && authorizationInfoPerms.contains(authStr);
    }

    public static boolean isPermitted(Object obj, String authStr) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorizationToken = request.getHeader(CommonConstant.JWT_HEADER_TOKEN_KEY);
        if (ValidatorUtil.isEmpty(authorizationToken)) {
            authorizationToken = "" + ReflectUtil.getValueByFieldName(obj, CommonConstant.JWT_HEADER_TOKEN_KEY);
        }
        if (ValidatorUtil.isEmpty(authorizationToken)) {
            throw new SignatureException("token头缺失");
        }
        final Claims claims = parseJWT(authorizationToken);
        JSONObject jobj = JSON.parseObject(claims.getSubject());
        JSONArray authorizationInfoPerms = jobj.getJSONArray("authorizationInfoPerms");
        return authorizationInfoPerms != null && authorizationInfoPerms.contains(authStr);
    }

}
