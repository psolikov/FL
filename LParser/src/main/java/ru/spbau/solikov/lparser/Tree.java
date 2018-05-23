package ru.spbau.solikov.lparser;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private String value;

    private List<Tree> children = new ArrayList<>();

    public Tree(String node, List<Tree> children) {
        this.value = node;
        this.children = children ;
    }

    public Tree(String node) {
        this.value = node;
    }

    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + value);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1)
                    .print(prefix + (isTail ? "    " : "│   "), true);
        }
    }
}
