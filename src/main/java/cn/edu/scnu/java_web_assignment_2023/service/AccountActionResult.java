package cn.edu.scnu.java_web_assignment_2023.service;

import lombok.Getter;

@Getter
public enum AccountActionResult {
    SUCCESS("success"),
    MISSING_ACCOUNT("missing_account"),
    INCORRECT_PASSWORD("incorrect_password");

    private final String localeKey;

    AccountActionResult(String localeKey) {
        this.localeKey = localeKey;
    }
}
