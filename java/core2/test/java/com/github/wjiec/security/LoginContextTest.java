package com.github.wjiec.security;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class LoginContextTest {

    public static void main(String[] args) {
        try {
            LoginContext ctx = new LoginContext("Login1");

            ctx.login();
            System.out.println("Authorization successful");

            Subject subject = ctx.getSubject();
            System.out.println(subject);

            var action = new SysPropAction("user.home");
            System.out.println(Subject.doAsPrivileged(subject, action, null));

            ctx.logout();
            System.out.println(subject);
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

}
