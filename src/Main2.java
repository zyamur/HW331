import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import java.io.BufferedReader;
import java.util.Comparator;
import java.io.InputStream;
import java.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main2 {

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskA solver = new TaskA();
        solver.solve(in, out);
        out.close();
    }

    static class TaskA {

        public int[] articulationPoints(List<List<Integer>> adj) {
            int V = adj.size();
            boolean[] visited = new boolean[V];
            int[] disc = new int[V];
            int[] low = new int[V];
            int[] parent = new int[V];
            boolean[] ap = new boolean[V]; // To store articulation points
            Arrays.fill(parent, -1);
            Arrays.fill(ap, false);
            Arrays.fill(disc, -1);
            Arrays.fill(low, -1);

            for (int i = 0; i < V; ++i) {
                if (!visited[i]) {
                    dfs(i, visited, disc, low, parent, ap, adj);
                }
            }

            int count = 0;
            for (boolean isAP : ap) {
                if (isAP) {
                    count++;
                }
            }

            int[] result = new int[count];
            int index = 0;
            for (int i = 0; i < V; i++) {
                if (ap[i]) {
                    result[index++] = i;
                }
            }

            return result;
        }
        private void dfs(int u, boolean[] visited, int[] disc, int[] low, int[] parent, boolean[] ap, List<List<Integer>> adj) {
            Stack<Integer> stack = new Stack<>();
            stack.push(u);
            int V = adj.size();

            int time = 0; // Initialize discovery time
            while (!stack.isEmpty()) {
                int current = stack.peek();
                if (!visited[current]) {
                    visited[current] = true;
                    disc[current] = low[current] = ++time;
                }

                boolean isFinished = true;
                for (int v : adj.get(current)) {
                    // If v is not visited yet
                    if (!visited[v]) {
                        stack.push(v);
                        parent[v] = current;
                        isFinished = false;
                        break; // Simulate the recursive call
                    } else if (v != parent[current]) {
                        // Check for back edge
                        low[current] = Math.min(low[current], disc[v]);
                    }
                }

                if (isFinished) {
                    stack.pop();
                    if (!stack.isEmpty()) {
                        int par = parent[current];
                        low[par] = Math.min(low[par], low[current]);
                        if (low[current] >= disc[par] && par != -1) {
                            ap[par] = true;
                        }
                    }
                }
            }

             //Special case for the root
            int rootChildren = 0;
            for (int i = 0; i < V; i++) {
                if (parent[i] == u) rootChildren++;
            }
            if (rootChildren > 1) ap[u] = true; // Root is an articulation point if it has more than one child
        }


        public void solve(InputReader in, PrintWriter out) {

            int n = in.nextInt();
            int m = in.nextInt();

            // Initialize the adjacency list
            List<List<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<>());
            }

            // Read m lines with u and v, and construct the adjacency list
            for (int i = 0; i < m; i++) {
                int u = in.nextInt();
                int v = in.nextInt();
//                u--;
//                v--;
                adj.get(u).add(v);
                adj.get(v).add(u);
            }

            int[] points = articulationPoints(adj);

            System.out.println(points.length);
            for (int i = 0 ; i < points.length ; i++) {
                System.out.print((points[i] + 1) + " ");
            }
            System.out.println();
        }

    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }


        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }


    }
}