package com.eelve.limiting.sentinel.enums;

public enum RulesEnum {

    Flow(1),

    Degrade(2),

    System(3),

    Authority(4);



    private int code;

    RulesEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
