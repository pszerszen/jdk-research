package com.perunit.jdk.reserach.jdk16;

record Range(int lo, int hi) {

    public Range {
        if (lo > hi) {
            throw new IllegalArgumentException(String.format("(%d,%d)", lo, hi));
        }
    }

}
