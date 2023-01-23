import java.util.*;
import java.util.Scanner;

public class ALG8 {
    public static int dpCache_2[][];

    public static void main(String[] args) {
        Scanner input = null;
        try {
            input = new Scanner(System.in);
            int m = 40;
            int c = 2;
            int n = 6;
            int stockPrices[][] = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    stockPrices[i][j] = (int) ((Math.random() * (1000 - 0)) + 0);
                }
            }
            long start = System.nanoTime();
            maxProfitStocks8(stockPrices, c);
            print_solution8(c);
            long end = System.nanoTime();
            System.out.println("m = " + m + " and n = " + n + " takes " + (end - start) / 1000 + " micro seconds");
        } catch (Exception e) {
            System.out.println("Wrong format of input given");
        } finally {
            input.close();
        }
    }

    public static void maxProfitStocks8(int[][] stockPrices, int c) {

        int m = stockPrices.length;
        int n = stockPrices[0].length;
        dpCache_2 = new int[n][4];

        /*
         * Base cases : No of days is 1
         */

        dpCache_2[0][0] = -1;
        dpCache_2[0][1] = -1;
        dpCache_2[0][2] = -1;
        dpCache_2[0][3] = 0;

        // Fill the dp table
        for (int j = 1; j < n; j++) {
            int[] no_transaction_details = dpCache_2[j - 1];
            Result transaction_details = transaction8(j, stockPrices, m, c);
            int transaction_profit = transaction_details.maxProfit;
            int no_transaction_profit = no_transaction_details[3];
            if (transaction_profit > no_transaction_profit) {
                dpCache_2[j][0] = transaction_details.indexShare;
                dpCache_2[j][1] = transaction_details.buyIndexDay;
                dpCache_2[j][2] = transaction_details.sellIndexDay;
                dpCache_2[j][3] = transaction_details.maxProfit;
            } else {
                dpCache_2[j] = no_transaction_details;
            }

        }
        System.out.println("Max profit: " + dpCache_2[n - 1][3]);

    }

    public static Result transaction8(int j, int[][] stockPrices, int m, int c) {

        Result res = new Result();
        res.maxProfit = 0;
        res.sellIndexDay = -1;
        res.buyIndexDay = -1;
        res.indexShare = -1;

        for (int x = 0; x < m; x++) {
            for (int y = 0; y < j; y++) {
                int dp_val;
                if ((y - c - 1) >= 0) {
                    dp_val = dpCache_2[y - c - 1][3];
                } else {
                    dp_val = 0;
                }
                int profit = stockPrices[x][j] - stockPrices[x][y] + dp_val;
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

    private static void print_solution8(int c) {
        int n = dpCache_2.length;
        int j = n - 1;
        List<List<Integer>> resList = new ArrayList<>();
        while (j >= 1 && j < n) {
            if (dpCache_2[j] != dpCache_2[j - 1]) {
                List<Integer> res = new ArrayList<>();
                res.add(dpCache_2[j][0] + 1);
                res.add(dpCache_2[j][1] + 1);
                res.add(dpCache_2[j][2] + 1);
                resList.add(res);
                j = dpCache_2[j][1] - c - 1;
            } else {
                j -= 1;
            }
        }
        Collections.reverse(resList);
        for (List<Integer> result : resList) {
            System.out.println(result.get(0) + " " + result.get(1) + " " + result.get(2));
        }

    }
}
