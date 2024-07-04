package com.balabenute.demo_park_api.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstacionamentoUtils {

    // 2024-07-04T14:41:22.123344332
    //20240704-144122

    public static String gerarRecibo() {
        LocalDateTime date = LocalDateTime.now();
        String recibo = date.toString().substring(0, 19);
        return recibo.replace("-", "")
                .replace(":", "")
                .replace("T", "-");
    }
}
