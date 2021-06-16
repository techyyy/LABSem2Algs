package task.lab7;

public interface BinomialSet<T extends Comparable<? super T>> {
    void addRight(BinomialTree<T> tree);
    void addChild(BinomialTree<T> tree);
}
