import java.util.Scanner;

public class ALG7 {

    public static void main(String[] args) {
        Scanner input = null;
        try {
            input = new Scanner(System.in);
            int m = 10;
            int c = 2;
            int n = 6;
            int stockPrices[][] = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    stockPrices[i][j] = (int) ((Math.random() * (1000 - 0)) + 0);
                }
            }
            long start = System.nanoTime();
            maxProfitStocks7(stockPrices, c);
            long end = System.nanoTime();
            System.out.println("m = " + m + " and n = " + n + " and c = " + c + " takes " + (end - start) / 1000 + " micro seconds");
        } catch (Exception e) {
            System.out.println("Wrong format of input given");
        } finally {
            input.close();
        }
    }

    public static void maxProfitStocks7(int[][] stockPrices, int c) {
        int m = stockPrices.length;
        int n = stockPrices[0].length;

        int max_profit = maximizeProfit7(0, true, -1, m, n, stockPrices, c);

        System.out.println("Maximum profit: " + max_profit);
    }

    public static int maximizeProfit7(int j, boolean buy, int boughtStock, int m, int n, int[][] stockPrices, int c) {

        if (j >= n) {
            return 0;
        }

        int maxProfit = Integer.MIN_VALUE;
        if (buy) {
            for (int i = 0; i < m; i++) {
                int profit = -stockPrices[i][j] + maximizeProfit7(j + 1, false, i, m, n, stockPrices, c);
                int currProfit = Math.max(profit, maximizeProfit7(j + 1, true, boughtStock, m, n, stockPrices, c));
                maxProfit = Math.max(maxProfit, currProfit);
            }
        } else {
            int profit = stockPrices[boughtStock][j] + maximizeProfit7(j+c+1, true, -1, m, n, stockPrices, c);
            maxProfit = Math.max(profit, maximizeProfit7(j + 1, false, boughtStock, m, n, stockPrices, c));
        }

        return maxProfit;
    }

}
