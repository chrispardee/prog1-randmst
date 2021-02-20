import java.util.Random;
import java.util.LinkedList;

class Node {
    int vertex;
    float weight;
    boolean explored = false;

    Node(){
        this.vertex = 0;
        this.weight = 0;
        this.explored = false;
    }
}

class RandomGraph {
    //global variables go here
    LinkedList<Node>[] adjList;
    Random gen = new Random();

    // n = [2, 31] -> Keep Everything
    // 1 / (log_6 32) = 0.516
    // 1 / (log_5 64) = 0.38
    // 1 / (log_4 128) = 0.28
    // 1 / (log_3 256) = 0.19
    // 1 / (log_2 512) = 0.11
    // 1 / (log_1.85 1024) = 0.088 (46,000 considered edges) RUNS QUICK
    // 1 / (log_1.7 2048) = 0.069 (144,000 considered edges) RUNS QUICK
    // 1 / (log_1.55 4096) = 0.052 (436,000 considered edges) RUNS IN 15s
    // 1 / (log_1.3 8192) = 0.029 (1M considered edges) RUNS IN 40s
    // 1 / (log_1.2 16,384) = 0.018 (2.4M considered edges)
    // 1 / (log_1.1 32,768) = 0.009
    // 1 / (log_1.075 65,536) = 0.0065
    // 1 / (log_1.05 131,072) = 0.004
    // 1 / (log_1.01 262,144) = 0.0007 (25M edges considered)
    // edge weights stored as 4B floats -> 100M Bytes of storage
    public double threshArg(int n) {
        double base;
        if (n < 32) {base = 1;}
        else if (n < 64) {base = 6;}
        else if (n < 128) {base = 5;}
        else if (n < 256) {base = 4;}
        else if (n < 512) {base = 3;}
        else if (n < 1024) {base = 2;}
        else if (n < 2048) {base = 1.85;}
        else if (n < 4096) {base = 1.7;}
        else if (n < 8192) {base = 1.55;}
        else if (n < 16384) {base = 1.3;}
        else if (n < 32768) {base = 1.2;}
        else if (n < 65536) {base = 1.1;}
        else if (n < 131072) {base = 1.075;}
        else if (n < 262144) {base = 1.05;}
        else {base = 1.01;}
        return base;
    }

    public double threshold(int n) {
        double doub_n = n;
        double b = threshArg(n);
        if (b == 1.0) {
            return 1.0;
        }
        else {
            return 1 / ((Math.log(doub_n) / Math.log(b))) ;
        }
    }
  
    public RandomGraph(int n) {
        adjList = new LinkedList[n];
        double threshold = threshold(n);

        for(int i = 0; i < n; i++) {
            LinkedList currentList = new LinkedList();
            adjList[i] = currentList;

            for(int j = 0; j < n; j++) {
                if (i == j) {
                    Node newNode = new Node();
                    newNode.vertex = i;
                    newNode.weight = 0;
                    newNode.explored = true;
                    currentList.add(newNode);
                }
                else {
                    float edgeWeight = gen.nextFloat();
                    if (edgeWeight < threshold) {
                        Node newNode = new Node();
                        newNode.vertex = j;
                        if (i > j) {
                            newNode.weight = adjList[j].get(i).weight;
                        }
                        else {newNode.weight = edgeWeight;}
                        if (j == 0) {
                            newNode.explored = true;
                        }
                        else {newNode.explored = false;}
                        currentList.add(newNode);
                    }
                }
            }
        }
    }

    public void printGraph() {
        //loop over the array of linkedlists and return a string
            for(int i = 0; i < adjList.length; i++) {
                //loop over array indicies
                System.out.println(i + ": ");
                LinkedList currentList = adjList[i];
                //loop over linkedlist
                for(int j = 0; j < currentList.size(); j++) {
                    Node jNode = adjList[i].get(j);
                    System.out.println(jNode.vertex + " | " + jNode.weight + " | " + jNode.explored);
                }
                System.out.println();
            }
        }
    
    public void prim(int n) {
        LinkedList<Node> retTree = new LinkedList<Node>();
        Node nextEdge = new Node();
        double min = 1;
        for(int i = 0; i < adjList[0].size(); i++) {
            if (adjList[0].get(i).weight < min) {
                nextEdge = adjList[0].get(i);
                min = adjList[0].get(i).weight;
            }
        }
        retTree.add(nextEdge);
        
        int fromVertex = -1;
        int counter = 0;
        while (retTree.size() < n) {
            float minWeight = 1;
            Node minEdge = new Node();
            // find next min edge
            for(int i = 0; i < retTree.size(); i++) {
                for(int j = 0; j < adjList[retTree.get(i).vertex].size(); j++) {
                    if (adjList[retTree.get(i).vertex].get(j).explored == false) {
                        if (adjList[retTree.get(i).vertex].get(j).weight < minWeight) {
                            minEdge = adjList[retTree.get(i).vertex].get(j);
                            minWeight = minEdge.weight;
                            fromVertex = retTree.get(i).vertex;
                        }
                    }
                }
            }
            for(int k = 0; k < n; k++) {
                adjList[k].get(minEdge.vertex).explored = true;
            }
            retTree.add(minEdge);
        }

        for (int i = 0; i < retTree.size(); i++) {
            System.out.println(retTree.get(i).vertex + " | " + retTree.get(i).weight);
        }
    }
}

class randmst {
    public static void main(String[] args){
        // RandomGraph graph = RandomGraph(args[1]);
        int n = 31;
        RandomGraph graph = new RandomGraph(n);
        graph.printGraph();
        graph.prim(n);
        System.out.println();
    }
}

//./randmst 0 numpoints numtrials dimension