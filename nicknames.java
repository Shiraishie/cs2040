import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

class nicknames {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        // Data Structures
        AVLTree avl = new AVLTree();
        HashMap<String, Integer> hashmap = new HashMap<String, Integer>(); // repeated queries
        //
        int A = Integer.parseInt(br.readLine()); // Number of names
        for (int i = 0; i < A; i++) {
            String name = br.readLine();
            avl.insert(name);
        }
        int B = Integer.parseInt(br.readLine()); // Number of nicknames to query
        for (int i = 0; i < B; i++) {
            String nickname = br.readLine();
            if (!hashmap.containsKey(nickname)) {
                int count = avl.search(nickname);
                hashmap.put(nickname, count);
            } // repeated queries
            pw.println(hashmap.get(nickname));
        }

        pw.close();
    }
}

class Node {
    // int properties
    int height; // num of max edges
    int size; // num of nodes
    //
    Node parent;
    Node left;
    Node right;
    //
    String key;

    Node(String key) {
        this.height = 0;
        this.size = 1;
        this.parent = this.left = this.right = null;
        this.key = key;
    }

}

class AVLTree {
    Node root;

    // Initialization
    AVLTree() {
        this.root = null;
    }
    //

    int getRootSize() {
        if (this.root == null) {
            return 0;
        }
        return getSize(this.root);
    }

    int getSize(Node node) {
        if (node == null) {
            return 0;
        } else {
            return node.size;
        }
    }

    int getHeight(Node node) {
        if (node == null) {
            return -1;
        } else {
            return node.height;
        }
    }

    int balanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return (getHeight(node.left) - getHeight(node.right));
    }

    int updateHeight(Node node) { // check
        if (node == null) {
            return 0;
        }
        return (1 + max(getHeight(node.left), getHeight(node.right)));
    }

    int updateSize(Node node) {
        if (node == null) {
            return 0;
        }
        return (1 + getSize(node.left) + getSize(node.right));
    }

    int max(int a, int b) {
        if (a >= b) {
            return a;
        } else {
            return b;
        }
    }

    Node leftRotate(Node T) {
        if (T.right != null) {
            Node w = T.right;
            w.parent = T.parent; // w = Q and T = P;
            T.parent = w;

            // Update Parent pointers
            if (w.parent == null) { // If new root has a null parent, we set it as the root
                this.root = w;
            } else if (w.parent.left == T) { // Parent pointer
                w.parent.left = w;
            } else if (w.parent.right == T) {
                w.parent.right = w;
            }
            //
            if (w.left != null) {
                w.left.parent = T;
            }
            T.right = w.left;
            w.left = T;
            // Property updating
            T.height = updateHeight(T);
            T.size = updateSize(T);
            w.height = updateHeight(w);
            w.size = updateSize(w);
            return w; // new root node

        }
        return T; // if null, return T because no rotation can be done
    }

    Node rightRotate(Node T) {
        if (T.left != null) {
            Node w = T.left;
            w.parent = T.parent;
            if (w.parent == null) {
                this.root = w;
            } else if (w.parent.left == T) {
                w.parent.left = w;
            } else if (w.parent.right == T) {
                w.parent.right = w;
            }
            if (w.right != null) {
                w.right.parent = T;
            }
            T.left = w.right;
            w.right = T;
            //
            T.height = updateHeight(T);
            T.size = updateSize(T);
            w.height = updateHeight(w);
            w.size = updateSize(w);
            return w; // new root
        }
        return T;
    }

    Node rebalance(Node node) {
        int bf = balanceFactor(node);
        if (bf == 2) { // Left Heavy
            int bfChild = balanceFactor(node.left);
            if (bfChild == -1) {
                // Left-Right Rotation
                node.left = leftRotate(node.left);
            }
            node = rightRotate(node);
            // Rotate already updates our properties

        }

        else if (bf == -2) { // Right Heavy
            int bfChild = balanceFactor(node.right);
            if (bfChild == 1) {
                node.right = rightRotate(node.right); // don't do rightRotate(node.right) directly because it returns a
                                                      // node;
            }
            node = leftRotate(node);
        }
        // if balance factor is -1,0,1, does nothing
        return node;
    }

    void insert(String nickname) {
        this.root = insert(nickname, this.root);
    }

    Node insert(String nickname, Node node) {
        if (node == null) { // Base Case
            return (new Node(nickname));
        }
        if (nickname.compareTo(node.key) < 0) { // nickname < key
            node.left = insert(nickname, node.left);
            node.left.parent = node;
        } else {
            node.right = insert(nickname, node.right);
            node.right.parent = node;
        }
        // Recursively updates height & size of our node before we do balancing
        node.height = updateHeight(node);
        node.size = updateSize(node);
        return rebalance(node);
    }

    Node lowestcommonancestor(String nickname, Node node) { // finds first lowest common ancestor which is the subtree
                                                            // to search
        if (node == null) { // Null case;
            return null;
        }
        String key = node.key;
        boolean checkPrefix = key.startsWith(nickname);
        if (checkPrefix) { // If node is LCA, return node
            return node;
        } else if (nickname.compareTo(key) < 0) { // nickname is to the left
            return lowestcommonancestor(nickname, node.left);
        } else {
            return lowestcommonancestor(nickname, node.right);
        }
    }

    int search(String nickname) {
        Node lca = lowestcommonancestor(nickname, this.root);
        if (lca == null) { // No lowest common ancestor
            return 0;
        } else { // Have lowest common ancestor
            return 1 + leftQuery(nickname, lca.left) + rightQuery(nickname, lca.right); // Start query on our subtree;
        }

    }

    int leftQuery(String nickname, Node leftNode) { // In
        if (leftNode == null) {
            return 0;
        } else {
            boolean checkPrefix = leftNode.key.startsWith(nickname);
            if (checkPrefix) { // If it descends to the left --> then leftNode <= leftNode. right Tree are
                               // nodes that start with nickname <= initialNode
                return 1 + getSize(leftNode.right) + leftQuery(nickname, leftNode.left); // leftNode + size of Right
                                                                                         // subtree of leftNode + query
                                                                                         // the left
            } else {
                return leftQuery(nickname, leftNode.right);
            }
        }
    }

    int rightQuery(String nickname, Node rightNode) {
        if (rightNode == null) {
            return 0;
        } else {
            boolean checkPrefix = rightNode.key.startsWith(nickname);
            if (checkPrefix) {
                return 1 + getSize(rightNode.left) + rightQuery(nickname, rightNode.right);
            } else {
                return rightQuery(nickname, rightNode.left);
            }
        }
    }
}
