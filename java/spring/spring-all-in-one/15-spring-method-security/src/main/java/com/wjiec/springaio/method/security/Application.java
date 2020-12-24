package com.wjiec.springaio.method.security;

import com.wjiec.springaio.method.security.service.SystemService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

@ComponentScan
public class Application {

    private static SystemService systemService;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Application.class);
        context.refresh();

        systemService = context.getBean(SystemService.class);
        System.out.println(systemService);

        wrap("admin", "admin", () -> systemService.shutdown());
        wrap("user", "user", () -> systemService.shutdown());
    }

    private static void wrap(String username, String password, Action action) {
        System.out.printf("\nDoing by %s -> %s\n\n", username, password);

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
