import java.util.*;
import java.util.Scanner;

public class ALG9A {
    public static int dpCache_2[][];
    public static int[][] max_difference_2;

    public static void main(String[] args) {
        Scanner input = null;
        try {
            input = new Scanner(System.in);
            int m = 50;
            int c = 2;
            int n = 6;
            int stockPrices[][] = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    stockPrices[i][j] = (int) ((Math.random() * (1000 - 0)) + 0);
                }
            }
            long start = System.nanoTime();
            maxProfitStocks9a(stockPrices, c);
            //print_solution9a(c);
            long end = System.nanoTime();
            System.out.println("m = " + m + " and n = " + n + " takes " + (end - start) / 1000 + " micro seconds");
        } catch (Exception e) {
            System.out.println("Wrong format of input given");
        } finally {
            input.close();
        }
    }

    public static void maxProfitStocks9a(int[][] stockPrices, int c) {
        int m = stockPrices.length;
        int n = stockPrices[0].length;
        max_difference_2 = new int[m][2];
        dpCache_2 = new int[n][4];

        for (int j = 0; j < m; j++) {
            max_difference_2[j][0] = Integer.MIN_VALUE;
            max_difference_2[j][1] = -1;
        }

        for (int j = 0; j < n; j++) {
            dpCache_2[j][0] = Integer.MIN_VALUE;
            dpCache_2[j][1] = Integer.MIN_VALUE;
            dpCache_2[j][2] = Integer.MIN_VALUE;
            dpCache_2[j][3] = Integer.MIN_VALUE;
        }

        maximize_profit9a(n - 1, stockPrices, m, c);
    }

    public static int[] maximize_profit9a(int j, int[][] stockPrices, int m, int c) {
        if (j == 0) {
            dpCache_2[j][0] = -1;
            dpCache_2[j][1] = -1;
            dpCache_2[j][2] = -1;
            dpCache_2[j][3] = 0;
            return dpCache_2[j];
        } else if (j < 0) {
            return new int[] { -1, -1, -1, 0 };
        }

        if (dpCache_2[j][3] != Integer.MIN_VALUE) {
            return dpCache_2[j];
        }

        // No trasaction
        int[] no_transaction_details = maximize_profit9a(j - 1, stockPrices, m, c);

        Result res = new Result();
        res.maxProfit = 0;
        res.sellIndexDay = -1;
        res.buyIndexDay = -1;
        res.indexShare = -1;

        for (int x = 0; x < m; x++) {
            int difference = -stockPrices[x][j - 1] + maximize_profit9a(j - c - 2, stockPrices, m, c)[3];
            if (difference > max_difference_2[x][0]) {
                max_difference_2[x][0] = difference;
                max_difference_2[x][1] = j - 1;
            }
            int profit = stockPrices[x][j] + max_difference_2[x][0];
            if (profit > res.maxProfit) {
                res.sellIndexDay = j;
                res.buyIndexDay = max_difference_2[x][1];
                res.indexShare = x;
            }
            res.maxProfit = Math.max(res.maxProfit, profit);
        }
        int transaction_profit = res.maxProfit;
        int no_transaction_profit = no_transaction_details[3];
        // transaction_details = [stock, buy_date, sell_date, max_profit]

        if (transaction_profit > no_transaction_profit) {
            dpCache_2[j][0] = res.indexShare;
            dpCache_2[j][1] = res.buyIndexDay;
            dpCache_2[j][2] = res.sellIndexDay;
            dpCache_2[j][3] = res.maxProfit;
            return dpCache_2[j];
        } else {
            dpCache_2[j] = no_transaction_details;
            return dpCache_2[j];
        }
    }

    private static void print_solution9a(int c) {
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
        // Collections.reverse(resList);
        // for (List<Integer> result : resList) {
        //     System.out.println(result.get(0) + " " + result.get(1) + " " + result.get(2));
        // }
    }
}
