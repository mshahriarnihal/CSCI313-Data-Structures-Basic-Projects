/* Student's Name: Mubasshir Al Shahriar, CUNYFirst ID: 24398818
   Submitting To : Dr. Alla Rozovskaya
   Course Name   : CSCI 313: Data Structures (Mon-Wednesday 3:10 p.m)

   Assignment #2, Problem #3: Implement a skip list class. Save it in file skipList.java. Use randomization to promote nodes to higher levels. Implement the following methods: skipInsert(k,v) – to insert in a skip list entry with key k and value v; skipRemove(k) – to remove position corresponding to key k from all levels in the skipList; skipSearch(k) - to search for key k in the skip list (returns position corresponding to key k in the bottom list, or null if k is not found). The java file should include the main method that creates a skip list from the hardcoded sequence of 10 integers and prints out the resulting list to stdout. */





import java.util.*;

public class skipList 
{

    /* Creating this node class to store key and value. Next, prev, above, below - these 4 nodes will be used to create levels and towers for our skip list. */
    private static class Node 
    {
        int key;
        String value;
        Node next, prev, above, below;

        Node(int key, String value) 
        {
            this.key = key;
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int height = 0;
    private Random rand = new Random();    /* Using this random number generator for the coin flip logic to decide which nodes will exist on next level. */


    /* This constructor creates the bottom level (S0) of our skip list. We can use head and tail here as sentinels (like negative and positive infinity sentinels we saw in the lecture slides). */
    public skipList() 
    {
        head = new Node(Integer.MIN_VALUE, null);
        tail = new Node(Integer.MAX_VALUE, null);
        head.next = tail;
        tail.prev = head;
    }

    // Coin flip method
    private boolean coinFlip() 
    {
        return rand.nextBoolean();
    }


    /* This method searches and returns the base level node with key <= k. */
    private Node skipSearch(int key) 
    {
        Node curr = head;     /* Setting current node as head so that it starts at the top-left of the list. */
        while (true) 
        {

            while (curr.next.key <= key)   /* While loop allows it to keep going towards right until the next key is larger than the one we need. */
            {
                curr = curr.next;
            }

            if (curr.below != null)    /* Moves to the next level of the same tower until it reaches the base level (S0). */
            {
                curr = curr.below;
            } 
            else 
            {
                break;
            }
        }
        return curr.key == key ? curr : null;
    }

    /* We will need to use this method in our in main function to print the result of the search in output and returns position corresponding to key k in the bottom list, or null if k is not found. I am using the similar logic of skipSearch method for this one. */
    public Node skipSearchPrint(int key) 
    {
        Node curr = head;
        while (true) 
        {
            while (curr.next.key <= key) 
            {
                curr = curr.next;
            }

            if (curr.below != null) 
            {
                curr = curr.below;
            } 
            else 
            {
                break;
            }
        }
        return curr.key == key ? curr : null;
    }


    // This method can be used to insert a new key-value pair.
    public void skipInsert(int key, String value) 
    {
        Stack<Node> path = new Stack<>();    /* This variable "path" will keep track of the insert positions at each level. */
        Node curr = head;

        /* First, it searches and finds the position where the new pair needs to be inserted. */
        while (curr != null) 
        {
            while (curr.next.key < key) 
            {
                curr = curr.next;
            }

            path.push(curr);
            curr = curr.below;
        }

        /* After finding the correct position, it inserts at bottom. */
        Node belowNode = null;
        boolean insertUp = true;
        int level = 0;
        
        while (insertUp && !path.isEmpty()) 
        {
            curr = path.pop();
            Node newNode = new Node(key, value);
            newNode.prev = curr;
            newNode.next = curr.next;
            curr.next.prev = newNode;
            curr.next = newNode;
            newNode.below = belowNode;
            if (belowNode != null) belowNode.above = newNode;
            belowNode = newNode;
            insertUp = coinFlip();
            level++;
        }

        
        if (insertUp)     /* This adds new level if needed. If the coin flips (through rand function) more times than the number of levels we have, then it create a new top level, and adds sentinels. */
        {
            height++;
            Node newHead = new Node(Integer.MIN_VALUE, null);
            Node newTail = new Node(Integer.MAX_VALUE, null);
            newHead.next = newTail;
            newTail.prev = newHead;
            newHead.below = head;
            head.above = newHead;
            newTail.below = tail;
            tail.above = newTail;
            head = newHead;
            tail = newTail;

            Node newNode = new Node(key, value);
            newNode.prev = head;
            newNode.next = tail;
            head.next = newNode;
            tail.prev = newNode;
            newNode.below = belowNode;
            belowNode.above = newNode;
        }
    }

    // This method removes all levels of key
    public void skipRemove(int key) 
    {
        Node node = skipSearch(key);    /* First I am using skipSearch() method to find the base-level node for key k. */
        while (node != null)            /* Then, it traverses upward and removes each level of the tower. */
        {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node = node.above;
        }
    }


    // This method prints the skip list.
    public void printList() 
    {
    System.out.println("Full Skip List:");

    /* First, it goes down to the base level (S0) of the list. */
    Node temp = head;
    while (temp.below != null) 
    {
        temp = temp.below;
    }

    /* Then it builds base level keys list which I will use as reference later to determine if an element was eliminated in next level and I should put "--" in place of that eliminated element. */
    List<Integer> baseKeys = new ArrayList<>();
    Node currBase = temp;
    while (currBase != null) 
    {
        baseKeys.add(currBase.key);
        currBase = currBase.next;
    }


   /* Next, it prints each level */
    int extendedHeight = height + 1;
    System.out.print("S" + extendedHeight + ": ");
    for (int i = 0; i < baseKeys.size(); i++) 
    {
        if (i == 0) 
        {
            System.out.print("[-inf]");
        } 
        else if (i == baseKeys.size() - 1) 
        {
            System.out.print("[+inf]");
        } 
        else 
        {
            System.out.print("[--]");
        }

        if (i != baseKeys.size() - 1) 
        {
            System.out.print(" --- ");
        }
    }
    System.out.println();

    Node levelStart = head;
    int level = height;
    while (levelStart != null) 
    {
        System.out.print("S" + level + ": ");

        Node curr = levelStart;
        int b = 0;

        while (b < baseKeys.size()) 
        {
            int currentKey = baseKeys.get(b);

            if (curr != null && curr.key == currentKey) 
            {
                // Key matches: print the real key
                String display = (curr.key == Integer.MIN_VALUE) ? "-inf" :
                                 (curr.key == Integer.MAX_VALUE) ? "+inf" :
                                 Integer.toString(curr.key);

                System.out.print("[" + display + "]");
                curr = curr.next; // Moving to the next node (rightward) at this same level
            } 
            else 
            {
                /* If no matching node found at this level (meaning the element was eliminated by the rand function), it prints [--] so that all present elements stays in correct places. */
                System.out.print("[--]");
            }

            // After each box, it prints connectors
            if (b != baseKeys.size() - 1) 
            {
                System.out.print(" --- ");
            }

            b++;
        }

        System.out.println(); 
        levelStart = levelStart.below; // Moving to the next (lower) level
        level--;
    }
}


    /* Main method of the program with hardcoded sequence of 10 arbitrary integers. */
    public static void main(String[] args) 
    {
        skipList sl = new skipList();    // Creating a new skip list.
        int[] keys = {35, 23, 17, 48, 15, 89, 41, 60, 28, 33};
        for (int k : keys) 
        {
            sl.skipInsert(k, "V" + k);
        }

        System.out.println("Initial Skip List with Hard-coded Key-Value Pairs:");
        sl.printList();

        System.out.println("\nInserting and Printing a New Key-Value Pair (21, V21):");
        sl.skipInsert(21, "V21");
        sl.printList();

        System.out.println("\nSearching For Key 28:");
        Node result = sl.skipSearchPrint(28);
        System.out.println(result != null ? "Found at Base Level: (" + result.key + "," + result.value + ")" : "Not found.");

        System.out.println("\nRemoving Key 28 and Printing Again:");
        sl.skipRemove(28);
        sl.printList();
    }
}
