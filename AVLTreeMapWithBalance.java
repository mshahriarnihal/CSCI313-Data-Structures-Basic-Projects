
/* Student's Name: Mubasshir Al Shahriar, CUNYFirst ID: 24398818
   Submitting To : Dr. Alla Rozovskaya
   Course Name   : CSCI 313: Data Structures (Mon-Wednesday 3:10 p.m)

   Assignment #3, Problem #2: In the AVL implementation in the chapter, each node stores the height of its subtree, which is an arbitrarily large integer. The space usage for an AVL tree can be reduced by instead storing the balance factor of a node, which is defined as the height of its left subtree minus the height of its right subtree. Thus, the balance factor of a node is always equal to 1, 0, or 1, except during an insertion or removal, when it may become temporarily equal to 2 or +2. Reimplement the AVLTreeMap class storing balance factors rather than subtree heights. */





import java.util.*;

public class AVLTreeMapWithBalance 
{
    
    static class Node                // Since we will need to create node in our AVL tree.
    {
        int key, value;
        int balance;                 // Balance factor
        Node left, right, parent;

        Node(int key, int value, Node parent) 
        {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }

    private Node root;              // Root node of the tree.


    /* This public method shall be used to insert a new key-value pair. */
    public void put(int key, int value) 
    {
        if (root == null) 
        {
            root = new Node(key, value, null);      /* It sets the first node as root node. */
        } 
        else 
        {
            insert(root, key, value);              /* If there already exists a root and the current one is not the first node, it inserts recursively. */
        }
    }

    /* This is recursive insert function with rebalancing (if necessary). */
    private void insert(Node node, int key, int value)    
    {
        if (key < node.key) 
        {
            if (node.left == null) 
            {
                node.left = new Node(key, value, node);
                rebalance(node);
            } 
            else 
            {
                insert(node.left, key, value);
            }
        } 
 
        else if (key > node.key)
        {
            if (node.right == null) 
            {
                node.right = new Node(key, value, node);
                rebalance(node);
            }
            else 
            {
                insert(node.right, key, value);
            }
        }
 
        else 
        {
            node.value = value;      /* If the key already exists, then it just updates the value. */
        }
    }

    /* This method removes a certain key. */
    public void remove(int key) 
    {
        delete(root, key);
    }

    /* This method deletes node recursively and rebalances if needed. */
    private void delete(Node node, int key) 
    {
        if (node == null) return;

        if (key < node.key) 
        {
            delete(node.left, key);
        }
 
        else if (key > node.key) 
        {
            delete(node.right, key);
        } 

        else 
        {
            if (node.left == null && node.right == null)        /* Checks if there is no children. */
            {
                replaceNodeInParent(node, null);
                rebalance(node.parent);
            }

            else if (node.left != null && node.right != null)   /* It checks if there are 2 child. */ 
            {
                Node successor = findMin(node.right);           /* Finds smallest element's node in the right subtree. */   
                node.key = successor.key;
                node.value = successor.value;
                delete(successor, successor.key);
            } 

            else                                                // When it is a case of only 1 child.
            {
                Node child = (node.left != null) ? node.left : node.right;
                replaceNodeInParent(node, child);
                rebalance(node.parent);
            }
        }
    }


    /* This method replaces one node in the tree with another one (used in deletion and rotation). */
    private void replaceNodeInParent(Node node, Node child) 
    {
        if (node.parent == null) 
        {
            root = child;
            if (child != null) child.parent = null;
        } 

        else if (node == node.parent.left) 
        {
            node.parent.left = child;
            if (child != null) child.parent = node.parent;
        }
 
        else 
        {
            node.parent.right = child;
            if (child != null) child.parent = node.parent;
        }
    }

    /* This method finds the node with the minimum/leftmost key. */
    private Node findMin(Node node) 
    {
        while (node.left != null) node = node.left;
        return node;
    }


    /* This method updates and rebalances the tree from this node upward. */
    private void rebalance(Node node) 
    {
        updateBalance(node);

        if (node.balance == -2) 
        {
            if (height(node.right.right) >= height(node.right.left)) 
            {
                rotateLeft(node);             // Single left rotation
            }

            else 
            {
                rotateRight(node.right);      // Double rotation (right-left)
                rotateLeft(node);
            }
        } 

        else if (node.balance == 2) 
        {
            if (height(node.left.left) >= height(node.left.right)) 
            {
                rotateRight(node);            // Single right rotation
            }
            else 
            {
                rotateLeft(node.left);
                rotateRight(node);           // Double left-right rotation.
            }
        }

        if (node.parent != null) 
        {
            rebalance(node.parent);         /* Keeps going upward and rebalances. */
        }
    }


    /* This method updates the balance factor of a node. */
    private void updateBalance(Node node) 
    {
        node.balance = height(node.left) - height(node.right);    /* As we learnt, as well as given in the question: "Balance Factor = height of its left subtree - the height of its right subtree." */
    }


    /* This method computes the height of a node recursively. */
    private int height(Node node) 
    {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /* This method performs a left rotation around a node. */
    private void rotateLeft(Node node)
    {
        Node right = node.right;
        replaceNodeInParent(node, right);    /* It moves right child up. */
        node.right = right.left;
        if (right.left != null) right.left.parent = node;
        right.left = node;
        node.parent = right;

        updateBalance(node);
        updateBalance(right);
    }

    /* This method performs a right rotation around a node. */
    private void rotateRight(Node node) 
    {
        Node left = node.left;
        replaceNodeInParent(node, left);      /* It moves left child up. */
        node.left = left.right;

        if (left.right != null) left.right.parent = node;

        left.right = node;
        node.parent = left;

        updateBalance(node);
        updateBalance(left);
    }


    /* Print method to show the correct output as a level by level tree structure. */
    public void printTree() 
    {
        if (root == null) 
        {
            System.out.println("(empty)");
            return;
        }

        List<List<String>> lines = new ArrayList<>();
        List<Node> level = new ArrayList<>();
        level.add(root);
        int nonNulls = 1;
        int widest = 0;

        while (nonNulls != 0) 
        {
            List<String> line = new ArrayList<>();
            List<Node> next = new ArrayList<>();
            nonNulls = 0;

            for (Node node : level) 
            {
                if (node == null) 
                {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                }

                else 
                {
                    String val = Integer.toString(node.key);
                    line.add(val);
                    if (val.length() > widest) widest = val.length();
                    next.add(node.left);
                    next.add(node.right);
                    if (node.left != null) nonNulls++;
                    if (node.right != null) nonNulls++;
                }
            }

            lines.add(line);
            level = next;
        }


        int perPiece = lines.get(lines.size() - 1).size() * (widest + 4);

        for (List<String> line : lines) 
        {
            int halfPiece = (int) Math.floor(perPiece / 2f) - 1;

            for (String val : line) 
            {
                if (val == null) val = "";
                int gap1 = (int) Math.ceil(perPiece / 2f - val.length() / 2f);
                int gap2 = (int) Math.floor(perPiece / 2f - val.length() / 2f);
                System.out.print(" ".repeat(gap1));
                System.out.print(val);
                System.out.print(" ".repeat(gap2));
            }

            System.out.println("\n\n");
            perPiece /= 2;
        }
    }


    /* Main method for this program. */
    public static void main(String[] args) 
    {
        AVLTreeMapWithBalance tree = new AVLTreeMapWithBalance();

        /* Inserting some nodes as hardcoded. Here I am using the same nodes we saw in the lecture slides' example, so that we can confirm it is showing correct output. */
        int[] keys = {44, 17, 62, 32, 50, 78, 48, 54, 88};     

        for (int key : keys) 
        {
            tree.put(key, key * 10);
        }

        System.out.println("Tree After Initial Insertion:");
        tree.printTree();

        System.out.println("\nDeleting 32: ");
        tree.remove(32);

        System.out.println("Tree After Deletion:");
        tree.printTree();
    }
}
