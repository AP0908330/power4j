package com.wsx.play.datastructure.unionfind;

/**
 * @Description 基于size优化、基于rank优化.
 * @Author:ShangxiuWu
 * @Date: 9:03 2020/7/12.
 * @Modified By:
 */
public class TreeUnionFind1 implements UnionFind {

  private int[] parent;
  //基于size优化并查集
  private int[] sizeArr;

  public TreeUnionFind1(int size) {
    this.parent = new int[size];
    this.sizeArr = new int[size];
    for (int i = 0; i < parent.length; i++) {
      parent[i] = i;
      sizeArr[i] = 1;
    }
  }

  private int find(int p) {
    if (p < 0 || p >= parent.length) {
      throw new IllegalArgumentException("p is out of bound.");
    }
    while (p != parent[p]) {
      p = parent[p];
    }
    return p;
  }

  @Override
  public int getSize() {
    return parent.length;
  }

  @Override
  public boolean isConnected(int p, int q) {
    return find(p) == find(q);
  }

  /**
   *@Description 基于size维护并查集.
   *@Author  wusx
   *@Date 10:06 2020/7/12
   *@Modified
   */
  @Override
  public void unionElement(int p, int q) {
    int pRoot = find(p);
    int qRoot = find(q);
    if (pRoot == qRoot) {
      return;
    }
    if (sizeArr[pRoot] < sizeArr[qRoot]) {
      parent[pRoot] = qRoot;
      sizeArr[pRoot] += sizeArr[qRoot];
    }else{
      parent[qRoot] = pRoot;
      sizeArr[qRoot] += sizeArr[pRoot];
    }
  }

}
