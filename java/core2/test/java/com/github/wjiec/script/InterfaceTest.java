package com.github.wjiec.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class InterfaceTest {

    public static void main(String[] args) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("Nashorn");

        engine.put("console", System.out);
        engine.eval("function sayHello(name) { console.println('Hello' + ' ' + name); }");

        HumanInterface person = ((Invocable)engine).getInterface(HumanInterface.class);
        person.sayHello("jayson");
    }

}
