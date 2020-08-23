package com.ing.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class DateTimeUtils {
    public LocalDateTime nowUTC() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}
