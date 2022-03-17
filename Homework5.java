import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
class Homework5
{
    public static long[] data = new long[10000000];
    public static long[] copy = new long[10000000];

    // Checks if array is properly ordered
    public static boolean isOrdered(long arr[])
    {
        for(int i = 1; i < arr.length; i++)
        {
            if(arr[i] < arr[i-1])
                return false;
        }
        return true;
    }
    
    public static int findBaseNDigit(long num, int n, int exp)
    {
        return (int)((num / (long) Math.pow(n, exp)) % n);
    }

    public static long[] radixSort(long arr[], int n)
    {
        long[] A = new long[n];
        long[] B = new long[n];

        // Make A a copy of arr
        A = arr;

        // Only iterates 4 times since 2^32 numbers can be represented with 4 radices in base 256
        for(int exp = 0; exp <= 3; exp++)
        {
            // Counting sort
            int[] C = new int[256];

            for(int i = 0; i < n; i++)
            {
                C[findBaseNDigit(A[i], 256, exp)] += 1;
            }

            for(int i = 1; i < 256; i++)
            {
                C[i] += C[i-1];
            }

            for(int i = n - 1; i >= 0; i--)
            {
                B[C[findBaseNDigit(A[i], 256, exp)] - 1] = A[i];
                C[findBaseNDigit(A[i], 256, exp)] -= 1;
            }

            for(int i = 0; i < n; i++)
            {
                A[i] = B[i];
            }
        }
        return A;
    }

    // Swaps values
    static void swap(long[] arr, int i, int j)
    {
        long temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static int partition(long[] arr, int low, int high)
    {
        long piv = arr[high];

        int i = low - 1;

        for(int j = low; j <= high - 1; j++)
        {
            if(arr[j] < piv)
            {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    public static void quickSort(long[] arr, int low, int high)
    {
        if(low < high)
        {
            int p = partition(arr, low, high);

            quickSort(arr, low, p - 1);
            quickSort(arr, p + 1, high);
        }
    }

    public static void main (String[] args)
    {
        // reads input file and creates output file based on arguments provided
        if(args.length > 0)
        {
            try {
                // Read input file
                File inputFile = new File(args[0]);
                Scanner reader = new Scanner(inputFile);

                // Create output file
                File outputFile = new File(args[1]);
                if(outputFile.createNewFile()) {
                    System.out.println("File created: " + outputFile.getName());
                } 
                else{
                    System.out.println(outputFile.getName() + " already exists. Overwriting file...");
                }

                // Create output file writer
                FileWriter writer = new FileWriter(args[1]);

                // Inputs data from data0.txt to data[] array
                int i = 0;
                while(reader.hasNextLong())
                {
                    data[i] = reader.nextLong();
                    copy[i] = data[i];
                    i++;
                }
                reader.close();

                // Sort data with Radix Sort and measure time
                long startTime = System.nanoTime();
                radixSort(data, data.length);
                long duration = System.nanoTime() - startTime;

                // Write Radix Sort runtime and sorting success to output
                writer.write("Radix Sort Runtime: " + duration/1000000 + " milliseconds\n");
                if(isOrdered(data))
                {
                    writer.write("Radix Sort properly sorted " + args[0] + "\n");
                }

                // Sort data with Quick Sort and measure time
                startTime = System.nanoTime();
                quickSort(copy, 0, copy.length - 1);
                duration = System.nanoTime() - startTime;
                
                // Write Quick sort runtime and sorting success to output
                writer.write("Quick Sort Runtime: " + duration/1000000 + " milliseconds\n");
                if(isOrdered(copy))
                {
                    writer.write("Quick Sort properly sorted " + args[0] + "\n");
                }
                writer.close();

                System.out.println("Write complete.");
                
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred. FileNotFound.");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}