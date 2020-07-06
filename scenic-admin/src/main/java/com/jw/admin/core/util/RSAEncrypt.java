package com.jw.admin.core.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RSAEncrypt {
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(512,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0,publicKeyString);  //0表示公钥
        keyMap.put(1,privateKeyString);  //1表示私钥
    }
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }


    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
        genKeyPair();
        //加密字符串
        Map<String,String> map = new HashMap<>();
       /* map.put("tNo","OD826765456778990998");
        map.put("source","metro");*/

        Date dd = new Date();
        long c = dd.getTime();

        map.put("id","99999900000");
        map.put("t","0");
        map.put("d",String.valueOf(c));
        String message = JSON.toJSONString(map);
        System.out.println(message);
        System.out.println("随机生成的公钥为:" + keyMap.get(0));
        System.out.println("随机生成的私钥为:" + keyMap.get(1));
        String messageEn = encrypt(message,keyMap.get(0));
        System.out.println("加密后的字符串为:" + messageEn);
        System.out.println("加密后的字符串长度:" + messageEn.length());
        String messageDe = decrypt(messageEn,keyMap.get(1));
        /*String  Gyao = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFk4ibiEt0vST1gDsHmTKc0Gqn3PVQJ+LOrB2WvO9fnTqjBmzTRv0azjYCDyIyiWgplrhYOpWfrq2DecI6o9P8qLOkxkKMPZS16EZ61MNBwle+AnWjCXo0xMC48lsBPPNyBcucrlHdrIjodQwBPlHf0D9+7jKcBNbllnHawNupzQIDAQAB";

        String Syao = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIWTiJuIS3S9JPWAOweZMpzQaqfc9VAn4s6sHZa871+dOqMGbNNG/RrONgIPIjKJaCmWuFg6lZ+urYN5wjqj0/yos6TGQow9lLXoRnrUw0HCV74CdaMJejTEwLjyWwE883IFy5yuUd2siOh1DAE+Ud/QP37uMpwE1uWWcdrA26nNAgMBAAECgYATL3aOiEv2L3iQfmN8GufnGBRJmfXuFlyAPL5fOTHUdNvIMJMO4YJXCEk/kHbj9VJtrsU5fuigr9EiMinQjFlVGnDbk5xEE0JLR1cWyu4W1vi4Q82QQO/yOz7MRy/LF36AKUnE12/1EmcRnb8sXeO454qyt02vJuVqaAOD40H/QQJBAMwY6p3PhE4bkyzOMU2ghiv7Qe/ed8cXVe4P3Gn2souJp5xoksy1aBPvp37X+Ergzp79C6AReIXwgZowHjzYA/ECQQCni5QLBu8JU7LiIhkeIir7dl6F6HUjbmWsX1bqyVycGjIy8QcTD0vJDxF0X4vRhnnyJcBMWc26xWSgXLfKKC+dAkEAx4D7pfM5gxhVTxDnBNiLDub2V0EBmG1pI+IBNNtfQCL44l0eUlhpOkOjWMxH+vmxP5xfBsLNKzAjn9K1XAhUIQJAC30ow7DQB2Wz6InKCyTwqZmuVaRhE/LoCe/nt+Xi8dliA9g/UfFEkx7zZXV/0vhTuGUaUKH/14Tdp5l0COrEiQJAHF65X6Y62L/zpjuyHQh2PQwgnY26OJOp432RBOa8wZ1KVwJ6sFD+Y1NWG6qs0Gj8vK60LFpvd6qTmphkUr1Yow==";

         String messageEn = encrypt(message,Gyao);
        System.out.println("加密后的字符串为:" + messageEn);
        System.out.println("加密后的字符串长度:" + messageEn.length());
        String messageDe = decrypt(messageEn,Syao);*/


        System.out.println("还原后的字符串为:" + messageDe);
    }
}
