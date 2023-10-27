import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class nicknames {
    public static void main(String[] args) throws IOException {
        // Kattio io = new Kattio(System.in, System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        AVLTree avl = new AVLTree();
        int A = Integer.parseInt(br.readLine());
        for (int i = 0; i < A; i++) {
            String name = br.readLine();
            avl.insert(name);
        }
        int B = Integer.parseInt(br.readLine());
        for (int j = 0; j < B; j++) {
            String nickname = br.readLine();
            pw.println(avl.search(nickname, avl.root));
        }
        pw.close();
    }

}

class AVLVertex {

    String name;
    int height; // To keep track of balance factor

    AVLVertex parent;
    AVLVertex left;
    AVLVertex right;

    AVLVertex(String name) {
        this.height = 0;
        this.name = name;
        this.parent = null;
        this.left = null;
        this.right = null;

    }

}

// Sort out our AVL tree first
class AVLTree {

    AVLVertex root;

    AVLTree() { // Initialization results in empty AVLTree
        this.root = null;
    }

    int search(String nickname, AVLVertex vertex) {
        if (vertex == null) {
            return 0;
        }
        int count = 0;
        int lengthofnickname = nickname.length();
        String sliced = vertex.name.substring(0, lengthofnickname);
        if (nickname.compareTo(sliced) == 0) { // if same -- we check both left and right subtree
            count++;
            if (vertex.left == null && vertex.right != null) {
                int y = search(nickname, vertex.right);
                count += y;
            } else if (vertex.right == null && vertex.left != null) {
                int x = search(nickname, vertex.left);
                count += x;
            } else {
                int x = search(nickname, vertex.right);
                int y = search(nickname, vertex.left);
                count = count + x + y;
            }

        } else if (nickname.compareTo(sliced) < 0) { // so my nicknam
            if (vertex.left != null) {
                int x = search(nickname, vertex.left);
                count += x;
            }

        } else {
            if (vertex.right != null) {
                int x = search(nickname, vertex.right);
                count += x;
            }

        }
        return count;
    }

    int max(int left, int right) { // Custom
        if (left > right) {
            return left;
        } else {
            return right;
        }
    }

    // not same as height
    int bf(AVLVertex vertex) { // returns balance factor
        if (vertex == null) { // left - right height == 0 since null
            return 0;
        } else {
            return (heightCheck(vertex.left) - heightCheck(vertex.right));
        }
    }

    // check height of vertex
    int heightCheck(AVLVertex vertex) { //
        if (vertex == null) {
            return -1;
        } else {
            return vertex.height;
        }
    }

    // from lecture notes
    AVLVertex leftRotate(AVLVertex node) { // P
        if (node != null) {
            AVLVertex rightChild = node.right; // Q
            AVLVertex rightLeftChild = rightChild.left; // B

            // set Parents
            rightChild.parent = node.parent;
            if (node.parent == null) {
                this.root = rightChild;
            }
            node.parent = rightChild;

            // Rotation
            node.right = rightLeftChild;
            if (rightLeftChild != null) {
                rightLeftChild.parent = node;
            }
            rightChild.left = node;

            // Change Height change this
            node.height = max(heightCheck(node.left), heightCheck(node.right)) + 1;
            rightChild.height = max(heightCheck(rightChild.left), heightCheck(rightChild.right)) + 1;

            return rightChild;
        }
        return node;
    }

    // frm lecture notes
    AVLVertex rightRotate(AVLVertex node) { // Node is Q -- using lecture notes as reference pg 31 of L11
        // left Child becomes new vertex
        if (node != null) {
            AVLVertex leftChild = node.left; // P
            AVLVertex leftRightChild = leftChild.right; // B

            // set Parent
            leftChild.parent = node.parent;
            if (node.parent == null) {
                this.root = leftChild;
            }
            node.parent = leftChild;

            // Rotation
            node.left = leftRightChild; // Q left points to B
            if (leftRightChild != null) {
                leftRightChild.parent = node;
            }
            leftChild.right = node; // P right points to Q;

            // Change height of both left child and node
            node.height = max(heightCheck(node.left), heightCheck(node.right)) + 1;
            leftChild.height = max(heightCheck(leftChild.left), heightCheck(leftChild.right)) + 1;

            return leftChild; // returns this because helper recursive function will auto set its parent

        }
        return node;
    }

    // Rotation function making use of left and right rotate ^^
    AVLVertex rotateFunction(AVLVertex vertex) {
        if (bf(vertex) == 2) {
            if (bf(vertex.left) == -1) {
                vertex.left = leftRotate(vertex.left);
            }
            vertex = rightRotate(vertex); // set vertex to be our rotation so our insert function doesnt bug out
        } else if (bf(vertex) == -2) {
            if (bf(vertex.right) == 1) {
                vertex.right = rightRotate(vertex.right);
            }
            vertex = leftRotate(vertex);
        }
        return vertex;
    }

    // overloading insert function
    void insert(String name) { // insert at the root
        this.root = insert(name, this.root);
    }

    AVLVertex insert(String name, AVLVertex vertex) {
        if (vertex == null) {
            return (new AVLVertex(name)); // implies that the child is null --> so we change that null into a node

        }
        // do height change.
        if (name.compareTo(vertex.name) < 0) { //
            // Left Insertion
            vertex.left = insert(name, vertex.left);
            vertex.left.parent = vertex;
        } else {
            // Right Rotation
            vertex.right = insert(name, vertex.right);
            vertex.right.parent = vertex;
        }
        // Height Update
        vertex.height = max(heightCheck(vertex.left), heightCheck(vertex.right)) + 1;

        return rotateFunction(vertex); // Rotates this vertex before going back to its parent vertex to check for
                                       // rotation again
    }

}

/*
public class nicknames {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        // AVLTree avl = new AVLTree();
        AVLTree[] arr = new AVLTree[26]; // Direct Addressing Table
        int A = Integer.parseInt(br.readLine());
        for (int i = 0; i < A; i++) {
            String name = br.readLine();
            int firstCharIDX = name.charAt(0) - 97; // ASCII
            if (arr[firstCharIDX] == null) {
                AVLTree avl = new AVLTree();
                avl.insert(name);
                arr[firstCharIDX] = avl;
            } else {
                arr[firstCharIDX].insert(name);
            }
        }
        int B = Integer.parseInt(br.readLine());
        for (int j = 0; j < B; j++) {
            String nickname = br.readLine();
            int firstCharIDX = nickname.charAt(0) - 97;
            if (arr[firstCharIDX] != null) {
                pw.println(arr[firstCharIDX].search(nickname, arr[firstCharIDX].root));
            } else {
                pw.println(0);
            }
            // pw.println(avl.search(nickname, avl.root));
        }
        pw.close();
    }

    int search(String nickname, AVLVertex vertex) {
        if (vertex == null) {
            return 0;
        }
        int count = 0;
        int lengthofnickname = nickname.length();
        String sliced = vertex.name.substring(0, lengthofnickname);
        if (nickname.compareTo(sliced) == 0) { // if same -- we check both left and right subtree
            count++;
            if (vertex.left == null && vertex.right != null) {
                int y = search(nickname, vertex.right);
                count += y;
            } else if (vertex.right == null && vertex.left != null) {
                int x = search(nickname, vertex.left);
                count += x;
            } else {
                int x = search(nickname, vertex.right);
                int y = search(nickname, vertex.left);
                count = count + x + y;
            }

        } else if (nickname.compareTo(sliced) < 0) { // so my nicknam
            if (vertex.left != null) {
                int x = search(nickname, vertex.left);
                count += x;
            }

        } else {
            if (vertex.right != null) {
                int x = search(nickname, vertex.right);
                count += x;
            }

        }


        return count;
    }
}
*/


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

/*
 * Check which vertices its no longer the same -- then root size - that
 * 
 * Use a hashmap or hashtable for repeated queries
 * Find start and end point and just minus instead of searching all nodes.
 */


public class nicknames {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        // AVLTree avl = new AVLTree();
        AVLTree[] arr = new AVLTree[26]; // Direct Addressing Table for first nickname;
        int A = Integer.parseInt(br.readLine());
        HashMap<String,Integer> map = new HashMap<>(); //To store my queries


        //AVL Tree Insertion
        //AVL Tree storing at array using first character
        for (int i = 0; i < A; i++) {
            String name = br.readLine();
            int firstCharIDX = name.charAt(0) - 97; // ASCII
            if (arr[firstCharIDX] == null) {
                AVLTree avl = new AVLTree();
                avl.insert(name);
                arr[firstCharIDX] = avl;
            } else {
                arr[firstCharIDX].insert(name);
            }
        }
        //
        //AVL Tree Search
        int B = Integer.parseInt(br.readLine());
        for (int j = 0; j < B; j++) {
            String nickname = br.readLine();
            int firstCharIDX = nickname.charAt(0) - 97;
            if (arr[firstCharIDX] == null){ //Directly checks DAT
                pw.println(0);
                continue;
            }
            if (nickname.length() == 1){ //checks size directly without looping through as its the whole tree if length == 1
                pw.println(arr[firstCharIDX].root.size);
                continue;    
            }
            else{ //if not null and nickname.length > 1 //Checks AVL Tree then puts it into a map.
                AVLVertex lca = arr[firstCharIDX].lca(nickname, arr[firstCharIDX].root); //lowest common ancestor
                pw.println(arr[firstCharIDX].testSearch(nickname, lca));
                if (map.get(nickname) == null){
                    int x = arr[firstCharIDX].search(nickname, arr[firstCharIDX].root);
                    map.put(nickname, x);
                    //pw.println(x);
                }
                else{
                    //pw.println(map.get(nickname));
                }
            }
        }
    pw.close();
    }
}

class AVLVertex {

    String name;
    int height; // To keep track of balance factor
    int size; // Keeps track of the size of our 'root'

    AVLVertex parent;
    AVLVertex left;
    AVLVertex right;
    

    AVLVertex(String name) {
        this.height = 0;
        this.size = 1;
        //
        this.name = name;
        //
        this.parent = null;
        this.left = null;
        this.right = null;

    }

}

// Sort out our AVL tree first
class AVLTree {

    AVLVertex root;

    AVLTree() { // Initialization results in empty AVLTree
        this.root = null;
    }

    //Check left and right vertices -- if right
    // int search(String nickname, AVLVertex vertex){
    //     if (vertex == null){
    //         return 0;
    //     }
    //     int count = 0;
    //     int lengthofnickname = nickname.length();
    //     String slicedvertex = vertex.name.substring(0, lengthofnickname); //my sliced name for avl tree
    //     int compareTo = nickname.compareTo(slicedvertex);
    //     if (compareTo == 0){ //does search until we reach a subtree
    //         count += vertex.size;
    //         return count; //return size of my vertex (subtrees)
    //     }
    //     else if (compareTo == 1){ // search right subtree
    //         int x = search(nickname, vertex.right);
    //         count += x;
    //         //return x;
    //     }
    //     else{ //search left subtree
    //         int x = search(nickname, vertex.left);
    //         count += x;
    //         //return x;
    //     }
    //     //count += x;
    //     return count;

    // }

    //lowest common ancestor
    AVLVertex lca (String nickname, AVLVertex vertex){
        int lengthofnickname = nickname.length();
        String sliced = vertex.name.substring(0,lengthofnickname);
        if (nickname.compareTo(sliced) == 0){
            return vertex;
        }
        else if (nickname.compareTo(sliced) == -1){
            vertex = lca(nickname, vertex.left);
        }else{
            vertex = lca(nickname, vertex.right);
        }
        return vertex;
    }
    //find start annd end pint
    //use lowest common ancestor
    int testSearch(String nickname, AVLVertex vertex){
        if (vertex == null){ //null vertex condition
            return 0;
        }
        int lengthofnickname = nickname.length();
        String sliced = vertex.name.substring(0, lengthofnickname);
        //
        int count = 0;
        if (nickname.compareTo(sliced) == 0){
            //int sizeofVertex = vertex.size; //Vertex that it is equal substring == start point
            count = 1 + testSearch(nickname, vertex.left) + testSearch(nickname, vertex.right);
            return count;
        }if (nickname.compareTo(sliced) == -1 || nickname.compareTo(sliced) == 1);{
            if (nickname.compareTo(sliced))
            return 0;
        }
        
    }
    //
    int search(String nickname, AVLVertex vertex) {
        if (vertex == null) {
            return 0;
        }
        int count = 0;
        int lengthofnickname = nickname.length();
        String sliced = vertex.name.substring(0, lengthofnickname);
        if (nickname.compareTo(sliced) == 0) { // if same -- we check both left and right subtree
            count++;
            if (vertex.left == null && vertex.right != null) {
                int y = search(nickname, vertex.right);
                count += y;
            } else if (vertex.right == null && vertex.left != null) {
                int x = search(nickname, vertex.left);
                count += x;
            } else {
                int x = search(nickname, vertex.right);
                int y = search(nickname, vertex.left);
                count = count + x + y;
            }

        } else if (nickname.compareTo(sliced) < 0) { // so my nicknam
            if (vertex.left != null) {
                int x = search(nickname, vertex.left);
                count += x;
            }

        } else {
            if (vertex.right != null) {
                int x = search(nickname, vertex.right);
                count += x;
            }

        }
        return count;
    }

    int max(int left, int right) { // Custom
        if (left > right) {
            return left;
        } else {
            return right;
        }
    }

    // not same as height
    int bf(AVLVertex vertex) { // returns balance factor
        if (vertex == null) { // left - right height == 0 since null
            return 0;
        } else {
            return (heightCheck(vertex.left) - heightCheck(vertex.right));
        }
    }

    // check height of vertex
    int heightCheck(AVLVertex vertex) { //
        if (vertex == null) {
            return -1;
        } else {
            return vertex.height;
        }
    }

    int sizeCheck(AVLVertex vertex){
        if (vertex == null){
            return 0;
        }
        else{
            return sizeCheck(vertex.left) + sizeCheck(vertex.right) + 1;
        }
    }

    // from lecture notes
    AVLVertex leftRotate(AVLVertex node) { // P
        if (node != null) {
            AVLVertex rightChild = node.right; // Q
            AVLVertex rightLeftChild = rightChild.left; // B

            // set Parents
            rightChild.parent = node.parent;
            if (node.parent == null) {
                this.root = rightChild;
            }
            node.parent = rightChild;

            // Rotation
            node.right = rightLeftChild;
            if (rightLeftChild != null) {
                rightLeftChild.parent = node;
            }
            rightChild.left = node;

            // Change Height change this
            node.height = max(heightCheck(node.left), heightCheck(node.right)) + 1;
            node.size = sizeCheck(node);
            rightChild.height = max(heightCheck(rightChild.left), heightCheck(rightChild.right)) + 1;
            rightChild.size = sizeCheck(rightChild);

            return rightChild;
        }
        return node;
    }

    // frm lecture notes
    AVLVertex rightRotate(AVLVertex node) { // Node is Q -- using lecture notes as reference pg 31 of L11
        // left Child becomes new vertex
        if (node != null) {
            AVLVertex leftChild = node.left; // P
            AVLVertex leftRightChild = leftChild.right; // B

            // set Parent
            leftChild.parent = node.parent;
            if (node.parent == null) {
                this.root = leftChild;
            }
            node.parent = leftChild;

            // Rotation
            node.left = leftRightChild; // Q left points to B
            if (leftRightChild != null) {
                leftRightChild.parent = node;
            }
            leftChild.right = node; // P right points to Q;

            // Change height of both left child and node
            node.height = max(heightCheck(node.left), heightCheck(node.right)) + 1;
            node.size = sizeCheck(node);
            leftChild.height = max(heightCheck(leftChild.left), heightCheck(leftChild.right)) + 1;
            leftChild.size = sizeCheck(leftChild);

            return leftChild; // returns this because helper recursive function will auto set its parent

        }
        return node;
    }

    // Rotation function making use of left and right rotate ^^
    AVLVertex rotateFunction(AVLVertex vertex) {
        if (bf(vertex) == 2) {
            if (bf(vertex.left) == -1) {
                vertex.left = leftRotate(vertex.left);
            }
            vertex = rightRotate(vertex); // set vertex to be our rotation so our insert function doesnt bug out
        } else if (bf(vertex) == -2) {
            if (bf(vertex.right) == 1) {
                vertex.right = rightRotate(vertex.right);
            }
            vertex = leftRotate(vertex);
        }
        return vertex;
    }

    // overloading insert function
    void insert(String name) { // insert at the root
        this.root = insert(name, this.root);
    }

    AVLVertex insert(String name, AVLVertex vertex) {
        if (vertex == null) {
            return (new AVLVertex(name)); // implies that the child is null --> so we change that null into a node

        }
        //
        if (name.compareTo(vertex.name) < 0) { //
            // Left Insertion
            vertex.left = insert(name, vertex.left);
            vertex.left.parent = vertex;
        } else {
            // Right Insertion
            vertex.right = insert(name, vertex.right);
            vertex.right.parent = vertex;
        }
        // Height Update
        vertex.height = max(heightCheck(vertex.left), heightCheck(vertex.right)) + 1;
        vertex.size = sizeCheck(vertex);
        return rotateFunction(vertex); // Rotates this vertex before going back to its parent vertex to check for
                                       // rotation again
    }

}

/*
public class nicknames {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        // AVLTree avl = new AVLTree();
        AVLTree[] arr = new AVLTree[26]; // Direct Addressing Table
        int A = Integer.parseInt(br.readLine());
        for (int i = 0; i < A; i++) {
            String name = br.readLine();
            int firstCharIDX = name.charAt(0) - 97; // ASCII
            if (arr[firstCharIDX] == null) {
                AVLTree avl = new AVLTree();
                avl.insert(name);
                arr[firstCharIDX] = avl;
            } else {
                arr[firstCharIDX].insert(name);
            }
        }
        int B = Integer.parseInt(br.readLine());
        for (int j = 0; j < B; j++) {
            String nickname = br.readLine();
            int firstCharIDX = nickname.charAt(0) - 97;
            if (arr[firstCharIDX] != null) {
                pw.println(arr[firstCharIDX].search(nickname, arr[firstCharIDX].root));
            } else {
                pw.println(0);
            }
            // pw.println(avl.search(nickname, avl.root));
        }
        pw.close();
    }

    int search(String nickname, AVLVertex vertex) {
        if (vertex == null) {
            return 0;
        }
        int count = 0;
        int lengthofnickname = nickname.length();
        String sliced = vertex.name.substring(0, lengthofnickname);
        if (nickname.compareTo(sliced) == 0) { // if same -- we check both left and right subtree
            count++;
            if (vertex.left == null && vertex.right != null) {
                int y = search(nickname, vertex.right);
                count += y;
            } else if (vertex.right == null && vertex.left != null) {
                int x = search(nickname, vertex.left);
                count += x;
            } else {
                int x = search(nickname, vertex.right);
                int y = search(nickname, vertex.left);
                count = count + x + y;
            }

        } else if (nickname.compareTo(sliced) < 0) { // so my nicknam
            if (vertex.left != null) {
                int x = search(nickname, vertex.left);
                count += x;
            }

        } else {
            if (vertex.right != null) {
                int x = search(nickname, vertex.right);
                count += x;
            }

        }
        return count;
    }
}
*/
