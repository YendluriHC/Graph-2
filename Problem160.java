//TC: O(n^2)
//SC: O(n)
class Solution {
    public int minMalwareSpread(int[][] graph, int[] initial) {
        int n = graph.length;
        
        // Sort the initial array to ensure we pick the smallest node in case of a tie
        Arrays.sort(initial);
        
        // Union-Find data structure to find connected components
        UnionFind uf = new UnionFind(n);
        
        // Build the connected components
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (graph[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        
        // Count the size of each component
        int[] componentSize = new int[n];
        for (int i = 0; i < n; i++) {
            int root = uf.find(i);
            componentSize[root]++;
        }
        
        // Count how many initial infected nodes are in each component
        int[] infectedCount = new int[n];
        for (int node : initial) {
            int root = uf.find(node);
            infectedCount[root]++;
        }
        
        // Now find the best node to remove
        int result = -1;
        int maxSaved = -1;
        
        for (int node : initial) {
            int root = uf.find(node);
            
            // If this component has exactly one infected node, we can save the entire component
            if (infectedCount[root] == 1) {
                int saved = componentSize[root];
                
                // We want to maximize the number of saved nodes
                if (saved > maxSaved) {
                    maxSaved = saved;
                    result = node;
                }
            }
        }
        
        // If no node was able to save any component, return the smallest index node
        return result == -1 ? initial[0] : result;
    }
    
    // Union-Find data structure
    class UnionFind {
        int[] parent, rank;
        
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }
}
