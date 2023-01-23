import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ALG6A {

    public static int[][][] max_difference;
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
        System.out.println(
                "m = " + m + " and n = " + n + " and k = " + k + " takes " + (end - start) / 1000 + " micro seconds");
    }

    public static void maxProfit(int[][] stockPrices, int k) {
        int m = stockPrices.length;
        int n = stockPrices[0].length;
        max_difference = new int[k + 1][m][4];
        dpCache = new int[k + 1][n][4];

        for (int i = 0; i < k + 1; i++) {
            for (int j = 0; j < m; j++) {
                max_difference[i][j][0] = Integer.MIN_VALUE;
                max_difference[i][j][1] = Integer.MIN_VALUE;
                max_difference[i][j][2] = Integer.MIN_VALUE;
                max_difference[i][j][3] = Integer.MIN_VALUE;
            }
        }

        for (int i = 0; i < k + 1; i++) {
            for (int j = 0; j < n; j++) {
                dpCache[i][j][0] = Integer.MIN_VALUE;
                dpCache[i][j][1] = Integer.MIN_VALUE;
                dpCache[i][j][2] = Integer.MIN_VALUE;
                dpCache[i][j][3] = Integer.MIN_VALUE;
            }
        }

        maximize_profit(k, n - 1, stockPrices, m);
    }

    public static int[] maximize_profit(int i, int j, int[][] stockPrices, int m) {
        if (i == 0 || j == 0) {
            dpCache[i][j][0] = -1;
            dpCache[i][j][1] = -1;
            dpCache[i][j][2] = -1;
            dpCache[i][j][3] = 0;
            return dpCache[i][j];
        }

        if (dpCache[i][j][3] != Integer.MIN_VALUE) {
            return dpCache[i][j];
        }

        // No trasaction
        int[] no_transaction_details = maximize_profit(i, j - 1, stockPrices, m);

        Result res = new Result();
        res.maxProfit = 0;
        res.sellIndexDay = -1;
        res.buyIndexDay = -1;
        res.indexShare = -1;

        for (int x = 0; x < m; x++) {
            int difference = -stockPrices[x][j - 1] + maximize_profit(i - 1, j - 1, stockPrices, m)[3];
            if (difference > max_difference[i][x][0]) {
                max_difference[i][x][0] = difference;
                max_difference[i][x][1] = j - 1;
            }
            int profit = stockPrices[x][j] + max_difference[i][x][0];
            if (profit > res.maxProfit) {
                res.sellIndexDay = j;
                res.buyIndexDay = max_difference[i][x][1];
                res.indexShare = x;
            }
            res.maxProfit = Math.max(res.maxProfit, profit);
        }
        int transaction_profit = res.maxProfit;
        int no_transaction_profit = no_transaction_details[3];
        // transaction_details = [stock, buy_date, sell_date, max_profit]

        if (transaction_profit > no_transaction_profit) {
            dpCache[i][j][0] = res.indexShare;
            dpCache[i][j][1] = res.buyIndexDay;
            dpCache[i][j][2] = res.sellIndexDay;
            dpCache[i][j][3] = res.maxProfit;
            return dpCache[i][j];
        } else {
            dpCache[i][j] = no_transaction_details;
            return dpCache[i][j];
        }
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
        for (List<Integer> result : resList) {
            // System.out.println(result.get(0) + " " + result.get(1) + " " +
            // result.get(2));
        }
    }
}
