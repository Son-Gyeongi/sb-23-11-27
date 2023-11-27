package com.ll.sb231127.global.rsData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RsData<T> {
    private final String resultCode;
    private final String msg;
    private final T data;
    private final int statusCode;

    // of의 경우 객체를 생성해주는 녀석이라고 이해하면 된다.
    // 이렇게 안하고 new로 해도 된다.
    public static <T> RsData of(String resultCode, String msg, T data) {
        // statusCode는 resultCode를 정수화 한거다.
        int statusCode = Integer.parseInt(resultCode);

        return new RsData<>(resultCode, msg, data, statusCode);
    }

    public boolean isSuccess() {
        // isSuccess 기준
        return statusCode >= 200 && statusCode < 400;
    }

    public boolean isFail() {
        return !isSuccess();
    }
}
