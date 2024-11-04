//TC: O(n+m)
//SC: O(n+m)
class Solution {
    private int time = 0; // Timer to track discovery time of nodes

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> result = new ArrayList<>();
        
        // Graph represented as adjacency list
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        // Populate the adjacency list from the connections input
        for (List<Integer> connection : connections) {
            int u = connection.get(0);
            int v = connection.get(1);
            graph[u].add(v);
            graph[v].add(u);
        }
        
        int[] disc = new int[n]; // discovery times of visited vertices
        int[] low = new int[n];  // earliest visited vertex reachable from subtree
        Arrays.fill(disc, -1);   // initialize as unvisited
        
        // Start DFS from node 0
        dfs(0, -1, graph, disc, low, result);
        
        return result;
    }
    
    private void dfs(int u, int parent, List<Integer>[] graph, int[] disc, int[] low, List<List<Integer>> result) {
        disc[u] = low[u] = ++time; // Discovery time and low time of `u`
        
        for (int v : graph[u]) {
            if (v == parent) {
                continue; // If `v` is the parent of `u`, skip it
            }
            
            if (disc[v] == -1) { // If `v` is not visited
                dfs(v, u, graph, disc, low, result);
                
                // Check if the subtree rooted at `v` has a connection back to one of the ancestors of `u`
                low[u] = Math.min(low[u], low[v]);
                
                // If the lowest reachable vertex from `v` is greater than `u`'s discovery time, then it's a critical connection
                if (low[v] > disc[u]) {
                    result.add(Arrays.asList(u, v));
                }
            } else {
                // Update low value of `u` for parent function calls
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }
}
