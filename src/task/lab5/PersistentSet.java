package task.lab5;

public interface PersistentSet<T> {
    void add(T data);
    boolean contains(T value);
}
