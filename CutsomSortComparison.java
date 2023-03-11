import java.util.ArrayList;
import java.lang.Math;
import java.util.Collections;
import java.util.Arrays;

@SuppressWarnings({"unchecked", "rawtypes"})
public class CutsomSortComparison {
    public static final int maxStringLength = 10;
    public static final int[] arrLengths = {10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};
    public static final int[] threadCounts = {1, 2, 3, 4, 5, 6, 7, 8};
    public static final Class[] arrTypes = {Character.class, String.class, Short.class, Integer.class, Long.class, Float.class, Long.class};

    public static<T extends Comparable<T>> double[][][][] compareSorts() {
        double[][][][] results = new double[2][arrTypes.length][arrLengths.length][threadCounts.length];

        for (int i=0; i<arrTypes.length; i++) {
            for (int j=0; j<arrLengths.length; j++) {
                    Class type = arrTypes[i];
                    int length = arrLengths[j];
                    ArrayList<T> origList = createList(type, length);
                    T[] origArr = origList.toArray((T[])new Comparable[origList.size()]);

                for (int k=0; k<threadCounts.length; k++) {
                    System.out.println((i * (arrLengths.length * threadCounts.length) + (j * threadCounts.length) + k + 1) + "/"
                                        + (arrTypes.length * arrLengths.length * threadCounts.length) + 
                                        " " + arrTypes[i].getSimpleName() + " " + arrLengths[j] + " elements");
                    int numThreads = threadCounts[k];

                    ArrayList<T> list = new ArrayList<T>(origList);
                    T[] arr = Arrays.copyOf(origArr, origArr.length);

                    CustomCollections.setMaxThreads(numThreads);
                    double startTime = System.nanoTime() * 1E-9;
                    CustomCollections.sort(arr);
                    double endTime = System.nanoTime() * 1E-9;
                    double executionTime = endTime - startTime;
                    results[0][i][j][k] = executionTime;

                    startTime = System.nanoTime() * 1E-9;
                    Collections.sort(list);
                    endTime = System.nanoTime() * 1E-9;
                    executionTime = endTime - startTime;
                    results[1][i][j][k] = executionTime;
                    
                    T[] arrNormalSort = list.toArray((T[])new Comparable[list.size()]);
                    if (checkInOrder(arr) == false || checkInOrder(arrNormalSort) == false)
                        System.out.println("Sorts failed to sort");
                }
            }
        }
        return results;
    }

    public static <T extends Comparable<T>> boolean checkInOrder(T[] arr) {
        for (int i=1; i<arr.length; i++) {
            T curElement = arr[i];
            T prevElement = arr[i-1];

            if (curElement.compareTo(prevElement) < 0)
                return false;
        }

        return true;
    }

    public static <T extends Comparable<T>> ArrayList<T> createList(Class<T> type, int length) {
        ArrayList<T> list = new ArrayList<>(length);

        if (type == Character.class) {
            for (int i=0; i<length; i++) {
                int asciiValue = (int)(Math.random() * 26) + 97;
                T element = type.cast(Character.valueOf((char)asciiValue));
                list.add(element);
            }
        }
        else if (type == String.class) {
            for (int i=0; i<length; i++) {
                int stringLength = (int)(Math.random() * maxStringLength);

                String str = "";
                for (int j=0; j<stringLength; j++) {
                    int asciiValue = (int)(Math.random() * 26) + 97;
                    str = str + (char)asciiValue;
                }
                T element = type.cast(str);
                list.add(element);
            }
        }
        else if (type == Short.class) {
            for (int i=0; i<length; i++) {
                short value = (short)(Short.MIN_VALUE - ((short)(Math.random() * Short.MIN_VALUE) - 1) + ((short)(Math.random() * Short.MAX_VALUE) + 1));
                T element = type.cast(value);
                list.add(element);
            }
        }
        else if (type == Integer.class) {
            for (int i=0; i<length; i++) {
                int value = Integer.MIN_VALUE - ((int)(Math.random() * Integer.MIN_VALUE) - 1) + ((int)(Math.random() * Integer.MAX_VALUE) + 1);
                T element = type.cast(value);
                list.add(element);
            }
        }
        else if (type == Long.class) {
            for (int i=0; i<length; i++) {
                long value = Long.MIN_VALUE - ((long)(Math.random() * Long.MIN_VALUE) - 1) + ((long)(Math.random() * Long.MAX_VALUE) + 1);
                T element = type.cast(value);
                list.add(element);
            }
        }
        else if (type == Float.class) {
            for (int i=0; i<length; i++) {
                float value = Float.MIN_VALUE - (float)(Math.random() * Float.MIN_VALUE) + (float)(Math.random() * Float.MAX_VALUE);
                T element = type.cast(value);
                list.add(element);
            }
        }
        else if (type == Double.class) {
            for (int i=0; i<length; i++) {
                double value = Double.MIN_VALUE - (double)(Math.random() * Double.MIN_VALUE) + (double)(Math.random() * Double.MAX_VALUE);
                T element = type.cast(value);
                list.add(element);
            }
        }
        else {
            System.out.println("Unsupported data type cannot be randomly assigned values");
        }

        return list;
    }
}
