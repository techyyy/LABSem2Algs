package task.lab3;

import java.util.ArrayList;
import java.util.List;

class SplayTree<T extends Comparable<? super T>> implements SplaySet<T>{
    private SplayNode root;
    private int count = 0;

    private class SplayNode {
        SplayNode left;
        SplayNode right;
        SplayNode parent;
        T element;

        public SplayNode()
        {
            this(null, null, null, null);
        }

        public SplayNode(T element)
        {
            this(element, null, null, null);
        }

        public SplayNode(T element, SplayNode left, SplayNode right, SplayNode parent)
        {
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.element = element;
        }
    }

    public SplayTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
        count = 0;
    }

    @Override
    public void add(T data) {
        SplayNode z = root;
        SplayNode p = null;
        while (z != null) {
            p = z;
            if (data.compareTo(p.element) > 0) {
                z = z.right;
            } else {
                z = z.left;
            }
        }
        z = new SplayNode();
        z.element = data;
        z.parent = p;
        if (p == null) {
            root = z;
        } else if (data.compareTo(p.element) > 0) {
            p.right = z;
        } else {
            p.left = z;
        }
        splay(z);
        count++;
    }

    private void makeLeftChildParent(SplayNode c, SplayNode p) {
        if ((c == null) || (p == null) || (p.left != c) || (c.parent != p))
            throw new IllegalArgumentException();

        if (p.parent != null)
        {
            if (p == p.parent.left)
                p.parent.left = c;
            else
                p.parent.right = c;
        }
        if (c.right != null)
            c.right.parent = p;

        c.parent = p.parent;
        p.parent = c;
        p.left = c.right;
        c.right = p;
    }

    private void makeRightChildParent(SplayNode c, SplayNode p) {
        if ((c == null) || (p == null) || (p.right != c) || (c.parent != p)) throw new IllegalArgumentException();
        if (p.parent != null) {
            if (p == p.parent.left) {
                p.parent.left = c;
            } else {
                p.parent.right = c;
            }
        } if (c.left != null) {
            c.left.parent = p;
        }
        c.parent = p.parent;
        p.parent = c;
        p.right = c.left;
        c.left = p;
    }

    private void splay(SplayNode x) {
        while (x.parent != null) {
            SplayNode parent = x.parent;
            SplayNode grandParent = parent.parent;
            if (grandParent == null) {
                if (x == parent.left) {
                    makeLeftChildParent(x, parent);
                } else {
                    makeRightChildParent(x, parent);
                }
            } else {
                if (x == parent.left) {
                    if (parent == grandParent.left) {
                        makeLeftChildParent(parent, grandParent);
                        makeLeftChildParent(x, parent);
                    } else {
                        makeLeftChildParent(x, x.parent);
                        makeRightChildParent(x, x.parent);
                    }
                } else {
                    if (parent == grandParent.left) {
                        makeRightChildParent(x, x.parent);
                        makeLeftChildParent(x, x.parent);
                    } else {
                        makeRightChildParent(parent, grandParent);
                        makeRightChildParent(x, parent);
                    }
                }
            }
        }
        root = x;
    }

    @Override
    public void remove(T element) {
        SplayNode node = get(element);
        remove(node);
    }

    private void remove(SplayNode node)
    {
        if (node == null) return;
        splay(node);
        if( (node.left != null) && (node.right !=null))
        {
            SplayNode min = node.left;
            while(min.right!=null)
                min = min.right;

            min.right = node.right;
            node.right.parent = min;
            node.left.parent = null;
            root = node.left;
        }
        else if (node.right != null)
        {
            node.right.parent = null;
            root = node.right;
        }
        else if( node.left !=null)
        {
            node.left.parent = null;
            root = node.left;
        }
        else
        {
            root = null;
        }
        node.parent = null;
        node.left = null;
        node.right = null;
        node = null;
        count--;
    }

    public int countAllNodes() {
        return count;
    }

    @Override
    public boolean contains(T val) {
        return get(val) != null;
    }

    private SplayNode get(T element) {
        SplayNode prevNode = null;
        SplayNode z = root;
        while (z != null) {
            prevNode = z;
            if (element.compareTo(z.element) > 0) {
                z = z.right;
            } else if (element.compareTo(z.element) < 0) {
                z = z.left;
            } else if(element.equals(z.element)) {
                splay(z);
                return z;
            }

        }

        if(prevNode != null) {
            splay(prevNode);
            return null;
        }
        return null;
    }

    public List<T> inOrderTraversal() {
        List<T> res = new ArrayList<>();
        return inOrderTraversal(root, res);
    }

    private List<T> inOrderTraversal(SplayNode r, List<T> res) {
        if (r != null) {
            inOrderTraversal(r.left, res);
            res.add(r.element);
            inOrderTraversal(r.right, res);
        }
        return res;
    }

    public static void main(String[] args) {
        SplayTree<String> tree = new SplayTree<>();
        tree.add("1");
        tree.add("2");
        tree.add("3");
        tree.add("4");
        System.out.println(tree.inOrderTraversal());
        System.out.println(tree.contains("3"));
        System.out.println(tree.countAllNodes());
        System.out.println(tree.isEmpty());
        tree.remove("3");
        System.out.println(tree.inOrderTraversal());
        tree.clear();
        System.out.println(tree.inOrderTraversal());
    }
}
