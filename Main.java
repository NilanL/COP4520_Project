import java.util.*;

public class Main {

    public static void printArray(String [] array)
    {
        System.out.print("[ ");
        for (String val : array)
        {
            System.out.print(val + " ");
        }
        System.out.println("]");
    }

    public static void confirmSort(String [] array)
    {
        for (int i = 0; i < array.length - 1; i++)
        {
            if (array[i + 1].compareTo(array[i]) < 0)
            {
                System.out.println("ARRAY NOT SORTED DUMMY");
                break;
            }
        }
    }

    public static void confirmSort(List<String> array)
    {
        for (int i = 0; i < array.size() - 1; i++)
        {
            if (array.get(i + 1).compareTo(array.get(i)) < 0)
            {
                System.out.println("ARRAY NOT SORTED DUMMY");
                break;
            }
        }
    }

    public static void main(String [] args)
    {
        int n = 100;
        String [] array = new String [n];

        for (int i = 0; i < n; i++)
        {
            Random rand = new Random();
            String str = "";
            for (int j = 0; j < rand.nextInt(9) + 1; j++)
                str += (char)(rand.nextInt(26) + 97);
            array[i] = str;
        }
        String [] array2 = Arrays.copyOf(array, n);

        printArray(array);

        List<String> sortArray = Arrays.asList(array2);
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
