import java.util.Scanner;

public class ALG2 {

    public static void main(String[] args) {
        Scanner input = null;
        try {
            // input = new Scanner(System.in); // Create a Scanner object
            // System.out.println("Enter m and n : ");
            // int m = input.nextInt();
            // int n = input.nextInt();

            // // Declartion of the matrix based on user input
            // int stockPrices[][] = new int[m][n];

            // // Read the matrix values
            // System.out.println("Enter the elements of the matrix : ");
            // for (int i = 0; i < m; i++)
            //     for (int j = 0; j < n; j++)
            //         stockPrices[i][j] = input.nextInt();

            // // Display the elements of the matrix
            // System.out.println("Elements of the matrix are : ");
            // for (int i = 0; i < m; i++) {
            //     for (int j = 0; j < n; j++)
            //         System.out.print(stockPrices[i][j] + "  ");
            //     System.out.println();
            // }
            input = new Scanner(System.in);
            int m = 100;
            int n = 1000;
            int stockPrices[][] = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    stockPrices[i][j] = (int) ((Math.random() * (1000 - 0)) + 0);
                }
            }
            long start = System.nanoTime();
            maxProfit(stockPrices);
            long end = System.nanoTime();
            System.out.println("m = " + m + " and n = " + n + " takes " + (end - start) / 1000 + " micro seconds");
        } catch (Exception e) 
        {
            System.out.println("Wrong format of input given");
        } finally {
            input.close();
        }

    }

    public static void maxProfit(int[][] stockPrices) {
        int m = stockPrices.length;
        int n = stockPrices[0].length;
        Result result = new Result();
        result.maxProfit = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            Result resultEach = new Result();
            resultEach.maxProfit = Integer.MIN_VALUE;
            int minCost = stockPrices[i][0];
            resultEach.indexShare = i;
            resultEach.buyIndexDay = 0;
            for(int j = 1; j < n; j++) {
                if(stockPrices[i][j] - minCost > result.maxProfit) {
                    resultEach.maxProfit = stockPrices[i][j] - minCost;
                    resultEach.sellIndexDay = j;
                } 
                if(stockPrices[i][j] < minCost) {
                    minCost = stockPrices[i][j];
                    resultEach.indexShare = i;
                    resultEach.buyIndexDay = j;
                }
            }
            if(resultEach.maxProfit > result.maxProfit) {
                result = new Result(resultEach);
            }
        }
        System.out.println("Max profit :" + result.maxProfit);
        result.indexShare = result.indexShare + 1;
        result.buyIndexDay = result.buyIndexDay + 1;
        result.sellIndexDay = result.sellIndexDay + 1;
        System.out.println(result.indexShare + " " + result.buyIndexDay + " " + result.sellIndexDay);
    }
}
