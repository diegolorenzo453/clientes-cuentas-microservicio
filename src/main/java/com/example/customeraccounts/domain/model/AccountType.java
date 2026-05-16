package com.example.customeraccounts.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum AccountType {
    NORMAL,
    PREMIUM,
    JUNIOR;

    @JsonCreator
    public static AccountType from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("accountType is required");
        }

        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported account type: " + value));
    }

    @JsonValue
    public String value() {
        return name();
    }
}
