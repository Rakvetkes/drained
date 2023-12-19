package org.aki.drained.common.util;

public class Pair<A, B> {

    private static final int HASH_MULTIPLIER = 32;
    private A left;
    private B right;

    public Pair(A left, B right) {
        this.left = left;
        this.right = right;
    }

    public A getLeft() {
        return this.left;
    }

    public void setLeft(A left) {
        this.left = left;
    }

    public B getRight() {
        return this.right;
    }

    public void setRight(B right) {
        this.right = right;
    }

    @Override
    public int hashCode() {
        int leftCode = left == null ? 0 : left.hashCode();
        int rightCode = right == null ? 0 : right.hashCode();
        return leftCode * HASH_MULTIPLIER + rightCode;
    }

    @Override
    public boolean equals(Object rhs) {
        return rhs.getClass().equals(this.getClass()) && ((Pair<?, ?>) rhs).left.equals(this.left) && ((Pair<?, ?>) rhs).right.equals(this.right);
    }

}