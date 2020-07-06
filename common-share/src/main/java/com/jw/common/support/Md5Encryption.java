package com.jw.common.support;

public class Md5Encryption {


    /**
     *
     * @param code  code
     * @param nonceStr  随机串
     * @param md5Sign   加密过的md5串 32位
     * @return
     */
    public static Boolean start(String code,String nonceStr,String md5Sign){
        if (null == md5Sign) {
            return false;
        }
        if (null == code || null == nonceStr) {
            return false;
        }
        String md5str = MD5.md5(code + nonceStr);
        if (!md5str.equals(md5Sign)) {
            return false;
        }
        return true;
    }

}
