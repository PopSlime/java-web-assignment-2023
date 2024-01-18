package cn.edu.scnu.java_web_assignment_2023.service;

import cn.edu.scnu.java_web_assignment_2023.entity.User;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

@Getter
public class AccountActionResult {
    private static final String SUCCESS = "success";
    public static final AccountActionResult DUPLICATE_ACCOUNT = new AccountActionResult("duplicate_account");
    public static final AccountActionResult MISSING_ACCOUNT = new AccountActionResult("missing_account");
    public static final AccountActionResult INCORRECT_PASSWORD = new AccountActionResult("incorrect_password");
    public static final AccountActionResult INTERNAL = new AccountActionResult("internal");

    @NonNull
    private final String localeKey;
    private final User user;

    public AccountActionResult(String localeKey) {
        this.localeKey = localeKey;
        user = null;
    }

    public AccountActionResult(User user) {
        this.localeKey = SUCCESS;
        this.user = user;
    }

    public boolean isSuccess() {
        return localeKey.equals(SUCCESS);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AccountActionResult)) return false;
        AccountActionResult other = (AccountActionResult) obj;
        return localeKey.equals(other.localeKey) && Objects.equals(user, other.user);
    }
}
