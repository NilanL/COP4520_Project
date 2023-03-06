import java.util.*;

public class Main {

    public static void printArray(Integer [] array)
    {
        System.out.print("[ ");
        for (Integer val : array)
        {
            System.out.print(val + " ");
        }
        System.out.println("]");
    }

    public static void confirmSort(Integer [] array)
    {
        for (int i = 0; i < array.length - 1; i++)
        {
            if (array[i + 1] < array[i])
            {
                System.out.println("ARRAY NOT SORTED DUMMY");
                break;
            }
        }
    }

    public static void confirmSort(List<Integer> array)
    {
        for (int i = 0; i < array.size() - 1; i++)
        {
            if (array.get(i + 1) < array.get(i))
            {
                System.out.println("ARRAY NOT SORTED DUMMY");
                break;
            }
        }
    }

    public static void main(String [] args)
    {
        int n = 10000000;
        Integer [] array = new Integer [n];

        for (int i = 0; i < n; i++)
        {
            Random rand = new Random();
            array[i] = Integer.valueOf(rand.nextInt(n));
        }
        Integer [] array2 = Arrays.copyOf(array, n);

        //printArray(array);

        List<Integer> sortArray = Arrays.asList(array2);
        long startTime = System.nanoTime();
        double execTime = 0;
        Collections.sort(sortArray);
        execTime = (System.nanoTime() - startTime) / 1000000.0;

        long startTime2 = System.nanoTime();
        double execTime2 = 0;
        CustomCollections.sort(array);
        execTime2 = (System.nanoTime() - startTime2) / 1000000.0;

        confirmSort(sortArray);
        confirmSort(array);

        System.out.println("Collections.sort() " + execTime);
        //printArray(array);

        System.out.println("CustomCollections.sort() " + execTime2);
        //printArray(array2);
    }
}
