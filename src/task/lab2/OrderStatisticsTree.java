package task.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderStatisticsTree<T extends Comparable<? super T>> implements OrderStatisticsSet<T> {
    private Node root;
    private Node tnull;

    private class Node {
        T data;
        Node parent;
        Node left;
        Node right;
        int color;
    }

    public List<T> preOrderTraversal() {
        List<T> res = new ArrayList<>();
        return preOrderTraversal(this.root, res);
    }
    private List<T> preOrderTraversal(Node node, List<T> res) {
        if (node != tnull) {
            res.add(node.data);
            preOrderTraversal(node.left, res);
            preOrderTraversal(node.right, res);
        }
        return res;
    }

    public List<T> inOrderTraversal() {
        List<T> res = new ArrayList<>();
        return inOrderTraversal(this.root, res);

    }
    private List<T> inOrderTraversal(Node node, List<T> res) {
        if (node != tnull) {
            inOrderTraversal(node.left, res);
            res.add(node.data);
            inOrderTraversal(node.right, res);
        }
        return res;
    }

    public List<T> postOrderTraversal() {
        List<T> res = new ArrayList<>();
        return postOrderTraversal(this.root, res);
    }
    private List<T> postOrderTraversal(Node node, List<T> res) {
        if (node != tnull) {
            postOrderTraversal(node.left, res);
            postOrderTraversal(node.right, res);
            res.add(node.data);
        }
        return res;
    }

    @Override
    public void add(T key) {

        Node temp = new Node();
        temp.parent = null;
        temp.data = key;
        temp.left = tnull;
        temp.right = tnull;
        temp.color = 1;

        Node y = null;
        Node x = this.root;

        while (x != tnull) {
            y = x;
            if (temp.data.compareTo(x.data) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        temp.parent = y;
        if (y == null) {
            root = temp;
        } else if (temp.data.compareTo(y.data) < 0) {
            y.left = temp;
        } else {
            y.right = temp;
        }
        if (temp.parent == null){
            temp.color = 0;
            return;
        }
        if (temp.parent.parent == null) {
            return;
        }
        fixInsert(temp);
    }

    private void fixInsert(Node k){
        Node u;
        while (k.parent.color == 1) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.color == 1) {
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;

                if (u.color == 1) {
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 0;
    }

    @Override
    public T get(T k) { return get(this.root, k).data; }
    private Node get(Node node, T key) {
        if (node == tnull || key.equals(node.data)) {
            return node;
        }

        if (key.compareTo(node.data) < 0) {
            return get(node.left, key);
        }
        return get(node.right, key);
    }

    public void delete(T data) { delete(this.root, data); }
    private void delete(Node node, T key) {
        Node z = tnull;
        Node x;
        Node y;
        while (node != tnull){
            if (node.data.equals(key)) {
                z = node;
            }

            if (node.data.compareTo(key) < 0 || node.data.equals(key)) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        if (z == tnull) {
            return;
        }

        y = z;
        int yOriginalColor = y.color;
        if (z.left == tnull) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == tnull) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == 0){
            fixDelete(x);
        }
    }

    private void fixDelete(Node x) {
        Node s;
        while (x != root && x.color == 0) {
            if (x == x.parent.left) {
                s = x.parent.right;
                if (s.color == 1) {
                    s.color = 0;
                    x.parent.color = 1;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }

                if (s.left.color == 0 && s.right.color == 0) {
                    s.color = 1;
                    x = x.parent;
                } else {
                    if (s.right.color == 0) {
                        s.left.color = 0;
                        s.color = 1;
                        rightRotate(s);
                        s = x.parent.right;
                    }
                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.right.color = 0;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.left;
                if (s.color == 1) {
                    s.color = 0;
                    x.parent.color = 1;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }

                if (s.right.color == 0) {
                    s.color = 1;
                    x = x.parent;
                } else {
                    if (s.left.color == 0) {
                        s.right.color = 0;
                        s.color = 1;
                        leftRotate(s);
                        s = x.parent.left;
                    }

                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.left.color = 0;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = 0;
    }

    private void rbTransplant(Node u, Node v){
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left){
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    public OrderStatisticsTree() {
        tnull = new Node();
        tnull.color = 0;
        tnull.left = null;
        tnull.right = null;
        root = tnull;
    }


    private Node minimum(Node node) {
        while (node.left != tnull) {
            node = node.left;
        }
        return node;
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != tnull) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != tnull) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public Node getRoot(){
        return this.root;
    }

    public T kthSmallest(int k) {
        List<T> res = inOrderTraversal();
        if(k > res.size()) throw new NoSuchElementException();
        return res.get(k-1);
    }

    public static void main(String [] args){
        OrderStatisticsTree<Integer> tree = new OrderStatisticsTree<>();
        tree.add(5);
        tree.add(4);
        tree.add(3);
        tree.add(2);
        System.out.println(tree.kthSmallest(2));
    }
}