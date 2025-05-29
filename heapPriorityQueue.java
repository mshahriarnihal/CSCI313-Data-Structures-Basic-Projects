

/*   Author's Name: Mubasshir Al Shahriar
Relevant Course   : CSCI 313: Data Structures (Mon-Wednesday 3:10 p.m) */




import java.util.*;

/* Creating This a generic class named "heapPriorityQueue" just like we saw in page 377 of the book. */
public class heapPriorityQueue<K, V> 
{

    protected static class PQEntry<K, V> implements Map.Entry<K, V> 
    {
        private K k;
        private V v;

        // Constructor
        public PQEntry(K key, V value) 
        {
            k = key;
            v = value;
        }

        
        public K getKey() { return k; }
        public V getValue() { return v; }
        public V setValue(V value) 
        {
            V old = v;
            v = value;
            return old;
        }


        protected void setKey(K key) { k = key; }
        protected void setValueInternal(V value) { v = value; }
    }

    
    /* This can compare two keys. We can use this to find who has higher priority. */
    private Comparator<K> comp;
    protected ArrayList<PQEntry<K, V>> heap = new ArrayList<>();

    /* This is a constructor which can be used to create a heapPriorityQueue without passing anything. It uses a default way to compare two keys. */
    public heapPriorityQueue() 
    {
        this(new DefaultComparator<K>());
    }

    /* This is another essential constructor for our class which can be used to pass our own custom comparison method if needed. */
    public heapPriorityQueue(Comparator<K> c) 
    {
        comp = c;
    }

    /* As we found on page 377 of the book, the following methods let us convert an index in the array into a position in a tree. */
    protected int parent(int j) { return (j - 1) / 2; }
    protected int left(int j) { return 2 * j + 1; }
    protected int right(int j) { return 2 * j + 2; }

    protected boolean hasLeft(int j) { return left(j) < heap.size(); }      /* These makes sure the node at index j actually has children. We need it to avoid error of going out of bounds. */
    protected boolean hasRight(int j) { return right(j) < heap.size(); }


    /*  This method swaps two entries in the heap array */
    protected void swap(int i, int j) {
        PQEntry<K, V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }


    // Reimplementing upheap method using recursion
    protected void upheap(int j) {
        if (j == 0) return; // Base case.
        int p = parent(j);
        if (comp.compare(heap.get(j).getKey(), heap.get(p).getKey()) < 0) {
            swap(j, p); /* Compares it with its parent. If it is smaller, then swaps and calls recursive function. */
            upheap(p);  // Recursive case.
        }
    }


    // Reimplementing downheap method using recursion
    protected void downheap(int j) 
    {
        if (!hasLeft(j)) return; // Base case: This means it does not have any children.

        int smallChildIndex = left(j);
        if (hasRight(j)) 
        {
            int rightIndex = right(j);
            if (comp.compare(heap.get(rightIndex).getKey(), heap.get(smallChildIndex).getKey()) < 0)
                smallChildIndex = rightIndex;
        }

        if (comp.compare(heap.get(smallChildIndex).getKey(), heap.get(j).getKey()) < 0) 
        {
            swap(j, smallChildIndex);
            downheap(smallChildIndex);  // Recursive case.
        }
    }

    public int size() { return heap.size(); }
    public boolean isEmpty() { return heap.isEmpty(); }

    /* This insert method adds new elements to the end and then calls upheap() to restore heap structure. */
    public void insert(K key, V value) 
    {
        PQEntry<K, V> newest = new PQEntry<>(key, value);
        heap.add(newest);
        upheap(heap.size() - 1);
    }

    public PQEntry<K, V> min() 
    {
        if (heap.isEmpty()) return null;
        return heap.get(0);
    }


    /* We will need this removeMin method to remove and display in output how the program actually works as this method removes and returns an entry with minimal key (if any). */
    public PQEntry<K, V> removeMin() 
    {
        if (heap.isEmpty()) return null;
        PQEntry<K, V> answer = heap.get(0);
        swap(0, heap.size() - 1);
        heap.remove(heap.size() - 1);
        downheap(0);
        return answer;
    }

/* This method will be used to print the heap as tree structure. */
public void printHeap() {
    System.out.println("Current Heap Tree Structure: \n");
    int n = heap.size();
    if (n == 0) {
        System.out.println("(empty)");
        return;
    }

    int height = (int) (Math.log(n) / Math.log(2)) + 1;
    int index = 0;

    for (int level = 0; level < height; level++) {
        int levelCount = (int) Math.pow(2, level);
        int nodeWidth = 6; // (x,x) format
        int maxLevelWidth = (int) Math.pow(2, height) * nodeWidth;
        int spacesBetween = maxLevelWidth / (levelCount + 1); // spacing between nodes
        int leadingSpace = spacesBetween / 2;

        System.out.print(" ".repeat(leadingSpace));

        for (int i = 0; i < levelCount && index < n; i++, index++) {
            PQEntry<K, V> entry = heap.get(index);
            System.out.printf("(%s,%s)", entry.getKey(), entry.getValue());

            if (i < levelCount - 1)
                System.out.print(" ".repeat(spacesBetween - nodeWidth));
        }
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");

    }
}


    /* Putting this main method to test and show what this program does. */
    public static void main(String[] args) 
    {
        heapPriorityQueue<Integer, String> pq = new heapPriorityQueue<>();
        
        /* Here creating a heap using a sequence of insert operations in the same order that was mentioned in our instruction/question. */
        pq.insert(5, "A");
        pq.insert(4, "B");
        pq.insert(7, "F");
        pq.insert(1, "D");
        pq.insert(3, "J");
        pq.insert(6, "L");
        pq.insert(8, "G");
        pq.insert(2, "H");

        pq.printHeap();  /* Calling printing method to print the heap as tree structure after completing the above insertions. */

    }

    /* Default comparator for regular/natural ordering. */
    protected static class DefaultComparator<E> implements Comparator<E> 
    {
        @SuppressWarnings("unchecked")
        public int compare(E a, E b) throws ClassCastException 
        {
            return ((Comparable<E>) a).compareTo(b);
        }
    }
}
