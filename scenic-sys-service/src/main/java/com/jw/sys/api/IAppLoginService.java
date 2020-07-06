package com.jw.sys.api;

public interface IAppLoginService {
    String sendSms(String phone);

    boolean isSendCode(String phone, String sendCode);
}
