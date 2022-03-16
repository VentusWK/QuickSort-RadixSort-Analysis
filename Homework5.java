import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
class Homework5
{
    public static long[] data = new long[10000000];
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
        for(int i = 0; i < n; i++)
        {
            A[i] = arr[i];
        }

        for(int exp = 0; exp <= 3; exp++)
        {
            // Counting sort
            int[] C = new int[n];

            for(int i = 0; i < n; i++)
            {
                C[findBaseNDigit(A[i], 256, exp)] += 1;
            }

            for(int i = 1; i < n; i++)
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
    public static void main (String[] args)
    {
        // reads input file and creates output file based on arguments provided
        if(args.length > 0)
        {
            try {
                System.out.println(args[0]);
                File inputFile = new File(args[0]);
                Scanner reader = new Scanner(inputFile);

                int i = 0;
                while(reader.hasNextLong())
                {
                    data[i++] = reader.nextLong();
                }
                reader.close();
                System.out.println(isOrdered(radixSort(data, data.length)));
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred. Can't create file.");
                e.printStackTrace();
            }
            try {
                File outputFile = new File(args[1]);
                if(outputFile.createNewFile()) {
                    System.out.println("File created: " + outputFile.getName());
                } 
                else{
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}