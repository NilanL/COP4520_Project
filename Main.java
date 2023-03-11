import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileOutputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;

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
        File dataDir = new File("Data");
        if (!dataDir.exists())
            dataDir.mkdir();
        

        //results -> Sort Type -- Array Type -- Array Length -- Thread Count
        double[][][][] results = CutsomSortComparison.compareSorts();

        String[] headers = {"Thread Count", "Custom Sort", "Collections.sort"};
        String[][] data = new String[CutsomSortComparison.threadCounts.length + 1][headers.length];
        data[0] = headers;
        
        for (int type=0; type<CutsomSortComparison.arrTypes.length; type++) {
            for (int n=0; n<CutsomSortComparison.arrLengths.length; n++) {
                for (int threads=0; threads<CutsomSortComparison.threadCounts.length; threads++) {
                    data[threads+1][0] = Integer.toString(CutsomSortComparison.threadCounts[threads]);
                    data[threads+1][1] = Double.toString(results[0][type][n][threads]);
                    data[threads+1][2] = Double.toString(results[1][type][n][threads]);
                }
                File typeDir = new File("Data/" + CutsomSortComparison.arrTypes[type].getSimpleName());
                if (!typeDir.exists())
                    typeDir.mkdir();

                String fileName = "./Data/" +
                                CutsomSortComparison.arrTypes[type].getSimpleName() + "/" +
                                CutsomSortComparison.arrLengths[n] + "_elements";

                File csvData = new File(fileName + ".csv");
                if (!csvData.exists())
                    try {
                        csvData.createNewFile();
                    } catch (Exception e) {e.printStackTrace();}

                try (FileWriter writer = new FileWriter(csvData)) {
                    for (String[] row : data) {
                        writer.write(String.join(",", row) + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Comparison of Sorts");

                int rowNum = 0;
                for (String[] row : data) {
                    XSSFRow sheetRow = sheet.createRow(rowNum++);
                    int colNum = 0;
                    for (String field : row) {
                        XSSFCell cell = sheetRow.createCell(colNum++);
                        cell.setCellValue(field);
                    }
                }

                File xlsxData = new File(fileName + ".xlsx");
                if (!xlsxData.exists())
                    try {
                        xlsxData.createNewFile();
                    } catch (Exception e) {e.printStackTrace();}

                try (FileOutputStream outputStream = new FileOutputStream(xlsxData)) {
                    workbook.write(outputStream);
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    }
                
            }
        }

        // CutsomSortComparison.compareSorts();
        // int n = 100;
        // String [] array = new String [n];

        // for (int i = 0; i < n; i++)
        // {
        //     Random rand = new Random();
        //     String str = "";
        //     for (int j = 0; j < rand.nextInt(9) + 1; j++)
        //         str += (char)(rand.nextInt(26) + 97);
        //     array[i] = str;
        // }
        // String [] array2 = Arrays.copyOf(array, n);

        // printArray(array);

        // List<String> sortArray = Arrays.asList(array2);
        // long startTime = System.nanoTime();
        // double execTime = 0;
        // Collections.sort(sortArray);
        // execTime = (System.nanoTime() - startTime) / 1000000.0;

        // long startTime2 = System.nanoTime();
        // double execTime2 = 0;
        // CustomCollections.sort(array);
        // execTime2 = (System.nanoTime() - startTime2) / 1000000.0;

        // confirmSort(sortArray);
        // confirmSort(array);

        // System.out.println("Collections.sort() " + execTime);
        // //printArray(array);

        // System.out.println("CustomCollections.sort() " + execTime2);
        // //printArray(array2);
    }
}
