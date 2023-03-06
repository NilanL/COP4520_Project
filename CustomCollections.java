import java.util.*;

public class CustomCollections<T extends Comparable<T>>
{
    // Assuming system has 4 logical processors
    private static final int MAX_THREADS = 4;
       
    // Custom Thread class with constructors
    private static class SortThreads<T extends Comparable<T>> extends Thread
    {
        SortThreads(T[] array, int begin, int end){
            super(()->{
                CustomCollections.mergeSort(array, begin, end);
            });
            this.start();
        }
    }
     
    // Perform Threaded merge sort
    public static <T extends Comparable<? super T>> void sort(T [] array)
    {
        //long time = System.currentTimeMillis();
        final int length = array.length;

        boolean exact = length % MAX_THREADS == 0;
        int lengthPerThread = exact ? (length / MAX_THREADS) : (length / (MAX_THREADS - 1));
        lengthPerThread = lengthPerThread < MAX_THREADS ? MAX_THREADS : lengthPerThread;
        final ArrayList<SortThreads<?>> threads = new ArrayList<>();

        for (int i = 0; i < length; i += lengthPerThread)
        {
            int beg = i;
            int remain = length - i;
            int end = remain < lengthPerThread ? i + (remain - 1) : i + (lengthPerThread - 1); 
            final SortThreads<?> t = new SortThreads(array, beg, end);

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
        //time = System.currentTimeMillis() - time;
        //System.out.println("Time spent for custom multi-threaded recursive merge_sort(): "+ time+ "ms");
    }
 
    // Typical recursive merge sort
    public static <T extends Comparable<? super T>> void mergeSort(T[] array, int begin, int end)
    {
        if (begin < end)
        {
            int mid = (begin + end) / 2;
            mergeSort(array, begin, mid);
            mergeSort(array, mid + 1, end);
            merge(array, begin, mid, end);
        }
    }
    
    @SuppressWarnings("unchecked")
    //Typical 2-way merge
    public static <T extends Comparable<? super T>> void merge(T[] array, int begin, int mid, int end)
    {
        Object[] temp = new Object [(end - begin) + 1];
         
        int i = begin, j = mid + 1;
        int k = 0;
 
        // Add elements from first half or second half based on whichever is lower,
        // do until one of the list is exhausted and no more direct one-to-one comparison could be made
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
 
        // Add remaining elements to temp array from first half that are left over
        while (i <= mid)
        {
            temp[k] = array[i];
            i += 1; 
            k += 1;
        }
        
        // Add remaining elements to temp array from second half that are left over
        while (j <= end)
        {
            temp[k] = array[j];
            j += 1; 
            k += 1;
        }

        // for (i = begin, k = 0; i <= end; i++,k++)
        // {
        //     array[i] = temp[k];
        // }
        
        for (i = begin; i <= end; i++)
        {
            array[i] = (T)temp[i - begin];
        }
    }
}