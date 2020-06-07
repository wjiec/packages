package com.github.wjiec.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptEngineTest {

    public static void main(String[] args) throws ScriptException {
        System.out.println(new ScriptEngineManager().getEngineFactories());
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("Nashorn");
        System.out.println(engine);
        System.out.println(engine.eval("a = [1,2,4].map(function(e) { return e * e; })"));
        System.out.println(engine.get("a"));

        engine.put("console", System.out);
        engine.eval("console.println('hello')");
        engine.eval("console.println(JSON.stringify({a:1, b:2, c:3}))");
    }

}
