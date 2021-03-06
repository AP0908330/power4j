package com.wsx.play.graph.dfs;

import com.wsx.play.graph.Graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description 深度优先遍历，单源路径问题优化，提前终止.
 * @Author:ShangxiuWu
 * @Date: 20:25 2020/7/22.
 * @Modified By:
 */
public class Path {

  private Graph graph;
  private int source;
  private int target;
  /**访问标记.*/
  private int[] pre;


  public Path(Graph graph, int source, int target) {

    graph.validateVertex(source);
    graph.validateVertex(target);

    this.graph = graph;
    this.source = source;
    this.target = target;
    pre = new int[graph.getVertex()];
    for (int i = 0; i < pre.length; i++) {
      pre[i] = -1;
    }
    dfs(source, source);
    System.out.println(Arrays.toString(pre));
  }


  /**
   * 递归方法实现，TODO非递归实现，邻接矩阵深度优先遍历
   * @param v
   */
  private boolean dfs(int v, int parent) {
    pre[v] = parent;
    if (v == target) {
      return true;
    }
    for (int w : graph.adjacencyVertex(v)) {
      if (pre[w] == -1) {
        if (dfs(w, v)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 是否联通
   * @param
   * @return
   */
  public boolean isConnectTo() {
    graph.validateVertex(target);
    return pre[target] != -1;
  }

  /**
   * 联通路径
   * @param
   * @return
   */
  public Iterable<Integer> path() {
    List<Integer> path = new ArrayList<>();
    if (!isConnectTo()) {
      return path;
    }
    int cur = target;
    while (cur != source) {
      path.add(cur);
      cur = pre[cur];
    }
    path.add(source);
    Collections.reverse(path);
    return path;
  }


  public static void main(String[] args) {
    Graph g = new Graph("H:\\workspace\\power4j\\datastructure-algorithm\\g.txt");
    Path dfs = new Path(g, 0,6);
    System.out.println(dfs.path());
    System.out.println(new Path(g, 0,5).path());
  }

}
