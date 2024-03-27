import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.*;

import java.io.BufferedReader;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {

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


        public int[] numberOfShortestPaths(List<List<Integer>> adj,int s) {
            int V = adj.size(); //Vertexes
            int[] color = new int[V]; // Vertexes colors -> 0 = White, 1 = Gray, 2 = Black
            int[] distance = new int[V]; //Stores the distance from the source vertex s to every other vertex.
            int[] pathCount = new int[V]; //Stores the number of distinct shortest paths from vertex s to other vertexes.

            //fill the arrays with default values
            for (int i = 0; i < V; i++) {
                distance[i] = Integer.MAX_VALUE;
                pathCount[i] = 0;
                color[i] = 0;
            }

            Queue<Integer> queue = new LinkedList<>();
            queue.add(s);
            color[s] = 1;
            distance[s] = 0;
            pathCount[s] = 1;

            while (!queue.isEmpty()) {
                int u = queue.poll();
                for (int i = 0; i < adj.get(u).size(); i++) {
                    int v = adj.get(u).get(i);
                    if (color[v] == 0) {
                        color[v] = 1;
                        queue.add(v);
                    }
                    if (distance[v] > distance[u] + 1) {
                        distance[v] = distance[u] + 1;
                        pathCount[v] = pathCount[u];
                    } else if (distance[v] == distance[u] + 1) {
                        pathCount[v] += pathCount[u];
                    }
                }
                color[u] = 2;
            }

            return pathCount;
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
                u--;
                v--;
                adj.get(u).add(v);
                adj.get(v).add(u);
            }

            int s = in.nextInt();
            s--;

            int[] costs = numberOfShortestPaths(adj,s);

            for (int i = 0 ; i < costs.length ; i++) {
                System.out.print(costs[i] + " ");
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