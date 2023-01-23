import java.util.Scanner;
import java.util.*;

public class ALG3A {

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
            // maxProfit(stockPrices);
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
        } catch (Exception e) {
            System.out.println("Wrong format of input given");
        } finally {
            input.close();
        }

    }

    public static void maxProfit(int[][] stockPrices) {
        int m = stockPrices.length;
        int n = stockPrices[0].length;
        Result result = new Result();
        result.maxProfit = 0;
        for (int i = 0; i < m; i++) {
            Result resultEach = maxProfitEach(stockPrices[i], n);
            resultEach.indexShare = i;
            if (resultEach.maxProfit > result.maxProfit) {
                result = new Result(resultEach);
            }
        }
        System.out.println("Max profit :" + result.maxProfit);
        result.indexShare = result.indexShare + 1;
        result.buyIndexDay = result.buyIndexDay + 1;
        result.sellIndexDay = result.sellIndexDay + 1;
        System.out.println(result.indexShare + " " + result.buyIndexDay + " " + result.sellIndexDay);
    }

    static int cache[][][];
    static ArrayList<Integer> buyday, sellday;

    private static Result maxProfitEach(int[] stockPricesEach, int n) {
        int k = 1;
        cache = new int[n][k + 1][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                cache[i][j][0] = -1000000000;
                cache[i][j][1] = -1000000000;
            }
        }
        buyday = new ArrayList<>();
        sellday = new ArrayList<>();

        getMaximumProfitEach(stockPricesEach, n - 1, 0, k);

        int res = 0;
        for (int j = 0; j <= k; j++) {
            res = Math.max(res, cache[n - 1][j][0]);
        }

        int maxk = 0;
        for (int i = 0; i <= k; i++) {
            if (res == cache[n - 1][i][0]) {
                maxk = i;
            }
        }

        Result resultantValue = new Result();
        findTransactionDays(stockPricesEach, n - 1, maxk, 0);

        for (int i = 0; i < buyday.size(); i++) {
            // System.out.println("Transac: " + buyday.get(i) + ", " + sellday.get(i));
            resultantValue.buyIndexDay = buyday.get(i);
            resultantValue.sellIndexDay = sellday.get(i);
        }
        if (res == 0) {
            resultantValue.buyIndexDay = 0;
            resultantValue.sellIndexDay = 0;
        } else {
            resultantValue.buyIndexDay = resultantValue.buyIndexDay;
            resultantValue.sellIndexDay = resultantValue.sellIndexDay;
        }
        resultantValue.maxProfit = res;
        return resultantValue;
    }

    public static void findTransactionDays(int[] prices, int day, int k, int holdstate) {
        if (day == 0)
            return;

        if (holdstate == 0) {
            if (cache[day][k][0] == cache[day - 1][k][1] + prices[day]) {
                sellday.add(day);
                if (day - 1 == 0)
                    buyday.add(day - 1);
                findTransactionDays(prices, day - 1, k, 1);
            } else {
                findTransactionDays(prices, day - 1, k, 0);
            }
        } else if (k > 0) {

            if (cache[day][k][1] == cache[day - 1][k - 1][0] - prices[day]) {
                buyday.add(day);
                findTransactionDays(prices, day - 1, k - 1, 0);
            } else {
                if (day - 1 == 0) {
                    buyday.add(day - 1);
                }
                findTransactionDays(prices, day - 1, k, 1);
            }
        }

    }

    private static int getMaximumProfitEach(int[] stockPricesEach, int day, int holdState, int k) {
        if (day == 0) {
            if (holdState == 0 && k == 0)
                return cache[0][0][0] = 0;
            else if (holdState == 1 && k == 1)
                return cache[0][1][1] = -stockPricesEach[0];
            else
                return cache[0][k][holdState] = -1000000000;
        }

        if(cache[day][k][holdState] != -1000000000) {
            return cache[day][k][holdState];
        }

        if (holdState == 1) {
            if (k > 0) {
                return cache[day][k][holdState] = Math.max(
                        getMaximumProfitEach(stockPricesEach, day - 1, 0, k - 1) - stockPricesEach[day],
                        getMaximumProfitEach(stockPricesEach, day - 1, 1, k));
            } else {
                return -1000000000;
            }
        } else {
            return cache[day][k][holdState] = Math.max(
                    getMaximumProfitEach(stockPricesEach, day - 1, 1, k) + stockPricesEach[day],
                    getMaximumProfitEach(stockPricesEach, day - 1, 0, k));
        }
    }
}