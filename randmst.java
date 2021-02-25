import java.util.Random;
import java.util.ArrayList;
import java.lang.Math; 

class Prim {
    //global variables go here
    ArrayList<Node> remainingNodes;
    float totalWeight;

    class Node {
        int vertex;
        float weight;
        float x;
        float y;
        float z;
        float w;
    
        Node(int vertex, float weight){ // dim = 1
            this.vertex = vertex;
            this.weight = weight;
        }

        Node(int vertex, float x, float y){ // unit square - dim = 2
            this.vertex = vertex;
            this.x = x;
            this.y = y;
            this.weight = (float) 2.0;
        }

        Node(int vertex, float x, float y, float z){ // unit cube - dim = 3
            this.vertex = vertex;
            this.x = x;
            this.y = y;
            this.z = z;
            this.weight = (float) 2.0;
        }

        Node(int vertex, float x, float y, float z, float w){ // hypercube - dim = 4
            this.vertex = vertex;
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
            this.weight = (float) 2.0;
        }
    }

    public float euclidDist(float x1, float x2, float y1, float y2){
        // calculate euclidian distance with 2 dimensions
        double dist = Math.sqrt( (Math.pow((x1 - x2),2)) + (Math.pow((y1 - y2),2)) );
        return (float) dist;
    }

    public float euclidDist(float x1, float x2, float y1, float y2, float z1, float z2){
        // calculate euclidian distance with 3 dimensions
        double dist = Math.sqrt( (Math.pow((x1 - x2),2)) + (Math.pow((y1 - y2),2)) + (Math.pow((z1 - z2),2)));
        return (float) dist;
    }

    public float euclidDist(float x1, float x2, float y1, float y2, float z1, float z2, float w1, float w2){
        // calculate euclidian distance with 4 dimensions
        double dist = Math.sqrt( (Math.pow((x1 - x2),2)) + (Math.pow((y1 - y2),2)) + (Math.pow((z1 - z2),2)) + (Math.pow((w1 - w2),2)) );
        return (float) dist;
    }

    public void printNodes(ArrayList<Node> remainingNodes, int n){
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

    public void checkDups(int[] path, Node minNode, int n) throws Exception{
        // check if there exists duplicates of minNode in path
        boolean existsDup = false; 
        for(int i = 0; i < n; i++){
            if (path[i] == minNode.vertex){
                existsDup = true;
            }
            if (path[i] == -1){
                path[i] = minNode.vertex;
                break;
            }
        }
        if(existsDup){ // raise an exception if there is a duplicate
            throw new Exception("The duplicate was: "+ String.valueOf(minNode.vertex));
        }
    }

    public void printPath(int[] path, int n){
        for(int i = 0; i < n; i++){
            System.out.println(path[i]);
        }
    }

    public Node deleteMin(int dimensions){
        Node minNode = new Node(0, (float) 2.0);

        switch(dimensions){ 
            // initialize minNode so we can enter the while loop - it gets overwritten by deleteMin
            case 2:
                minNode = new Node(0, (float) -1.0, (float) -1.0);
                break;
            case 3:
                minNode = new Node(0, (float) -1.0, (float) -1.0, (float) -1.0);
                break;
            case 4:
                minNode = new Node(0, (float) -1.0, (float) -1.0, (float) -1.0, (float) -1.0);
                break;
        }

        for(int i = 0; i < remainingNodes.size(); i++){
            if (remainingNodes.get(i) != null){
                if (minNode.weight > remainingNodes.get(i).weight){
                    minNode = remainingNodes.get(i); //swap the nodes
                }
            }
        }
        if(minNode.weight == (float) 2.0){ //remainingNodes is empty - there is no min
            return null;
        }

        Node retNode = new Node(minNode.vertex, minNode.weight); //copy the min node

        switch(dimensions){ 
            //initialize retNode as a duplicate of minNode so we can return it after deleting minNode
            case 2:
                retNode = new Node(minNode.vertex, minNode.x, minNode.y);
                retNode.weight = minNode.weight; //set the weight
                break;
            case 3:
                retNode = new Node(minNode.vertex, minNode.x, minNode.y, minNode.z);
                retNode.weight = minNode.weight; //set the weight
                break;
            case 4:
                retNode = new Node(minNode.vertex, minNode.x, minNode.y, minNode.z, minNode.w);
                retNode.weight = minNode.weight; //set the weight
                break;
        }

        remainingNodes.set(minNode.vertex, null); //delete the min by setting minNode to null
        return retNode;
    }

    public float prim(int n, int dimensions){
        remainingNodes = new ArrayList<Node>();
        totalWeight = (float) 0.0;
        Random gen = new Random();
        gen.setSeed(gen.nextLong());
        Node tempNode = new Node(0, 0);
        float x;
        float y;
        float z;
        float w;

        for (int i = 0; i < n; i++){
            if (i == 0){
                switch(dimensions){
                    case 0:
                        remainingNodes.add(new Node(i, 0));
                        break;
                    case 2:
                        tempNode = new Node(i, gen.nextFloat(), gen.nextFloat());
                        tempNode.weight = (float) 0.0;
                        remainingNodes.add(tempNode);
                        break;
                    case 3:
                        tempNode = new Node(i, gen.nextFloat(), gen.nextFloat(), gen.nextFloat());
                        tempNode.weight = (float) 0.0;
                        remainingNodes.add(tempNode);
                        break;
                    case 4:
                        tempNode = new Node(i, gen.nextFloat(), gen.nextFloat(), gen.nextFloat(), gen.nextFloat());
                        tempNode.weight = (float) 0.0;
                        remainingNodes.add(tempNode);
                        break;
                }
            }
            else{
                switch(dimensions){
                    case 0:
                        remainingNodes.add(new Node(i, (float) 2.0)); // 2.0 is bigger than any edge
                        break;
                    case 2:
                        x = gen.nextFloat();
                        y = gen.nextFloat();
                        remainingNodes.add(new Node(i, x, y));
                        break;
                    case 3:
                        x = gen.nextFloat();
                        y = gen.nextFloat();
                        z = gen.nextFloat();
                        remainingNodes.add(new Node(i, x, y, z));
                        break;
                    case 4:
                        x = gen.nextFloat();
                        y = gen.nextFloat();
                        z = gen.nextFloat();
                        w = gen.nextFloat();
                        remainingNodes.add(new Node(i, x, y, z, w));
                        break;
                }
            }
            gen.setSeed(gen.nextLong());
        }

        Node minNode = new Node(0, (float) 2.0);

        switch(dimensions){ 
            // initialize minNode so we can enter the while loop - it gets overwritten by deleteMin
            case 2:
                minNode = new Node(0, (float) -1.0, (float) -1.0);
                break;
            case 3:
                minNode = new Node(0, (float) -1.0, (float) -1.0, (float) -1.0);
                break;
            case 4:
                minNode = new Node(0, (float) -1.0, (float) -1.0, (float) -1.0, (float) -1.0);
                break;
        }
        
        // int[] path = new int[n];
        // for(int i = 0; i < n; i++){
        //     path[i] = -1;
        // }

        while (minNode != null){    
            minNode = deleteMin(dimensions);
            if(minNode == null){
                break;
            }

            // try{
            //     checkDups(path, minNode, n); // check for duplicates
            // }
            // catch(Exception e){
            //     System.err.println(e.getMessage());
            //     System.exit(-1);
            // }
            
            totalWeight += minNode.weight;
            float edgeWeight = (float) 0.0;
            //generate edges from minNode
            for(int i = 0; i < n; i++){ //make an edge for each vertex
                if(remainingNodes.get(i) != null){
                    switch(dimensions){
                        case 0:
                            edgeWeight = gen.nextFloat();
                            gen.setSeed(gen.nextLong() + i);
                            break;
                        case 2:
                            edgeWeight = euclidDist(minNode.x, remainingNodes.get(i).x, 
                                                    minNode.y, remainingNodes.get(i).y);
                            break;
                        case 3:
                            edgeWeight = euclidDist(minNode.x, remainingNodes.get(i).x, 
                                                    minNode.y, remainingNodes.get(i).y,
                                                    minNode.z, remainingNodes.get(i).z
                                                    );
                            break;
                        case 4:
                            edgeWeight = euclidDist(minNode.x, remainingNodes.get(i).x, 
                                                    minNode.y, remainingNodes.get(i).y,
                                                    minNode.z, remainingNodes.get(i).z,
                                                    minNode.w, remainingNodes.get(i).w
                                                    );
                            break;  
                    }
                    if (edgeWeight < remainingNodes.get(i).weight){
                        remainingNodes.get(i).weight = edgeWeight;
                    }
                }
            }
            // printNodes(remainingNodes, n); // print the nodes
        } //end while loop - deleteMin returned nothing so we're done
        // printPath(path, n); //print the path
        return totalWeight;
    }
}

class randmst {
    public static void main(String[] args){
        //./randmst 0 numpoints numtrials dimension
        // flag not used
        int flag = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        int trials = Integer.parseInt(args[2]);
        int dim = Integer.parseInt(args[3]);
        // int[] numVerts = {128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288};
        // int[] dimArr = {0, 2, 3, 4};
        // int trials = 4;
        float avg = 0;
        Prim primAlgo = new Prim();

        avg = (float) 0.0;
        for(int i = 0; i < trials; i++){
            float weight = primAlgo.prim(n, dim);
            System.out.println("Total weight of MST: " + weight);
            avg += weight / trials;
        }
        System.out.println("Avg weight for " + n + " vertices, " + trials + " trials, " + dim + " dimensions = " + avg);
        System.out.println();
        
        // for(int dim : dimArr){
        //     for(int n : numVerts){
        //         avg = (float) 0.0;
        //         for(int i = 0; i < trials; i++){
        //             float weight = primAlgo.prim(n, dim);
        //             System.out.println("Total weight of MST: " + weight);
        //             avg += weight / trials;
        //         }
        //         System.out.println("Avg weight for " + n + " vertices, " + trials + " trials, " + dim + " dimensions = " + avg);
        //         System.out.println();
        //     }
        // }
    }
}
