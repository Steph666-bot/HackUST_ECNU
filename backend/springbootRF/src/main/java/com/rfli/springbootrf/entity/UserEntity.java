package com.rfli.springbootrf.entity;

import lombok.Data;

/**
 * User Entity Class
 */
@Data
public class UserEntity {
    private Integer id;
    private String username;
    private String password;

    private String pk;

    private String sk;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
