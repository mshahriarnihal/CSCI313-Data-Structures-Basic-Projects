Read Me File:

   Author's Name : Mubasshir Al Shahriar (Nihal)
 Relevant Course : CSCI 313: Data Structures (CUNY QC)
   
This repository contains some small and basic projects I built with Java while taking my Data Structures course (CSCI-313) at Queens College of The City University of New York.


This read me file shows how to run my codes and brief explanation of how my coded programs work.
To run the Java codes I worked on, just compile and run those codes (.java files). You can just use javac *.java command to compile all the source codes at a time. Then run the java codes.



 AVLTreeMapWithBalance.java :
“java AVLTreeMapWithBalance” command should run this code.

Instead of storing subtree heights, this Java program reimplements the AVLTreeMap using balance factors. Thus, it reduces space usage while maintaining AVL tree's properties/conditions.
In general, in a usual AVL tree each node stores a height. But this code instead stores a balance factor.
Balance Factor = height(left sub-tree) − height(right sub-tree).
After insertions/deletions, balance factors are adjusted and trim-node restructure algorithm's rotations (single or double) are applied as necessary to restore the balance and maintain the property of AVL tree.

Algorithm:

Insertion:
When we need to perform the insertion operation into the AVL tree, we can insert new elements like a normal BST. After completing the insertion, we need to update balance factors going back up the tree. If any node has balance factor > 1 or < -1, we will need to perform necessary rotation as per necessity.

The running time of Balance Factor update is efficient because the balance factor is only either -1, 0, or +1 in a balanced AVL tree.

Significant methods that I used in the program:

insert(k, v) — This method performs insert operations while maintaining AVL tree's property.
delete() - This is a void method which deletes node recursively and rebalances if needed.
remove() - This method removes a specific key from the tree.
updateBalance() — This method updates the balance factors of our AVL tree.
rotateLeft(), rotateRight() — These 2 methods performs AVL tree rotations to restore the balance after insertion or deletion.
replaceNodeInParent() - This void method replaces one node in the tree with another one (used in deletion and rotation).
printTree() — This is the print method of the tree which prints the correct result of AVL tree as a pyramid shaped tree-like structure. It prints the correctly reshaped AVL tree after insertion, deletion, and restoring balance too.

Output:

In output, after inserting a arbitrarily hardcoded sequence of 9 nodes, first I am displaying the initial tree (with those arbitrarily selected inputs: 44, 17, 62, 32, 50, 78, 48, 54, 88). Then after deleting the node 32 and rebalancing AVL tree in main method by calling necessary functions, I am showing the rebalanced tree in output after deleting the node with 32. We can see in the output that the program successfully prints the correctly rebalanced AVL tree after deleting 32.





 QuickSortComparison.java :
“java QuickSortComparison” command should run this code.

This Java code implements deterministic and randomized versions of QuickSort algorithm. After running both versions of QuickSort, it also performs benchmarking to measure their speeds on both (i) Very "random" arrays and "Almost" sorted arrays.
In the "deterministic" version of QuickSort, it always chooses the last element as pivot in this program. As we learnt in the class, it is not so efficient and in worst-case when array is already sorted, the running time can be as bad as O(n^2). But if the array is not sorted, then it does not make big difference in general (depends somewhat on the sequence).
On the other hand, in "randomized" version of QuickSort, using the pre-defined "rand" function of Java (rand.nextInt()), it chooses a random pivot each time. It reduces the chance of having the "worst pivot" every time. It does not guarantee that we will not get the "worst pivot," but the probabilistic approach reduces the chance to "1/n." Thus, it prevents worst-case performance in sorted/repeated arrays and gives us an "expected" running time of O(n log n). So, we can say, it creates the difference and gives us a better running time when we have an array where the elements are already sorted or almost sorted. The code further does a benchmark test to measure and show the total time to the user to ensure these facts.

Benchmarking Test:
To perform benchmarking test, and show the user the measured time, inside the "double" method named "benchmark," I used the pre-defined Java method named ".nanoTime()" to track the starting and ending time of specific parts of this program.

Output:
In output, the program first shows 2 different sequences of very "random" 10 integers. First it shows the original (unsorted) inputs. Then it shows the inputs after running the "Deterministic" version of QuickSort. In very next line, it shows the running time it got for this sorting. After that, in next lines, it shows the inputs after running the "Randomized" version of QuickSort and shows the measured time. But these two sequences contain only small amount of elements, which might not always show us a bigger picture and give clear idea about the running time. So, next, I performed the benchmark test on 1 sequence of very large set of integers. I randomly generated 7 million very "random" integers and measured the time. For these 7 million integers, I did not show the original and sorted inputs (the integers) as it will not be so convenient to look at an output with 7 million integers. I only printed the measured time that it took to do sorting on those 7 million very "random" unsorted integers.

Next, I repeated these same things but for sequences of "almost" sorted integers. First did it on 2 different sequences of "almost" sorted 10 integers, and then to be more precise performed benchmark test on a set of 7 million "almost" sorted integers.

If we look at the benchmark results at output, we can find that, indeed the "randomized" version of QuickSort reduces and gives us way better (efficient) running time when we have an array of already sorted or "almost" sorted integers. In case of "very random" sequences of integers, it does not make that difference, even, often, "deterministic" version performs better than the "randomized" as the elements are already highly unsorted and random, and we have no idea how random they will be and what relation they will have with the randomly selected pivots. But it does create visible difference when we have sorted or "already" sorted array. This confirms the algorithm really helps us to get better result in terms of running time and also confirms that the Java program is running correctly.  






 heapPriorityQueue.java : 
“java heapPriorityQueue” command should run this code.
 
This code reimplements the algorithm of downheap and upheap methods in Java, such that these methods use recursion, instead of loop. My code contains main method that creates a heap using a sequence of given insert operations, which are: (5,A), (4,B),(7,F),(1,D),(3,J),(6,L),(8,G),(2,H). The main focus of this program is that instead of using the standard loop-based upheap() and downheap() methods, it reimplements both methods using recursion. However, we can say, this program takes those 8 hardcoded key-value pairs into the heap as its input and in output, it shows the elements in order they were removed from the queue, meaning, it prints the elements in ascending order by removing the minimum element repeatedly. It starts with the higher priority (smaller value), and ends with the element with lowest priority. This removal (using remove method) tests whether the heap structure is working correctly or not using recursive methods. If we can see the output is printing the key-value pairs in ascending order (starts with highest priority and goes towards smaller priorities), then that means the heap structure is working properly. 


 In the reimplementation of upheap method using recursion, in base case, it checks if our input is 0. If it has not reached the base case, then compares the input with its parent. If it is smaller, then swaps upward (if needed) and calls the upheap function recursively. This method is called after doing any insertion inside the "insert" method.

 The recursive downheap method is called after removing something through the "removeMin()" method. The reimplemented downheap method compares the root with its smaller child and swaps downward recursively to restore heap order.

 I used a printing method named "printHeap" which I later used inside the main function to display the heap as tree structure in the output.






 skipList.java : 
“java skipList” command should run this code.

This program implements a Skip List using Java, based on the concept of "Skip List" data structure. To implement the skip list class, following the given instructions I used randomization (rand function) to determine which nodes should be promoted to higher level. This program also contains efficient insertion, search, and removal operations. I implemented "skipInsert(k,v)" method in this program to insert in the skip list entry with key k and value v; "skipRemove(k)" method to remove certain position(s) corresponding to key k from all levels in the skipList and "skipSearch(k)" method to search for key k in the skip list (returns position corresponding to key k in the bottom list (S0), or null if k is not found). My code contains the main method that creates a skip list from the hardcoded sequence of 10 arbitrary integers and prints out the resulting list to stdout.

The java file is named "skipList.java" and it contains all the necessary classes and everything (the skipList class, the internal node structure, constructors, implemented necessary methods, and the main method) in the same single file. The main method of the program contains hardcoded sequence of 10 arbitrary integers, because, it is necessary to visualize and show how the program works and what is going on.

When you run the code, it does the following:
First, it creates a new skip list. Then it inserts 10 hardcoded integer keys using the "skipInsert(k,v)" method. I used the following 10 keys randomly/arbitrarily: 35, 23, 7, 48, 15, 9, 41, 60, 28, 3. Each of them are paired with a string value like "V35", "V9", "V60" etc. Finally, it prints our initial full skip list (all levels, starting from level S0 at bottom), so that we can see the final sorted order of the elements, as we already know, one of the feature of skip list is it is always sorted.

In next lines of the output, the program shows how the "skipRemove(k)" and "skipSearch(k)" methods work and what they actually do. In my program, it searches for the key "28", and prints whether it was found or not with the position. Next, it removes key "28" from all levels of the skip list using "skipRemove(k)" method. Finally, it calls the ".printList" method again to print the full skip list again (with all levels) to show that 28 was removed from the skip list successfully.


In my code, each node contains a key, value, and 4 pointers (next, prev, above, below) as we saw in the node structure and useful functions section of the lecture slide. "next(p)" returns the position following p on the same level, "prev(p)" returns the position preceding p on the same level, "above(p)" returns the position above p in the same tower, and "below(p)" returns the position below p in the same tower. The program inserts a new key at the bottom level using "skipInsert(k,v)" method, then flips a coin (random boolean) to decide if it should promote the node to higher levels or not, and builds a tower if needed (based on the probabilistic perspective/coin flip). 
When the program calls "skipSearch(k)" method, it starts searching from the top-left of the skip list and moves towards right and down until it finds the desired key (that it is searching) in the bottom level, as the question/instruction of the problem specifically mentioned to return the node from the base level only when we are using the "skipSearch()."
The "skipRemove(k)" method in my code removes the entire tower of node "k" by unlinking it level by level. It starts from the bottom and moves to the up.
In this program, I used "printList()" method only to print the entire skip list with all levels which displays the final sorted structure of the list. I used "-inf" and "+inf" as starting and ending sentinels.
In the printList() method, I kept a variable to track if any element was randomly eliminated, if the element (which was present in below level but not present in current level, meaning it is eliminated) is eliminated, then in place of that element, it prints "--" so that all present elements stays in correct position (in same tower it is in lower level(s)).

In this program with skip list data structure, insert, search, and remove - all these operations work in expected O(log n) running time.




Thanks for reading.

