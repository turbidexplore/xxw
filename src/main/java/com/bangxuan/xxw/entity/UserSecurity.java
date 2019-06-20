package com.bangxuan.xxw.entity;

import com.bangxuan.xxw.entity.values.ThirdPartyTypeOfAuth;
import com.bangxuan.xxw.entity.values.UserStatus;
import com.bangxuan.xxw.entity.values.UserType;
import lombok.Data;

import java.util.UUID;

/**
 * 用户安全信息
 */
@Data
public class UserSecurity {

    public UserSecurity(){
        if(this.id==null) {
            this.id = UUID.randomUUID().toString().replace("-", "");
            this.user_code = UUID.randomUUID().toString().replace("-", "");
        }
    }

    private String id;

    private String user_code;

    private String password;

    private String saltvalue;

    private String phonenumber;

    private String email;

    private String authcode;

    private String sendauthcode_time;

    private ThirdPartyTypeOfAuth thirdparty_type;

    private String thirdparty_openid;

    private UserType type;

    private UserStatus status;

    private String register_ip;

    private String create_time;
}
