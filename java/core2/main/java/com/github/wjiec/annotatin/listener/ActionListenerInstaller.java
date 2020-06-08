package com.github.wjiec.annotatin.listener;

import java.lang.reflect.*;

public class ActionListenerInstaller {

    public static void install(Object obj) {
        try {
            Class<?> cl = obj.getClass();
            for (Method m : cl.getMethods()) {
                ActionListener listener = m.getAnnotation(ActionListener.class);
                if (listener != null) {
                    Field field = cl.getDeclaredField(listener.source());
                    field.setAccessible(true);

                    addListener(field.get(obj), obj, m);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void addListener(Object source, final Object parameter, final Method m) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var handler = new InvocationHandler()  {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                return m.invoke(parameter);
            }
        };

        Object proxy = Proxy.newProxyInstance(null, new Class[] {java.awt.event.ActionListener.class}, handler);
        Method add = source.getClass().getMethod("addActionListener", java.awt.event.ActionListener.class);
        add.invoke(source, proxy);
    }

}
