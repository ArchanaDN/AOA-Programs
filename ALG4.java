import java.util.*;
import java.util.Scanner;

public class ALG4 {

    public static int[][][] stockDetails;

    public static void main(String[] args) {
        Scanner input = null;
        try {
            input = new Scanner(System.in);
            int m = 30;
            int k = 1;
            int n = 10;
            int stockPrices[][] = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    stockPrices[i][j] = (int) ((Math.random() * (1000 - 0)) + 0);
                }
            }
            long start = System.nanoTime();
            maxProfitStocks4(stockPrices, k);
            //print_solution4();
            long end = System.nanoTime();
            System.out.println("m = " + m + " and n = " + n + " and k = " + k + " takes " + (end - start) / 1000 + " micro seconds");
        } catch (Exception e) {
            System.out.println("Wrong format of input given");
        } finally {
            input.close();
        }
    }

    public static void maxProfitStocks4(int[][] stockPrices, int k) {
        int m = stockPrices.length;
        int n = stockPrices[0].length;
        stockDetails = new int[k+1][n][3];

        int maxProfit = maximize_profit4(k, n - 1, stockPrices, m);

        System.out.println("Max profit: " + maxProfit);
 
    }

    public static int maximize_profit4(int i, int j, int prices[][], int m) {

        if (i == 0 || j == 0) {
            stockDetails[i][j][0] = -1;
            stockDetails[i][j][1] = -1;
            stockDetails[i][j][2] = -1;
            return 0;
        }

        int noTransactionProfit = maximize_profit4(i, j-1, prices, m);
        Result transactionDetails = transaction4(i, j, prices, m);
        int transactionProfit = transactionDetails.maxProfit;
        if (transactionProfit > noTransactionProfit) {
            stockDetails[i][j][0] = transactionDetails.indexShare;
            stockDetails[i][j][1] = transactionDetails.buyIndexDay;
            stockDetails[i][j][2] = transactionDetails.sellIndexDay;
            return transactionProfit;
        } else {
            stockDetails[i][j] = stockDetails[i][j-1];
            return noTransactionProfit;
        }

    }

    public static Result transaction4(int i, int j, int[][] stockPrices, int m) {
        Result res = new Result();
        res.maxProfit = 0;
        res.sellIndexDay = -1;
        res.buyIndexDay = -1;
        res.indexShare = -1;

        for (int x = 0; x < m; x++) {
            for (int y = 0; y < j; y++) {
                int profit = stockPrices[x][j] - stockPrices[x][y]  + maximize_profit4(i-1, y, stockPrices, m);
                if (profit > res.maxProfit) {
                    res.sellIndexDay = j;
                    res.buyIndexDay = y;
                    res.indexShare = x;
                }
                res.maxProfit = Math.max(res.maxProfit, profit);
            }
        }
        return res;
    }

    private static void print_solution4() {
        int k = stockDetails.length - 1;
        int n = stockDetails[0].length;
        int i = k;
        int j = n - 1;
        List<List<Integer>> resList = new ArrayList<>();

        while (i >= 0 && j - 1 >= 0) {
            if (stockDetails[i][j] != stockDetails[i][j - 1]) {
                List<Integer> res = new ArrayList<>();
                res.add(stockDetails[i][j][0] + 1);
                res.add(stockDetails[i][j][1] + 1);
                res.add(stockDetails[i][j][2] + 1);
                resList.add(res);
                j = stockDetails[i][j][1];
                i -= 1;
            } else {
                j -= 1;
            }
        }
        Collections.reverse(resList);
        for(List<Integer> result : resList) {
            System.out.println(result.get(0) + " " + result.get(1) + " " + result.get(2));
        }
    }

}
