package com.example.Ecom.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionType {
    PRODUCT_READ("product:read"),
    PRODUCT_WRITE("product:write"),
    PRODUCT_MANAGE("product:manage"),
    USER_MANAGE("user:manage"),
    REPORT_VIEW("report:view");

    private final String permission;
}
