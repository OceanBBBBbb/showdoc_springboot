package com.ocean.showdoc.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author oceanBin on 2020/3/30
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Rsp<T> implements MessageSourceAware {

    private static MessageSourceAccessor messages = MessageSource.getAccessor();
    private static final Rsp<Void> INSTANCE = new Rsp<>(null);

    private int error_code;
    private String message;
    private String error_message;
    private T data;

    private Rsp(int code, String message, T data) {
        this.error_code = code;
        this.data = data;
        this.message = message;
    }

    private Rsp(T data) {
        this(RspCode.SUCCESS.getCode(), data);
    }

    public Rsp(int code, T data) {
        this.error_code = code;
        this.data = data;
    }

    public Rsp(int code, String msg) {
        this.error_code = code;
        this.error_message = msg;
    }

    public static Rsp<Void> buildSuccessEmpty() {
        return INSTANCE;
    }

    public static <T> Rsp<T> buildSuccess(T data) {
        return new Rsp<>(data);
    }

    public static <T> Rsp<T> buildFail(RspCode code,String msg) {
        return new Rsp<>(code.getCode(),msg);
    }

    private static <T> Rsp<T> buildException(T data, RspCode code, Object... args) {
        return new Rsp<>(code.getCode(), messages.getMessage(code.name(), args, code.getMessage()), data);
    }

    @Override
    public void setMessageSource(@Nullable org.springframework.context.MessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource cannot be null");
        messages = new MessageSourceAccessor(messageSource);
    }
}
