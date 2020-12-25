package com.wjiec.springaio.method.security.service;

import com.wjiec.springaio.method.security.model.Setting;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class SystemService {

    @Secured("ROLE_ADMIN")
    public void shutdown() {
        System.out.println("SystemService::shutdown");
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public void syncTimezone() {
        System.out.println("SystemService::stat");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #message.length() <= 10)")
    public void broadcast(String message) {
        System.out.println("SystemService::broadcast -> " + message);
    }

    @PostAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and returnObject.id < 10)")
    public Setting getSettingById(long id) {
        return Setting.builder()
            .id(id)
            .name("setting-" + id)
            .value("value-" + id)
            .build();
    }

    @PostFilter("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and filterObject.id < 10) or (filterObject.id == 0)")
    public List<Setting> stat() {
        return new ArrayList<>(List.of(
            Setting.builder().id(0L).name("opening").value("on").build(),
            Setting.builder().id(1L).name("customer").value("spring").build(),
            Setting.builder().id(10L).name("profit").value("123").build()
        ));
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PreFilter("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and filterObject.id < 10)")
    public void update(List<Setting> settings) {
        System.out.println("Update setting : " + settings);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PreFilter("hasPermission(filterObject, 'delete')")
    public void delete(List<Setting> settings) {
        System.out.println("Delete setting : " + settings);
    }

    public static class SettingPermissionEvaluator implements PermissionEvaluator {

        private static final GrantedAuthority ROLE_ADMIN_AUTHORITY = new SimpleGrantedAuthority("ROLE_ADMIN");

        @Override
        public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
            if (targetDomainObject instanceof Setting) {
                return isAdmin(authentication) || ((Setting) targetDomainObject).getId() < 10;
            }

            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasPermission(Authentication authentication, Serializable targetId,
                                     String targetType, Object permission) {
            throw new UnsupportedOperationException();
        }

        private boolean isAdmin(Authentication authentication) {
            return authentication.getAuthorities().contains(ROLE_ADMIN_AUTHORITY);
        }

    }

}
