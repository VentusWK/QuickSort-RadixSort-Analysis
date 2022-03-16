import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
class Homework5
{
    public static int findBaseNDigit(int num, int n, int exp)
    {
        return (num / (int) Math.pow(n, exp)) % n;
    }

    public static int[] radixSort(int arr[], int n)
    {
        int[] A = new int[n];
        int[] B = new int[n];
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
                File myObj = new File(args[0]);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    System.out.println(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
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