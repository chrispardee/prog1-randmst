import java.util.Random;
import java.util.LinkedList;
import java.util.ListIterator;



class Prim {
    //global variables go here
    LinkedList<Node> remainingNodes;
    float totalWeight;

    class Node {
        int vertex;
        float weight;
    
        Node(int vertex, float weight){
            this.vertex = vertex;
            this.weight = weight;
        }
    }
    

    public Node deleteMin(){
        Node minNode = new Node(0, (float) 2.0);
        for(int i = 0; i < remainingNodes.size(); i++){
            if (remainingNodes.get(i) != null){
                if (minNode.weight > remainingNodes.get(i).weight){
                    minNode = remainingNodes.get(i); //swap the nodes
                }
            }
        }
        if(minNode.weight == (float) 2.0){ //distArr was empty - there is no min
            return null;
        }

        Node retNode = new Node(minNode.vertex, minNode.weight); //copy the min node

        remainingNodes.set(minNode.vertex, null); //delete the min by setting minNode to null
        return retNode;
    }

    public float prim(int n){
        remainingNodes = new LinkedList<Node>();
        totalWeight = (float) 0.0;
        Random gen = new Random();


        for (int i = 0; i < n; i++){
            if (i == 0){
                remainingNodes.add(new Node(i, 0));
            }
            else{
                remainingNodes.add(new Node(i, (float) 2.0)); // 2.0 is bigger than any edge
            }
        }

        Node minNode = new Node(0, (float) 2.0);
        int[] path = new int[n];
        for(int i = 0; i < n; i++){
            path[i] = -1;
        }

        while (minNode != null){    
            minNode = deleteMin();
            if(minNode == null){
                break;
            }
            boolean existsDup = false; //check if there exists duplicates
            for(int i = 0; i < n; i++){
                if (path[i] == minNode.vertex){
                    existsDup = true;
                }
                if (path[i] == -1){
                    path[i] = minNode.vertex;
                    break;
                }
            }
            if(existsDup){
                System.out.println("The duplicate was: "+ String.valueOf(minNode.vertex));
                break;
            }
            totalWeight += minNode.weight;
            float edgeWeight;
            //generate edges from minNode
            for(int i = 0; i < n; i++){ //make an edge for each vertex
                edgeWeight = gen.nextFloat();
                if(remainingNodes.get(i) != null){
                    if (edgeWeight < remainingNodes.get(i).weight){
                        remainingNodes.get(i).weight = edgeWeight;
                    }  
                } 
            }
            for(int i = 0; i < n; i++){
                if (remainingNodes.get(i) == null){
                    System.out.println(i + " : visited");
                }
                else{
                    System.out.println(remainingNodes.get(i).vertex + " : " + remainingNodes.get(i).weight);
                }
            }
            System.out.println();
        }

        //deleteMin returned nothing so we're done
        for(int i = 0; i < n; i++){
            System.out.println(path[i]);
        }
        return totalWeight;
    }
    

}

class randmst {
    public static void main(String[] args){
        int n = 128;
        Prim primAlgo = new Prim(); 
        float weight = primAlgo.prim(n);
        System.out.println();
        System.out.println("Total weight of MST:");
        System.out.println(weight);
    }
}

//./randmst 0 numpoints numtrials dimension