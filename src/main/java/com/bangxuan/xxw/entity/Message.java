package com.bangxuan.xxw.entity;

import lombok.Data;

/**
 *消息返回
 */
@Data
public class Message {

    public final static String SECUESS="SECUESS";

    public final static String ERROR="ERROR";

    //状态
    private Integer status;

    //消息
    private String message;

    //数据
    private Object data;

    public static Message SCUESSS(String msg,Object data){
        Message message=new Message();
        message.setStatus(200);
        message.setMessage(msg);
        message.setData(data);
        return message;
    }

    public static Message ERROR(String msg){
        Message message=new Message();
        message.setStatus(500);
        message.setMessage(msg);
        message.setData(null);
        return message;
    }


}
