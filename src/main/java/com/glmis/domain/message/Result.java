package com.glmis.domain.message;

import java.io.Serializable;

/**
 * Created by kene213 on 2017/5/5.
 */
public class Result implements Serializable {
    //用于判断操作是否成功
    private boolean success = false;
    //提示信息
    private String msg = "";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
