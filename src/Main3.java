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

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main3 {

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
        public int findSink(int[][] adj)  {
            int n = adj.length; // Number of vertices in the graph
            int i = 0; //adj
            int j = 0; //adj[]


//            while (i < n && adj[i][j] == 0 ) {
//                j++;
//                while (j < n && adj[i][j] == 1){
//                    i++;
//                }
//            }

            while (i < n && j < n ) {
                if (adj[i][j] == 1){
                   i++;
                } else {
                    j++;
                }
            }


            for (int a = 0; a < n; a++) {
                if (a != i && (adj[i][a] == 1 || adj[a][i] == 0)) {
                    return -1;
                }
            }
            //Be sure that the selected vertex is the global sink

            return i;
        }

        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int adj[][] = new int[n][n];
            for (int i = 0 ; i < n ; i++) {
                for (int j = 0; j < n ; j++)
                    adj[i][j] = in.nextInt();
            }
            int result = findSink(adj);
            System.out.println(result);
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