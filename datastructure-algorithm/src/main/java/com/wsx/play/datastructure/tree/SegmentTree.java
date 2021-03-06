package com.wsx.play.datastructure.tree;

import java.util.Arrays;

/**
 * @Description 线段树（区间树）.
 * 平衡二叉树，叶子节点深度差不超过1
 * 求和线段树
 * @Author:ShangxiuWu
 * @Date: 23:29 2020/7/6.
 * @Modified By:
 */
public class SegmentTree<E> {

  private E[] tree;
  private E[] data;
  private Merger<E> merger;

  public SegmentTree(E[] arr, Merger<E> merger) {
    this.merger = merger;
    this.data = (E[]) new Object[arr.length];
    for (int i = 0; i < data.length; i++) {
      this.data[i] = arr[i];
    }
    this.tree = (E[]) new Object[data.length * 4];
    buildSegmentTree(0, 0, data.length - 1);
  }

  private void buildSegmentTree(int treeIndex, int left, int right) {

    if (left == right) {
      tree[treeIndex] = data[left];
      return;
    }
    int leftChildIndex = leftChildIndex(treeIndex);
    int rightChildIndex = rightChildIndex(treeIndex);
    int mid = left + (right - left) / 2;
    buildSegmentTree(leftChildIndex, left, mid);
    buildSegmentTree(rightChildIndex, mid + 1, right);
    tree[treeIndex] = merger.merge(tree[leftChildIndex], tree[rightChildIndex]);
  }


  private int leftChildIndex(int index) {
    return index * 2 + 1;
  }

  private int rightChildIndex(int index) {
    return index * 2 + 2;
  }

  public int getSize() {
    return data.length;
  }

  public E get(int index) {
    if (index < 0 || index >= data.length) {
      throw new IllegalArgumentException("illegal index.");
    }
    return data[index];
  }

  public void set(int index, E e) {
    if (0 > index || index >= data.length) {
      throw new IllegalArgumentException("index is illegal.");
    }
    data[index] = e;
    set(0, 0, data.length, index, e);
  }

  private void set(int treeIndex, int l, int r, int index, E e) {
    if (l == r) {
      tree[treeIndex] = e;
      return;
    }
    int mid = l + (r - l) / 2;
    int leftChildIndex = leftChildIndex(treeIndex);
    int rightChildIndex = rightChildIndex(treeIndex);
    if (index >= mid + 1) {
      set(rightChildIndex, mid + 1, r, index, e);
    } else {
      set(leftChildIndex, l, mid, index, e);
    }
    tree[treeIndex] = merger.merge(tree[leftChildIndex], tree[rightChildIndex]);
  }

  public E query(int queryL, int queryR) {
    if (queryL > queryR || queryL < 0 || queryL >= data.length ||
        queryR < 0 || queryR >= data.length) {
      throw new IllegalArgumentException("index is illegal.");
    }
    return query(0, 0, data.length - 1, queryL, queryR);
  }

  /**
   *@Description 在以treeIndex为根的线段树中[l...r]的范围里，搜索[queryL...queryR]的值.
   *@Author wusx
   *@Date 下午10:32 2020/7/10
   *@Modified
   */
  private E query(int treeIndex, int l, int r, int queryL, int queryR) {
    if (l == queryL && r == queryR) {
      return tree[treeIndex];
    }
    int mid = l + (r - l) / 2;
    int leftChildIndex = leftChildIndex(treeIndex);
    int rightChildIndex = rightChildIndex(treeIndex);
    if (queryL >= mid + 1) {
      return query(rightChildIndex, mid + 1, r, queryL, queryR);
    } else if (queryR <= mid) {
      return query(leftChildIndex, l, mid, queryL, queryR);
    } else {
      E left = query(leftChildIndex, l, mid, queryL, mid);
      E right = query(rightChildIndex, mid + 1, r, mid + 1, queryR);
      return merger.merge(left, right);
    }
  }

  @Override
  public String toString() {
    return Arrays.toString(tree);
  }

  public static void main(String[] args) {
    Integer[] nums = new Integer[]{-2, 0, 3, -5, 2, -1};
    SegmentTree<Integer> tree = new SegmentTree<>(nums, (a, b) -> a + b);
    System.out.println(tree);
    System.out.println(tree.query(0, 2));
    System.out.println(tree.query(2, 5));
    System.out.println(tree.query(0, 5));
  }

}
