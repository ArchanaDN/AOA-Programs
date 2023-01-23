import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ALG5 {

    public static int[][][] dpCache;

    public static void main(String[] args) {
            int m = 30;
            int k = 5;
            int n = 10;
            int stockPrices[][] = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    stockPrices[i][j] = (int) ((Math.random() * (1000 - 0)) + 0);
                }
            }
            long start = System.nanoTime();
            maxProfit(stockPrices, k);
            print_solution();
            long end = System.nanoTime();
            System.out.println("m = " + m + " and n = " + n + " and k = " + k + " takes " + (end - start) / 1000 + " micro seconds");
    }

    public static void maxProfit(int[][] stockPrices, int k) {
        int m = stockPrices.length;
        int n = stockPrices[0].length;
        dpCache = new int[k + 1][n][4];
       
        /*
         * Base cases : 0 transactions allowed
         */
        for (int j = 0; j < n; j++) {
            dpCache[0][j][0] = -1;
            dpCache[0][j][1] = -1;
            dpCache[0][j][2] = -1;
            dpCache[0][j][3] = 0;
        }

        /*
         * Base cases : No of days is 1
         */
        for (int j = 0; j < k + 1; j++) {
            dpCache[j][0][0] = -1;
            dpCache[j][0][1] = -1;
            dpCache[j][0][2] = -1;
            dpCache[j][0][3] = 0;
        }

        // Fill the dp table
        for (int i = 1; i < k + 1; i++) {
            for (int j = 1; j < n; j++) {
                int[] no_transaction_details = dpCache[i][j - 1];
                Result transaction_details = transaction(i, j, dpCache, stockPrices, m);
                int transaction_profit = transaction_details.maxProfit;
                int no_transaction_profit = no_transaction_details[3];
                if (transaction_profit > no_transaction_profit) {
                    dpCache[i][j][0] = transaction_details.indexShare;
                    dpCache[i][j][1] = transaction_details.buyIndexDay;
                    dpCache[i][j][2] = transaction_details.sellIndexDay;
                    dpCache[i][j][3] = transaction_details.maxProfit;
                } else {
                    dpCache[i][j] = no_transaction_details;
                }

            }
        }
        System.out.println("Max profit: " + dpCache[k][n - 1][3]);
    }

    public static Result transaction(int i, int j, int[][][] dpCache, int[][] stockPrices, int m) {
        Result res = new Result();
        res.maxProfit = 0;
        res.sellIndexDay = -1;
        res.buyIndexDay = -1;
        res.indexShare = -1;

        for (int x = 0; x < m; x++) {
            for (int y = 0; y < j; y++) {
                int profit = stockPrices[x][j] - stockPrices[x][y] + dpCache[i - 1][y][3];
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

    private static void print_solution() {
        int k = dpCache.length - 1;
        int n = dpCache[0].length;
        int i = k;
        int j = n - 1;
        List<List<Integer>> resList = new ArrayList<>();
        while (i >= 0 && j - 1 >= 0) {
            if (dpCache[i][j] != dpCache[i][j - 1]) {
                List<Integer> res = new ArrayList<>();
                res.add(dpCache[i][j][0] + 1);
                res.add(dpCache[i][j][1] + 1);
                res.add(dpCache[i][j][2] + 1);
                resList.add(res);
                j = dpCache[i][j][1];
                i -= 1;
            } else {
                j -= 1;
            }
        }
        Collections.reverse(resList);
        for(List<Integer> result : resList) {
            //System.out.println(result.get(0) + " " + result.get(1) + " " + result.get(2));
        }
    }
}
