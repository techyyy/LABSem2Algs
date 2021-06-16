package task.lab7;

import java.util.ArrayList;

public class BinomialHeap<T extends Comparable<? super T>> extends BinomialTree<T>{
    private int n;
    private int t;
    private BinomialTree<T> minRoot;
    private BinomialTree<T> pointer;

    public BinomialHeap() {
        n = 0;
        minRoot = null;

        pointer = new BinomialTree<>(null);
        pointer.setRight(pointer);
        pointer.setLeft(pointer);
    }


    public BinomialTree<T> insert(T x) {
        BinomialTree<T> subTree = new BinomialTree<>(x);

        pointer.addRight(subTree);

        if (minRoot == null || x.compareTo(minRoot.getKey()) < 0)
            minRoot = subTree;

        this.n++;
        this.t++;

        return subTree;
    }


    public T findMin() {
        if (minRoot == null)
            return null;

        return this.minRoot.getKey();
    }


    public BinomialTree<T> deleteMin() {
        if (this.isEmpty())
            return null;

        BinomialTree<T> minTree = minRoot;
        this.removeRoot(minRoot);
        minRoot = null;

        if (this.isEmpty())
            return minTree;

        int logN = (int) Math.ceil(Math.log(this.n) / Math.log(2));
        ArrayList<BinomialTree<T>> subTrees = new ArrayList<>();

        while (!this.isEmpty()) {
            BinomialTree<T> tree = this.removeTree();
            int d = tree.getDegree();

            if (subTrees.get(d) == null) {
                subTrees.set(d, tree);
            } else {
                this.insertTree(mergeHeapTrees(tree, subTrees.get(d)));
                subTrees.set(d, null);
            }
        }

        for (int i = 0; i <= logN; i++) {
            if (subTrees.get(i) == null)
                continue;

            BinomialTree<T> tree = subTrees.get(i);

            tree.setLeft(pointer);
            tree.setRight(pointer.getRight());
            pointer.getRight().setLeft(tree);
            pointer.setRight(tree);

            if (minRoot == null || tree.getKey().compareTo(minRoot.getKey()) < 0)
                minRoot = tree;

            this.t++;
            this.n += Math.pow(2, tree.getDegree());
        }

        return minTree;
    }


    public void decreaseKey(BinomialTree<T> node, T key) {
        node.setKey(key);

        BinomialTree<T> parent = node.getParent();
        while (parent != null && key.compareTo(parent.getKey()) < 0) {


            BinomialTree<T> temp1p = parent.getRight();
            BinomialTree<T> temp1n = node.getRight();

            node.setRight(temp1p);
            temp1p.setLeft(node);
            parent.setRight(temp1n);
            temp1n.setLeft(parent);

            BinomialTree<T> temp2p = parent.getLeft();
            BinomialTree<T> temp2n = node.getLeft();

            node.setLeft(temp2p);
            temp2p.setRight(node);
            parent.setLeft(temp2n);
            temp2n.setRight(parent);

            BinomialTree<T> temp3n = node.getChild();
            BinomialTree<T> temp3p = parent.getParent();

            parent.setChild(temp3n);
            if (temp3n != null)
                temp3n.setParent(parent);
            node.setParent(temp3p);
            if (temp3p != null)
                temp3p.setChild(node);

            int temp4 = node.getDegree();
            node.setDegree(parent.getDegree());
            parent.setDegree(temp4);

            node.setChild(parent);
            parent.setParent(node);

            parent = node.getParent();
        }

        if (key.compareTo(minRoot.getKey()) < 0) minRoot = node;
    }


    public BinomialTree<T> mergeHeapTrees(BinomialTree<T> t1, BinomialTree<T> t2) {
        if (t1 == null && t2 == null) {
            return null;
        } else if (t1 == null) {
            return t2;
        } else if (t2 == null) {
            return t1;
        }

        if (t1.getKey().compareTo(t2.getKey()) < 0) {
            t1.addChild(t2);
            return t1;
        } else {
            t2.addChild(t1);
            return t2;
        }
    }


    public BinomialTree<T> removeTree() {
        if (this.isEmpty())
            return null;

        BinomialTree<T> right = pointer.getRight();

        this.removeTree(right);

        return right;
    }

    public void removeTree(BinomialTree<T> t) {
        if (this.isEmpty())
            return;

        t.remove();

        t.setRight(t);
        t.setLeft(t);

        this.t--;
        this.n -= Math.pow(2, t.getDegree());
    }

    public void removeRoot(BinomialTree<T> t) {

        if (t.getDegree() == 0) {
            this.removeTree(t);
            return;
        }

        this.removeTree(t);

        BinomialTree<T> t1 = this.pointer;
        BinomialTree<T> t2 = t.getChild();
        BinomialTree<T> t1Right = t1.getRight();
        BinomialTree<T> t2Left = t2.getLeft();

        t1.setRight(t2);
        t2.setLeft(t1);
        t1Right.setLeft(t2Left);
        t2Left.setRight(t1Right);

        this.n += Math.pow(2, t.getDegree()) - 1;
        this.t += t.getDegree();
    }

    public BinomialTree<T> insertTree(BinomialTree<T> t) {

        t.setRight(pointer.getRight());
        t.setLeft(pointer);
        pointer.getRight().setLeft(t);
        pointer.setRight(t);

        this.t++;
        this.n += Math.pow(2, t.getDegree());

        return t;
    }

    public void union(BinomialHeap<T> heap) {
        this.n += heap.n;
        this.t += heap.t;

        if (heap.isEmpty()) {
            return;
        } else if (this.isEmpty()) {
            this.pointer = heap.pointer;
            this.minRoot = heap.minRoot;
            return;
        }

        BinomialTree<T> h1 = this.pointer;
        BinomialTree<T> h2 = heap.pointer.getRight();
        BinomialTree<T> h1Right = h1.getRight();
        BinomialTree<T> h2Left = h2.getLeft();

        h1.setRight(h2);
        h2.setLeft(h1);
        h1Right.setLeft(h2Left);
        h2Left.setRight(h1Right);

        heap.pointer.remove();

        if (heap.findMin().compareTo(this.findMin()) < 0) {
            this.minRoot = heap.getMinRoot();
        }
    }

    public boolean isEmpty() {
        return (pointer.getRight() == pointer && pointer.getLeft() == pointer);
    }

    public BinomialTree<T> getPointer() {
        return this.pointer;
    }

    public BinomialTree<T> getMinRoot() {
        return this.minRoot;
    }

    public int getN() {
        return this.n;
    }
}