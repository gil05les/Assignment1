/*
 * map object
 * Please do not make changes to this file
*/

package io.grpc.filesystem.task2;

public class Mapper<W, C> {
    public final W word;
    public final C value;

    public Mapper(W word, C value) {
        this.word = word;
        this.value = value;
    }

    public W getWord() {
        return word;
    }

    public C getValue() {
        return value;
    }

}