package main.innerclass;

import main.reflection.Analyzer;

public class InnerExtend extends InnerClock {

    {
        content = "Content from InnerExtend";
    }

    private String analysis() {
        InnerExtend.WhiteMouse whiteMouse = this.new WhiteMouse();
        System.out.println(whiteMouse.method());
        return new Analyzer().analysis(whiteMouse);
    }

    public static void main(String[] args) throws InterruptedException {
        new InnerExtend().start();
        Thread.sleep(2000);

        System.out.println(new InnerExtend().analysis());
    }

    private class WhiteMouse {

        String method() {
            return InnerExtend.this.content;
        }

    }

}
