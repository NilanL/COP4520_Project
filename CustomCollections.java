import java.util.*;

public class CustomCollections<T extends Comparable<T>>
{
    private static int MAX_THREADS = 4;
    
    private static class SortThreads<T extends Comparable<T>> extends Thread
    {
        public SortThreads(T[] array, int begin, int end)
        {
            super (
                () -> {
                    if (array.length > 750)
                        CustomCollections.mergeSort(array, begin, end);
                    else
                        CustomCollections.insertionSort(array);
                }
            );

            this.start();
        }
    }
     
    // Perform Threaded merge sort
    public static <T extends Comparable<T>> void sort(T [] array)
    {
        final int length = array.length;

        boolean exact = length % MAX_THREADS == 0;
        int lengthPerThread = 0;
        
        if (exact)
        {
            lengthPerThread = length / MAX_THREADS;
        }
        else
        {
            lengthPerThread = length / (MAX_THREADS - 1);
        }
        
        lengthPerThread = lengthPerThread < MAX_THREADS ? MAX_THREADS : lengthPerThread;

        final ArrayList<SortThreads<?>> threads = new ArrayList<>();

        for (int i = 0; i < length; i += lengthPerThread)
        {
            int beg = i;
            int remain = length - i;
            int end = 0;
            
            if (remain < lengthPerThread)
            {
                end = i + (remain - 1);
            }
            else
            {
                end = i + (lengthPerThread - 1);
            }

            final SortThreads<?> t = new SortThreads<>(array, beg, end);

            threads.add(t);
        }

        for (Thread t : threads)
        {
            try
            {
                t.join();
            } 
            catch(InterruptedException ignored)
            {
                System.out.println(ignored.getMessage());
            }
        }

        for(int i = 0; i < length; i += lengthPerThread)
        {
            int mid = i == 0 ? 0 : i - 1;
            int remain = length - i;
            int end = remain < lengthPerThread ? i + (remain - 1) : i + (lengthPerThread - 1);
            merge(array, 0, mid, end);
        }
    }

    // Typical recursive merge sort
    public static <T extends Comparable<T>> void mergeSort(T[] array, int begin, int end)
    {
        if (begin < end)
        {
            int mid = (begin + end) / 2;
            mergeSort(array, begin, mid);
            mergeSort(array, mid + 1, end);
            merge(array, begin, mid, end);
        }
    }

    // Typical iterative insertion sort
    public static <T extends Comparable<T>> void insertionSort(T arr[])
    {
        int n = arr.length;
        for (int i = 1; i < n; ++i) 
        {
            T key = arr[i];
            int j = i - 1;
            
            while (j >= 0 && arr[j].compareTo(key) > 0) 
            {
                arr[j + 1] = arr[j];
                j = j - 1;
            }

            arr[j + 1] = key;
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> void merge(T[] array, int begin, int mid, int end)
    {
        Object[] temp = new Object [(end - begin) + 1];
         
        int i = begin, j = mid + 1;
        int k = 0;
 
        while (i <= mid && j <= end){
            if (array[i].compareTo(array[j]) <= 0)
            {
                temp[k] = array[i];
                i += 1;
            }
            else
            {
                temp[k] = array[j];
                j += 1;
            }
            k += 1;
        }
 
        while (i <= mid)
        {
            temp[k] = array[i];
            i += 1; 
            k += 1;
        }
        
        while (j <= end)
        {
            temp[k] = array[j];
            j += 1; 
            k += 1;
        }
        
        for (i = begin; i <= end; i++)
        {
            array[i] = (T)temp[i - begin];
        }
    }

    public static void setMaxThreads(int num) {
        MAX_THREADS = num;
    }
}
