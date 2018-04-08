package ru.spbau.solikov.aeparser;

public class Tree {
    public String getValue() {
        return value;
    }

    public Tree getLeft() {
        return left;
    }

    public Tree getRight() {
        return right;
    }

    private String value;

    private Tree left;
    private Tree right;

    public Tree(String node, Tree left, Tree right) {
        this.value = node;
        this.left = left;
        this.right = right;
    }

    public Tree(String node) {
        this.value = node;
    }

    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + value);
        if (left != null) {
            left.print(prefix + (isTail ? "    " : "│   "), false);
        }

        if (right != null) {
            right.print(prefix + (isTail ? "    " : "│   "), false);
        }
    }
}
