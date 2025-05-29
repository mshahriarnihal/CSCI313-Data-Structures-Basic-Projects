

/*   Author's Name: Mubasshir Al Shahriar
Relevant Course   : CSCI 313: Data Structures (Mon-Wednesday 3:10 p.m) */



import java.util.*;

public class QuickSortComparison 
{
    /* Implementing the "Deterministic" QuickSort. Here I will always take the last element of the array as pivot. */
    public static void deterministicQuickSort(int[] arr, int low, int high) 
    {
        if (low < high) 
        {
            int pi = deterministicPartition(arr, low, high);    // It partitions and gets the pivot index.
            deterministicQuickSort(arr, low, pi - 1);           // Recursion on left part.
            deterministicQuickSort(arr, pi + 1, high);          // Recursion on right part.
        }
    }

    /* This partitioning method will be needed to be used inside the deterministic QuickSort. */
    private static int deterministicPartition(int[] arr, int low, int high) 
    {
        int pivot = arr[high];              // Using the last element as pivot.
        int i = low - 1;                    // Index for smaller element.
        for (int j = low; j < high; j++)    
        {
            if (arr[j] <= pivot)            /* Check if the current element is smaller than the current pivot. */
            {
                i++;
                swap(arr, i, j);            /* If the "if" condition is satisfied, then it swaps to move the smaller element to the left. */
            }
        }
        swap(arr, i + 1, high);             /* Next, it puts the pivot in correct position. */
        return i + 1;                       // Returning the pivot index.
    }

    /* In this "Randomized" version of QuickSort method, we will always need to use a random position as our pivot. */
    public static void randomizedQuickSort(int[] arr, int low, int high) 
    {
        if (low < high) 
        {
            int pi = randomizedPartition(arr, low, high);    // It partitions with the random pivot.
            randomizedQuickSort(arr, low, pi - 1);
            randomizedQuickSort(arr, pi + 1, high);
        }
    }
   
    /* This partitioning method will be needed to be used inside the "randomized" QuickSort. */
    private static int randomizedPartition(int[] arr, int low, int high) 
    {
        int pivotIndex = new Random().nextInt(high - low + 1) + low;          /* Choosing a random index according to our algorithm. */
        swap(arr, pivotIndex, high);
        return deterministicPartition(arr, low, high);
    }

    /* This method will be used everywhere we need to perform a swap. */
    private static void swap(int[] arr, int i, int j) 
    {
        int temp = arr[i]; 
        arr[i] = arr[j]; 
        arr[j] = temp;
    }

    // Printing method
    private static void printArray(String label, int[] arr) 
    {
        System.out.printf("%s: %s\n", label, Arrays.toString(arr));
    }

    // Method to copy array
    private static int[] copyArray(int[] arr)
    {
        return Arrays.copyOf(arr, arr.length);
    }

    /* We will use this method to measure time taken by sorting functions to complete the benchmarking tests as instructed in the question. */
    private static double benchmark(Runnable task) 
    {
        long start = System.nanoTime();           /* Using Java's pre-defined ".nanoTime()" method to keep track of the starting time in order to measure the total time. */
        task.run();                               /* Running the sorting. */
        long end = System.nanoTime();             /* Keeps track of the ending time. */
        return (end - start) / 1e6;               /* Converts the total time. */
    }

    /* This method generates a "very random" integer array. */
    private static int[] generateRandomArray(int size) 
    {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = rand.nextInt(1_000_000);
        return arr;
    }

    /* This method generates a "almost sorted" integer array. */
    private static int[] generateAlmostSortedArray(int size) 
    {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = i * 2;
        for (int i = 0; i < size / 100; i++) 
        {
            int a = new Random().nextInt(size);
            int b = new Random().nextInt(size);
            swap(arr, a, b);
        }
        return arr;
    }


    /* Main method of this program. */
    public static void main(String[] args) 
    {
        System.out.println("");
        System.out.println("---- Quick Sort Benchmarks On Very 'Random' Sequences: ---- ");
        System.out.println("");

        /* Running the test on two small sequences of 10 very random integers. Here I will show the 10 random original inputs, then show the sorted version of them, and then will display the benchmark times. */
        for (int i = 1; i <= 2; i++) 
        {
            int[] randomInput = generateRandomArray(10);
            int[] copy1 = copyArray(randomInput);               /* This one is for the "deterministic" sort. */
            int[] copy2 = copyArray(randomInput);               /* This one is for the "randomized" sort. */

            System.out.println("\nRandom Sequence " + i);
            printArray("Original             ", randomInput);

            double time1 = benchmark(() -> deterministicQuickSort(copy1, 0, copy1.length - 1));
            printArray("Deterministic Sorted", copy1);
            System.out.printf("Time: %.6f seconds\n", time1 / 1000);

            double time2 = benchmark(() -> randomizedQuickSort(copy2, 0, copy2.length - 1));
            printArray("Randomized   Sorted", copy2);
            System.out.printf("Time: %.6f seconds\n", time2 / 1000);
        }

        /* With sequence of smaller amount of elements, we cannot clear understand the time difference precisely. So, here I am testing on a very large set (7 million elements).
           For this test, I am showing the benchmark test only, not all those 7 million inputs and their sorted version to avoid readability issue and any trouble. */
        System.out.println("\nLet's test on a very large random array (7 million inputs) - ");
        int[] largeRandom = generateRandomArray(7_000_000);
        int[] largeCopy1 = copyArray(largeRandom);
        int[] largeCopy2 = copyArray(largeRandom);

        double largeTime1 = benchmark(() -> deterministicQuickSort(largeCopy1, 0, largeCopy1.length - 1));
        System.out.printf("Deterministic Time on Large Random:   %.6f seconds\n", largeTime1 / 1000);

        double largeTime2 = benchmark(() -> randomizedQuickSort(largeCopy2, 0, largeCopy2.length - 1));
        System.out.printf("Randomized   Time on Large Random:   %.6f seconds\n", largeTime2 / 1000);


        System.out.println("");
        System.out.println("\n ---- Quick Sort Benchmarks On Very 'Almost' Sorted Sequences: ----");
        System.out.println("");

        /* Running the test on two small sequences of 10 almost sorted integers. Here I will show the 10 random original inputs, then show their sorted version, and then will display the benchmark times. */
        for (int i = 1; i <= 2; i++) 
        {
            int[] almostSorted = generateAlmostSortedArray(10);
            int[] copy1 = copyArray(almostSorted);
            int[] copy2 = copyArray(almostSorted);

            System.out.println("\nAlmost Sorted Sequence " + i);
            printArray("Original             ", almostSorted);

            double time1 = benchmark(() -> deterministicQuickSort(copy1, 0, copy1.length - 1));
            printArray("Deterministic Sorted", copy1);
            System.out.printf("Time: %.6f seconds\n", time1 / 1000);

            double time2 = benchmark(() -> randomizedQuickSort(copy2, 0, copy2.length - 1));
            printArray("Randomized   Sorted", copy2);
            System.out.printf("Time: %.6f seconds\n", time2 / 1000);
        }

        /* With sequence of smaller amount of elements, we cannot clear understand the time difference precisely. So, here I am testing on a very large set (7 million elements).
           For this test, I am showing the benchmark test only, not showing all those 7 million inputs and their sorted version to avoid readability issue and any trouble. */
        System.out.println("\nLet's test on a very large almost sorted array (7,000,000 elements)...");
        int[] largeAlmostSorted = generateAlmostSortedArray(7_000_000);
        int[] largeCopy3 = copyArray(largeAlmostSorted);
        int[] largeCopy4 = copyArray(largeAlmostSorted);

        double largeTime3 = benchmark(() -> deterministicQuickSort(largeCopy3, 0, largeCopy3.length - 1));
        System.out.printf("Deterministic Time on Large Almost Sorted: %.6f seconds\n", largeTime3 / 1000);

        double largeTime4 = benchmark(() -> randomizedQuickSort(largeCopy4, 0, largeCopy4.length - 1));
        System.out.printf("Randomized   Time on Large Almost Sorted: %.6f seconds\n", largeTime4 / 1000);
    }
}
