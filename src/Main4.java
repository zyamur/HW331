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

//            6 8
//            1 2 2
//            1 3 4
//            2 3 1
//            2 4 7
//            3 5 3
//            4 6 1
//            5 4 2
//            5 6 5
//            1 6
            boolean[] visited = new boolean[adj.size()];
            long[] distance = new long[adj.size()];
            int edgeNumber = 0;
            for (int i = 0; i < adj.size(); i++) {
                distance[i] = Long.MAX_VALUE;
            }
            distance[s] = 0;


            if(s == t)
                return 0;

            //edge checking
            for(int i = 0; i < adj.size(); i++)
            {
                edgeNumber += adj.get(i).size();
            }
            if(edgeNumber == 0)
                return -1;



            Comparator<Main4.TaskA.Pair> comparator = new Comparator<Main4.TaskA.Pair>() {
                public int compare(Main4.TaskA.Pair pair1, Main4.TaskA.Pair pair2) {
                    return Long.compare(pair1.cost, pair2.cost);
                }
            };

            PriorityQueue<Main4.TaskA.Pair> pq = new PriorityQueue<>(comparator);
            pq.add(new Main4.TaskA.Pair(s, 0));



            while (!pq.isEmpty()) {
                Main4.TaskA.Pair u = pq.poll();
                if (visited[u.node]) {
                    continue;
                }
                visited[u.node] = true;


                // Relaxation
                for (Main4.TaskA.Pair neighbor : adj.get(u.node)) {
                    if (!visited[neighbor.node] && distance[u.node] + neighbor.cost < distance[neighbor.node]) {
                        distance[neighbor.node] = distance[u.node] + neighbor.cost;
                        pq.add(new Main4.TaskA.Pair(neighbor.node, distance[neighbor.node]));
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
                int cost = in.nextInt(); // Assuming the cost of the edge is given
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