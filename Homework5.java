import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
class Homework5
{
    // Checks if array is properly ordered
    public static boolean isOrdered(int arr[])
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
        return (int)(num / (long) Math.pow(n, exp)) % n;
    }

    public static long[] radixSort(long arr[], int n)
    {
        long[] A = new long[n];
        long[] B = new long[n];
        for(int i = 0; i < n; i++)
        {
            A[i] = arr[i];
        }

        // Only iterates 3 times since log_n(n^2.7) = 2.7.
        // The largest value in the valid set can't have more than 3 digits in base n after we take log_n of it.
        for(int exp = 0; exp <= 2; exp++)
        {
            // Counting sort
            int[] C = new int[n];

            for(int i = 0; i < n; i++)
            {
                C[findBaseNDigit(A[i], n, exp)] += 1;
            }

            for(int i = 1; i < n; i++)
            {
                C[i] += C[i-1];
            }

            for(int i = n - 1; i >= 0; i--)
            {
                B[C[findBaseNDigit(A[i], n, exp)] - 1] = A[i];
                C[findBaseNDigit(A[i], n, exp)] -= 1;
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

                long[] data = new long[10000000];
                int i = 0;
                while(reader.hasNextLong())
                {
                    System.out.println(reader.nextLong());
                }
                reader.close();
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