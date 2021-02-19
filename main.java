import java.util.Random;
import java.util.LinkedList;

class Node {
    int vertex;
    double weight;

    Node(int vertex, double weight){
        this.vertex = vertex;
        this.weight = weight;
    }
}

class RandomGraph {
  //global variables go here
  LinkedList<Node>[] adjList;
  Random gen = new Random();
  
//   public double threshold(){
    
//   }
  
  public void printGraph() {
    //loop over the array of linkedlists and return a string
    for(int i = 0; i < adjList.length; i++){
        //loop over array indicies
        System.out.println(i + ": ");
        LinkedList currentList = adjList[i];
        //loop over linkedlist
        for(int j = 0; j < currentList.size(); j++){
            Node jNode = adjList[i].get(j);
            System.out.println(jNode.vertex + "," + jNode.weight + " | ");
        }
        System.out.println();
    }  
  }
  
  public RandomGraph(int n){
  	adjList  = new LinkedList[n];
    
    for(int i = 0; i < n; i++){
        LinkedList currentList = new LinkedList();
        adjList[i] = currentList; 
        for(int j = 0; j < n; j++){
            double edgeWeight = gen.nextDouble();
            Node newNode;
            if (i == j) {
                newNode = new Node(j, 0);
            }
            else {
                newNode = new Node(j, edgeWeight);
            }
            currentList.add(newNode);
        }
    }
  }
  
}

class main{
    public static void main(String[] args){
        // RandomGraph graph = RandomGraph(args[1]);
        RandomGraph graph = new RandomGraph(5);
        graph.printGraph();   
    }
}


//./randmst 0 numpoints numtrials dimension