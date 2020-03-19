import java.util.*;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Nicholas Watney Meyer
 * @version 2.1
 * @userid nmeyer32
 * @GTID 903 444 783
 *
 * Collaborators: Me, Myself, and I.
 *
 * Resources: Only those provided to me.
 */
public class Sorting {

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {

        if (arr == null || comparator == null) {

            throw new IllegalArgumentException("The array or comparator is null");
        }

        int length = arr.length;

        for (int i = 1; i < length; i++) {

            int j = i;

            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {

                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }

    /**
     * Implement bubble sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * NOTE: See pdf for last swapped optimization for bubble sort. You
     * MUST implement bubble sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {

        if (arr == null || comparator == null) {

            throw new IllegalArgumentException("The array or comparator is null");
        }

        int i = 0;
        int length = arr.length;
        boolean swapped = true;

        while (i < length - 1 && swapped) {

            swapped = false;

            for (int j = 0; j < length - i - 1; j++) {

                if (comparator.compare(arr[j], arr[j + 1]) > 0) {

                    T temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                    swapped = true;

                }
            }

            i++;
        }
    }


    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {

        if (arr == null || comparator == null) {

            throw new IllegalArgumentException("The array or comparator is null");
        }


        int length = arr.length;
        int middle = length / 2;

        if (length == 1) {

            return;
        }

        T[] leftArray = (T[]) new Object[middle];
        T[] rightArray = (T[]) new Object[length - middle];

        for (int i = 0; i < middle; i++) {

            leftArray[i] = arr[i];
        }

        for (int i = 0; i < length - middle; i++) {

            rightArray[i] = arr[i + middle];
        }

        mergeSort(leftArray, comparator);
        mergeSort(rightArray, comparator);

        int leftIndex = 0;
        int rightIndex = 0;
        int currentIndex = 0;

        while (leftIndex < middle && rightIndex < length - middle) {

            if (comparator.compare(leftArray[leftIndex], rightArray[rightIndex]) <= 0) {

                arr[currentIndex] = leftArray[leftIndex];
                leftIndex++;

            } else {

                arr[currentIndex] = rightArray[rightIndex];
                rightIndex++;
            }

            currentIndex++;
        }

        while (leftIndex < middle) {

            arr[currentIndex] = leftArray[leftIndex];
            leftIndex++;
            currentIndex++;
        }

        while (rightIndex < length - middle) {

            arr[currentIndex] = rightArray[rightIndex];
            rightIndex++;
            currentIndex++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     * <p>
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * <p>
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {

        if (arr == null) {
            throw new IllegalArgumentException("The array is null");
        }

        int iterations = 0;

        for (int element : arr) {
            int count = 0;

            while (element != 0) {
                element = element / 10;
                count++;
            }

            if (count > iterations) {
                iterations = count;
            }
        }

        LinkedList<Integer>[] buckets = new LinkedList[19];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<Integer>();
        }

        int radix = 1;
        int idx = 0;

        while (idx < iterations) {

            for (int element : arr) {

                int position = (element / radix) % 10 + 9;
                buckets[position].add(element);
            }

            int count = 0;
            for (int i = 0; i < buckets.length; i++) {

                while (buckets[i].size() > 0) {
                    arr[count] = buckets[i].removeFirst();
                    count++;
                }
            }

            idx++;
            radix *= 10;
        }
    }


    /**
     * Implement heap sort.
     * <p>
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     * <p>
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     * <p>
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {

        if (data == null) {
            throw new IllegalArgumentException("the data is null");
        }

        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(data);

        int[] list = new int[data.size()];

        for (int i = 0; i < data.size(); i++) {

            list[i] = queue.remove();
        }

        return list;
    }

    /**
     * Implement kth select.
     * <p>
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     * <p>
     * int pivotIndex = rand.nextInt(b - a) + a;
     * <p>
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     * <p>
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * <p>
     * It should be:
     * in-place
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {

        if (arr == null || comparator == null || rand == null || k < 1 || k > arr.length) {

            throw new IllegalArgumentException("The array or comparator is null or k is not in the acceptable range");
        }

        return rKthSelect(k, arr, 0, arr.length, comparator, rand);
    }


    /**
     * Recursive Kth Select method that takes in more parameters when recursively calling itself.
     * @param k Kth smallest index
     * @param arr The array in which the kth smallest index would be found
     * @param left The minimum index of the array to be searched
     * @param right The maximum index of the array to be searched
     * @param comparator defines comparing behaviour and keep count
     * @param rand object for random generation
     * @param <T> The data type to be used
     * @return returns the kth smallest type
     */
    public static <T> T rKthSelect(int k, T[] arr, int left, int right, Comparator<T> comparator,
                                   Random rand) {

        int pivotIndex = rand.nextInt(right - left) + left;

        T temp1 = arr[pivotIndex];
        arr[pivotIndex] = arr[left];
        arr[left] = temp1;

        int leftIndex = left + 1;
        int rightIndex = right - 1;

        while (leftIndex <= rightIndex) {

            while (leftIndex <= rightIndex && comparator.compare(arr[leftIndex], temp1) <= 0) {
                leftIndex++;
            }

            while (leftIndex <= rightIndex && comparator.compare(arr[rightIndex], temp1) >= 0) {
                rightIndex--;
            }

            if (leftIndex <= rightIndex) {

                T temp2 = arr[leftIndex];
                arr[leftIndex] = arr[rightIndex];
                arr[rightIndex] = temp2;

                leftIndex++;
                rightIndex--;

            }
        }

        arr[left] = arr[rightIndex];
        arr[rightIndex] = temp1;

        if (k - 1 == rightIndex) {
            return arr[rightIndex];
        }

        if (k - 1 < rightIndex) {
            return rKthSelect(k, arr, left, rightIndex, comparator, rand);

        } else {
            return rKthSelect(k, arr, leftIndex, right, comparator, rand);
        }

    }

    /**
     * Non-essential implementation of the quicksort method
     * @param arr array to be sorted
     * @param left minimum index
     * @param right maximum index
     * @param comparator comparator defining comparison behaviour and comparison count
     * @param rand random number generation
     * @param <T> data type to be sorted
     */
    public static <T> void quickSort(T[] arr, int left, int right, Comparator<T> comparator, Random rand) {

        int pivotIndex = rand.nextInt(right - left) + left;

        T temp1 = arr[pivotIndex];
        arr[pivotIndex] = arr[left];
        arr[left] = temp1;

        int leftIndex = left + 1;
        int rightIndex = right - 1;

        while (leftIndex <= rightIndex) {

            while (leftIndex <= rightIndex && comparator.compare(arr[leftIndex], temp1) <= 0) {
                leftIndex++;
            }

            while (leftIndex <= rightIndex && comparator.compare(arr[rightIndex], temp1) >= 0) {
                rightIndex--;
            }

            if (leftIndex <= rightIndex) {

                T temp2 = arr[leftIndex];
                arr[leftIndex] = arr[rightIndex];
                arr[rightIndex] = temp2;

                leftIndex++;
                rightIndex--;

            }
        }
        arr[left] = arr[rightIndex];
        arr[rightIndex] = temp1;

        quickSort(arr, left, rightIndex, comparator, rand);
        quickSort(arr, rightIndex + 1, right, comparator, rand);

    }
}



