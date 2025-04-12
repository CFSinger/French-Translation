package com.mfwchris.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum lang {
    FRENCH("French","fr");

    private final String name;
    @Getter
    private final String code;

    public String toString(){
        return name;
    }

}
