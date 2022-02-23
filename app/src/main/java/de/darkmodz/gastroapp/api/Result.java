package de.darkmodz.gastroapp.api;

public abstract class Result<T> {
    private Result() {}

    public static final class Feedback<T> extends Result<T> {
        public T data;

        public Feedback(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }
}
