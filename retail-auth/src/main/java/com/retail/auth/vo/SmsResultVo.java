package com.retail.auth.vo;

import lombok.Data;

/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.auth.vo
 * @Classname: SmsResultVo
 * @CreateTime: 2023-03-24  16:29
 * @Created by: 喵喵
 * @Description:
 * @Version:
 */
public class SmsResultVo {


    /**
     * msg : 成功
     * smsid : 16565614329364584123421
     * code : 0
     * balance : 1234
     */

    private String msg;
    private String smsid;
    private Integer code;
    private Integer balance;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSmsid() {
        return smsid;
    }

    public void setSmsid(String smsid) {
        this.smsid = smsid;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
