package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {

    SUCCESS(200,"SUCCESS"),
    ERROR(500,"SERVER EXCEPTION"),

    CREATE_JOB_ERROR(500210,"CREATE JOB FAILED"),
    JOB_NOT_EXIST(500211,"JOB NOT EXIST");
    private final Integer code;
    private final String message;
}
