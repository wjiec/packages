package com.wjiec.springaio.method.security;

import com.wjiec.springaio.method.security.service.SystemService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@ComponentScan
public class Application {

    private static SystemService systemService;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Application.class);
        context.refresh();

        systemService = context.getBean(SystemService.class);
        System.out.println(systemService);

        System.out.println("------------ systemService.shutdown() ------------");
        wrap("admin", "admin", () -> systemService.shutdown());
        wrap("user", "user", () -> systemService.shutdown());
        wrap("guest", "guest", () -> systemService.shutdown());

        System.out.println("------------ systemService.syncTimezone() ------------");
        wrap("admin", "admin", () -> systemService.syncTimezone());
        wrap("user", "user", () -> systemService.syncTimezone());
        wrap("guest", "guest", () -> systemService.syncTimezone());

        System.out.println("------------ systemService.broadcast() ------------");
        wrap("admin", "admin", () -> systemService.broadcast("hello from admin"));
        wrap("user", "user", () -> systemService.broadcast("hello from user"));
        wrap("user", "user", () -> systemService.broadcast("from user"));
        wrap("guest", "guest", () -> systemService.broadcast("hello from guest"));

        System.out.println("------------ systemService.getSettingById() ------------");
        wrap("admin", "admin", () -> System.out.println(systemService.getSettingById(1)));
        wrap("admin", "admin", () -> System.out.println(systemService.getSettingById(11)));
        wrap("user", "user", () -> System.out.println(systemService.getSettingById(1)));
        wrap("user", "user", () -> System.out.println(systemService.getSettingById(11)));
        wrap("guest", "guest", () -> System.out.println(systemService.getSettingById(1)));
        wrap("guest", "guest", () -> System.out.println(systemService.getSettingById(11)));

        System.out.println("------------ systemService.stat() ------------");
        wrap("admin", "admin", () -> System.out.println(systemService.stat()));
        wrap("admin", "admin", () -> System.out.println(systemService.stat()));
        wrap("user", "user", () -> System.out.println(systemService.stat()));
        wrap("user", "user", () -> System.out.println(systemService.stat()));
        wrap("guest", "guest", () -> System.out.println(systemService.stat()));
        wrap("guest", "guest", () -> System.out.println(systemService.stat()));

        System.out.println("------------ systemService.update() ------------");
        wrap("admin", "admin", () -> systemService.update(List.of()));
        wrap("user", "user", () -> systemService.update(List.of()));
        wrap("guest", "guest", () -> systemService.update(List.of()));

        System.out.println("------------ systemService.delete() ------------");
        wrap("admin", "admin", () -> systemService.delete(List.of()));
        wrap("user", "user", () -> systemService.delete(List.of()));
        wrap("guest", "guest", () -> systemService.delete(List.of()));
    }

    private static void wrap(String username, String password, Action action) {
        System.out.printf("\nDoing %s by %s -> %s\n\n", action, username, password);
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            action.action();
        } catch (AuthenticationException ex) {
            System.out.println("\tAuthenticationException : " + ex.getMessage());
        } catch (AccessDeniedException ex) {
            System.out.println("\tAccessDeniedException : " + ex.getMessage());
        }

        System.out.println();
    }

    @FunctionalInterface
    private interface Action {
        void action();
    }

}
