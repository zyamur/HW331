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
public class Main4 {

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


        static class Pair {
            int node;
            long cost;

            Pair(int node, long cost) {
                this.node = node;
                this.cost = cost;
            }
        }

        public long shortestPath(List<List<Pair>> adj, int s, int t) {

            long[] distance = new long[adj.size()];
            for (int i = 0; i < adj.size(); i++) {
                distance[i] = Long.MAX_VALUE;
            }
            distance[s] = 0;

            Comparator<Pair> comparator = new Comparator<Pair>() {
                public int compare(Pair pair1, Pair pair2) {
                    return Long.compare(pair1.cost, pair2.cost);
                }
            };

            // PriorityQueue'yu oluştururken Comparator'ı kullan
            PriorityQueue<Pair> pq = new PriorityQueue<>(comparator);

            pq.add(new Pair(s, 0));

            boolean[] visited = new boolean[adj.size()];

            while (!pq.isEmpty()) {
                Pair u = pq.poll();
                if (visited[u.node]) {
                    continue;
                }
                visited[u.node] = true;

                // Relaxation
                for (Pair neighbor : adj.get(u.node)) {
                    if (!visited[neighbor.node] && distance[u.node] + neighbor.cost < distance[neighbor.node]) {
                        distance[neighbor.node] = distance[u.node] + neighbor.cost;
                        pq.add(new Pair(neighbor.node, distance[neighbor.node]));
                    }
                }
            }

            return distance[t] == Long.MAX_VALUE ? -1 : distance[t];
        }

        public void solve(InputReader in, PrintWriter out) {

            int n = in.nextInt();
            int m = in.nextInt();

            // Initialize the adjacency list with pairs (node, cost)
            List<List<Pair>> adj = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<>());
            }

            // Read m lines with u, v, and cost, and construct the adjacency list
            for (int i = 0; i < m; i++) {
                int u = in.nextInt();
                int v = in.nextInt();
                u--;
                v--;
                long cost = in.nextLong(); // Assuming the cost of the edge is given
                adj.get(u).add(new Pair(v, cost)); // Assuming the graph is directed
            }

            // Now you can call shortestPath with the adjacency list and source and target nodes
            // For example:
            int s = in.nextInt();
            int t = in.nextInt();
            s--;
            t--;
            long shortestPathCost = shortestPath(adj, s, t);
            System.out.println(shortestPathCost);
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