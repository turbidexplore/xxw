package com.bangxuan.xxw.util;

import lombok.Data;
@Data
public class Message {

    public final static String SECUESS="SECUESS";

    public final static String ERROR="ERROR";

    private Integer status;

    private String message;

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
