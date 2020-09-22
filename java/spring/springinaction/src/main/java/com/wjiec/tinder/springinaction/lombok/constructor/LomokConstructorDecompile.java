package com.wjiec.tinder.springinaction.lombok.constructor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LomokConstructorDecompile {

    @Data
    static class DataObject {
        private long id;
        private String name;
    }

    @Builder
    static class BuilderObject {
        private long id;
        private String name;
    }

    @NoArgsConstructor
    static class NoArgsConstructorObject {
        private long id;
        private String name;
    }

    @AllArgsConstructor
    static class AllArgsConstructorObject {
        private long id;
        private String name;
    }

//    @Data
//    @Builder
//    @NoArgsConstructor
//    static class MaybeWrong {
//        private long id;
//        private String name;
//    }

    public static void main(String[] args) {
        System.out.println(BuilderObject.builder().build());
        System.out.println(new NoArgsConstructorObject());
        System.out.println(new AllArgsConstructorObject(-1, "--"));
    }

}
