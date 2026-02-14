package com.example.Ecom.security;

import com.example.Ecom.model.type.PermissionType;
import com.example.Ecom.model.type.RoleType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.Ecom.model.type.RoleType.*;

public class RolePermissionMapping {

    private static final Map<RoleType, Set<PermissionType>> map = Map.of(
            CUSTOMER,Set.of(PermissionType.PRODUCT_READ),
            VENDOR,Set.of(PermissionType.PRODUCT_WRITE,PermissionType.PRODUCT_MANAGE,PermissionType.PRODUCT_READ),
            ADMIN,Set.of(PermissionType.PRODUCT_WRITE,PermissionType.PRODUCT_READ,PermissionType.PRODUCT_MANAGE,PermissionType.USER_MANAGE));

    public static  Set<SimpleGrantedAuthority> getAuthoritiesForRole(RoleType roleType){
        return map.get(roleType).stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
    }


}
