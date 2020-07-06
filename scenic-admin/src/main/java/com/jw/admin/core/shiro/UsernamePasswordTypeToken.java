package com.jw.admin.core.shiro;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

@Data
public class UsernamePasswordTypeToken extends UsernamePasswordToken {
    private static final long serialVersionUID = -8770899129644873929L;

    /**
     * 类型
     */
    private String type;

    public UsernamePasswordTypeToken(String username, char[] password, String type) {
        super(username, password);
        this.type = type;
    }

}
