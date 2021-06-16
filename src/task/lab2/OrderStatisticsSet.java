package task.lab2;

public interface OrderStatisticsSet<T> {
    void add(T data);
    T get(T value);
}