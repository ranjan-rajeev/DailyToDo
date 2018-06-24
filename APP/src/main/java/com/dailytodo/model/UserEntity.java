package com.dailytodo.model;

import java.util.List;

/**
 * Created by Rajeev Ranjan on 24-Jun-18.
 */

public class UserEntity {
    String userId;
    String name;
    String email;
    String mobile;
    String tokenId;


    public String getUserId() {
        return userId;
    }

    public UserEntity() {

    }

    public UserEntity(String userId, String name, String email, String mobile, String tokenId) {
        this.userId = userId;
        this.name = name;
        this.email = email;

        this.mobile = mobile;
        this.tokenId = tokenId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }


}
