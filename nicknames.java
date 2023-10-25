public class nicknames {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        while (io.hasMoreTokens()) {

        }
        io.close();
    }
}

class AVLVertex {

    String name;

    AVLVertex left;
    AVLVertex right;
    AVLVertex parent;

    int height; // To keep track of balance factor

    AVLVertex(String name) {
        this.height = 1;
        this.name = name;
    }

}

// Sort out our AVL tree first
class AVLTree {

    AVLVertex root;

    AVLTree() { // Initialization results in empty AVLTree
        this.root = null;
    }

    int height(AVLVertex node) {
        return node.height;
    }

    AVLVertex leftRotate(AVLVertex node) {
        AVLVertex nodeparent = node.parent;
        AVLVertex rightChild = node.right;
        AVLVertex leftofRightChild = rightChild.left;

        // set Parent
        rightChild.parent = nodeparent;
        node.parent = rightChild;

        // Rotation
        node.right = leftofRightChild;
        rightChild.left = node;

    }

    AVLVertex rightRotate(AVLVertex node) { // Node is Q -- using lecture notes as reference pg 31 of L11
        AVLVertex nodeparent = node.parent;
        AVLVertex leftChild = node.left; // P
        AVLVertex rightofLeftChild = leftChild.right; // B

        // set Parent
        leftChild.parent = nodeparent;
        node.parent = leftChild;

        // Rotation
        node.left = rightofLeftChild; // Q left points to B
        leftChild.right = node; // P right points to Q;

        // Change height

    }

}


class Kattio extends PrintWriter {
    public Kattio(InputStream i) {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public Kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
        return peekToken() != null;
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    public double getDouble() {
        return Double.parseDouble(nextToken());
    }

    public long getLong() {
        return Long.parseLong(nextToken());
    }

    public String getWord() {
        return nextToken();
    }

    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null)
                        return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) {
            }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}
