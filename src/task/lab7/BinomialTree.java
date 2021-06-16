package task.lab7;

public class BinomialTree<T extends Comparable<? super T>> implements BinomialSet<T> {
    private T key;
    private BinomialTree<T> parent = null;
    private BinomialTree<T> child = null;
    private BinomialTree<T> left = null;
    private BinomialTree<T> right = null;
    private int degree;

    public BinomialTree(){
    }


    public BinomialTree(T key) {
        this.key = key;
        this.degree = 0;

        this.left = this;
        this.right = this;
    }

    public void addRight(BinomialTree<T> tree) {
        tree.setLeft(this);
        tree.setRight(this.right);
        this.right.setLeft(tree);
        this.setRight(tree);
    }


    public void addChild(BinomialTree<T> tree) {
        if (this.child == null) {
            this.child = tree;
        } else {
            this.child.addRight(tree);
        }

        tree.setParent(this);

        this.degree++;
    }

    public boolean remove() {
        if (this.right == this && this.left == this) {
            return false;
        } else {
            this.left.setRight(this.right);
            this.right.setLeft(this.left);

            return true;
        }
    }

    public String toString() {
        return "key: " + key + ", degree: " + degree;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public void setParent(BinomialTree<T> tree) {
        this.parent = tree;
    }

    public void setChild(BinomialTree<T> tree) {
        this.child = tree;
    }

    public void setRight(BinomialTree<T> tree) {
        this.right = tree;
    }

    public void setLeft(BinomialTree<T> tree) {
        this.left = tree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public BinomialTree<T> getParent() {
        return this.parent;
    }

    public BinomialTree<T> getChild() {
        return this.child;
    }

    public BinomialTree<T> getLeft() {
        return this.left;
    }

    public BinomialTree<T> getRight() {
        return this.right;
    }

    public T getKey() {
        return this.key;
    }

    public int getDegree() {
        return this.degree;
    }

}
