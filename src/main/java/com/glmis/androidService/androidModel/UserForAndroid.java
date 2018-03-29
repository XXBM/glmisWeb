package com.glmis.androidService.androidModel;

/**
 * Created by 杨云召 on 26/05/2017.
 * 为android创建的User实体类
 */
public class UserForAndroid {
    private long id;
    private String username;//用户名
    private String password;//密码

    public UserForAndroid() {
    }

    public UserForAndroid(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}
