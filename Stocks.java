import java.util.*;
import java.util.Scanner;

public class Stocks {

    public static int dpCache[][][];
    public static int dpCache_2[][];
    public static ArrayList<Integer> buyday, sellday;
    public static int[][][] max_difference;
    public static int[][] max_difference_2;
    public static int[][][] stockDetails;

    /**
     * 
     * @param args[0] Takes the task number as the command line input.
     */
    public static void main(String[] args) {
        Scanner input = null;
        try {
            // Create a Scanner object
            input = new Scanner(System.in);
            
            if (args.length != 1) {
                System.out.println("Invalid input.");
                System.out.println("Run: java Stocks task_number");
                return;
            }
            String alg = args[0];
            int k = 1;
            int c = 0;
            if ("4".equals(alg) || "5".equals(alg) || "6a".equals(alg) || "6b".equals(alg)) {
                System.out.print("Enter k : ");
                k = input.nextInt();
            }
            if ("7".equals(alg) || "8".equals(alg) || "9a".equals(alg) || "9b".equals(alg)) {
                System.out.print("Enter c : ");
                c = input.nextInt();
            }
            System.out.print("Enter m and n : ");
            int m = input.nextInt();
            int n = input.nextInt();

            // Declartion of the matrix based on user input
            int stockPrices[][] = new int[m][n];

            // Read the matrix values
            System.out.println("Enter the elements of the matrix : ");
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    stockPrices[i][j] = input.nextInt();

            // Display the elements of the matrix
            // System.out.println("Elements of the matrix are : ");
            // for (int i = 0; i < m; i++) {
            //     for (int j = 0; j < n; j++)
            //         System.out.print(stockPrices[i][j] + " ");
            //     System.out.println();
            // }

            switch (alg) {
                case "1":
                    maxProfitStocks1(stockPrices);
                    break;
                case "2":
                    maxProfitStocks2(stockPrices);
                    break;
                case "3a":
                    maxProfitStocks3a(stockPrices);
                    break;
                case "3b":
                    maxProfitStocks3b(stockPrices);
                    break;
                case "4":
                    maxProfitStocks4(stockPrices, k);
                    print_solution4();
                    break;
                case "5":
                    maxProfitStocks5(stockPrices, k);
                    print_solution5();
                    break;
                case "6a":
                    maxProfitStocks6a(stockPrices, k);
                    print_solution6a();
                    break;
                case "6b":
                    maxProfitStocks6b(stockPrices, k);
                    print_solution6b();
                    break;
                case "7":
                    maxProfitStocks7(stockPrices, c);
                    //print_solution7(c);
                    break;
                case "8":
                    maxProfitStocks8(stockPrices, c);
                    print_solution8(c);
                    break;
                case "9a":
                    maxProfitStocks9a(stockPrices, c);
                    print_solution9a(c);
                    break;
                case "9b":
                    maxProfitStocks9b(stockPrices, c);
                    print_solution9b(c);
                    break;
                default:
                    System.out.println("Invalid algorithm selection.");
                    break;
            }

        } catch (Exception e) {
            System.out.println("Wrong format of input given");
            e.printStackTrace();
        } finally {
            input.close();
        }
    }

    /**************************************************************************************************************
     * Algorithm 1
     * ************************************************************************************************************
     */
    /**
     * 
     * @param stockPrices input prices of m stocks for n days
     * Computes and prints the maximum profit. 
     * This is a brute force approach which checks all possible combinations of valid transactions
     *  (buydate < selldate) for each stock to get the maximum profit transaction
     */
    public static void maxProfitStocks1(int[][] stockPrices) {
        int m = stockPrices.length;
        int n = stockPrices[0].length;
        Result result = new Result();
        result.maxProfit = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            Result resultEach = new Result();
            resultEach.maxProfit = Integer.MIN_VALUE;
            for (int j = 0; j < n; j++) {
                int buyPrice = stockPrices[i][j];
                for (int k = j + 1; k < n; k++) {
                    int sellPrice = stockPrices[i][k];
                    if (sellPrice > buyPrice) {
                        int profit = sellPrice - buyPrice;
                        if (profit > resultEach.maxProfit) {
                            resultEach.maxProfit = profit;
                            resultEach.indexShare = i;
                            resultEach.buyIndexDay = j;
                            resultEach.sellIndexDay = k;
                        }
                    }
                }
            }
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

    /**************************************************************************************************************
     * Algorithm 2
     * ************************************************************************************************************
     * 
     * @param stockPrices input prices of m stocks for n days
     * Computes and prints the maximum profit.
     * This is a greedy appraoch which checks the maximum profit transaction of each
     * stock to prints the max transaction for all the stocks.
     */

    public static void maxProfitStocks2(int[][] stockPrices) {
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
            for (int j = 1; j < n; j++) {
                if (stockPrices[i][j] - minCost > result.maxProfit) {
                    resultEach.maxProfit = stockPrices[i][j] - minCost;
                    resultEach.sellIndexDay = j;
                }
                if (stockPrices[i][j] < minCost) {
                    minCost = stockPrices[i][j];
                    resultEach.indexShare = i;
                    resultEach.buyIndexDay = j;
                }
            }
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

    /**************************************************************************************************************
     * Algorithm 3a - DP method with memoization
     * ************************************************************************************************************
     */

     /*
      * @param stockPrices input prices of m stocks for n days prints the maximum profit by calling 
      the function to get the maximum profit for each stock and by keeping track of all the 
      stock's max profit
      */
    public static void maxProfitStocks3a(int[][] stockPrices) {
        int m = stockPrices.length;
        int n = stockPrices[0].length;
        Result result = new Result();
        result.maxProfit = 0;
        for (int i = 0; i < m; i++) {
            Result resultEach = maxProfitEach3a(stockPrices[i], n, i);
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

     /*
      * This method will accept each stock array, length of the array
      and the current stock index and outputs the maximum profit for this stock
      */
    private static Result maxProfitEach3a(int[] stockPricesEach, int n, int shareIndex) {
        int k = 1;
        dpCache = new int[n][k + 1][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                dpCache[i][j][0] = -1000000000;
                dpCache[i][j][1] = -1000000000;
            }
        }
        buyday = new ArrayList<>();
        sellday = new ArrayList<>();

        getMaximumProfitEach3a(stockPricesEach, n - 1, 0, k);

        int res = 0;
        for (int j = 0; j <= k; j++) {
            res = Math.max(res, dpCache[n - 1][j][0]);
        }

        int maxk = 0;
        for (int i = 0; i <= k; i++) {
            if (res == dpCache[n - 1][i][0]) {
                maxk = i;
            }
        }

        Result resultantValue = new Result();
        findTransactionDays3a(stockPricesEach, n - 1, maxk, 0);

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


    /*
      * This is a recursive method which will accept each stock array, current transaction day,
      holdState(0 or 1 depending on whether the stock is previously bought or not) and k which
      is the transaction index to backtrack through the dpCache matrix to get indices of maximum
      transaction
      */
    public static void findTransactionDays3a(int[] prices, int day, int k, int holdstate) {
        if (day == 0)
            return;

        if (holdstate == 0) {
            if (dpCache[day][k][0] == dpCache[day - 1][k][1] + prices[day]) {
                sellday.add(day);
                if (day - 1 == 0)
                    buyday.add(day - 1);
                findTransactionDays3a(prices, day - 1, k, 1);
            } else {
                findTransactionDays3a(prices, day - 1, k, 0);
            }
        } else if (k > 0) {

            if (dpCache[day][k][1] == dpCache[day - 1][k - 1][0] - prices[day]) {
                buyday.add(day);
                findTransactionDays3a(prices, day - 1, k - 1, 0);
            } else {
                if (day - 1 == 0) {
                    buyday.add(day - 1);
                }
                findTransactionDays3a(prices, day - 1, k, 1);
            }
        }

    }

    /*
      * This is a recursive method which will accept each stock array, current transaction day,
      holdState(0 or 1 depending on whether the stock is previously bought or not) and k which
      is the transaction index.
      */
    private static int getMaximumProfitEach3a(int[] stockPricesEach, int day, int holdState, int k) {
        if (day == 0) {
            if (holdState == 0 && k == 0)
                return dpCache[0][0][0] = 0;
            else if (holdState == 1 && k == 1)
                return dpCache[0][1][1] = -stockPricesEach[0];
            else
                return dpCache[0][k][holdState] = -1000000000;
        }

        if (dpCache[day][k][holdState] != -1000000000) {
            return dpCache[day][k][holdState];
        }

        if (holdState == 1) {
            if (k > 0) {
                return dpCache[day][k][holdState] = Math.max(
                        getMaximumProfitEach3a(stockPricesEach, day - 1, 0, k - 1) - stockPricesEach[day],
                        getMaximumProfitEach3a(stockPricesEach, day - 1, 1, k));
            } else {
                return -1000000000;
            }
        } else {
            return dpCache[day][k][holdState] = Math.max(
                    getMaximumProfitEach3a(stockPricesEach, day - 1, 1, k) + stockPricesEach[day],
                    getMaximumProfitEach3a(stockPricesEach, day - 1, 0, k));
        }
    }

    /**************************************************************************************************************
     * Algorithm 3b - Bottom up DP algorithm
     * ************************************************************************************************************
     */

     /*
      * @param stockPrices input prices of m stocks for n days prints the maximum profit by calling 
      the function to get the maximum profit for each stock and by keeping track of all the 
      stock's max profit
      */
    public static void maxProfitStocks3b(int[][] stockPrices) {
        int m = stockPrices.length;
        Result result = new Result();
        result.maxProfit = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            int k = 1;
            Result resultEach = maxProfitEach3b(k, stockPrices[i]);
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

    /*
      * This method will accept each stock array, k (transaction index)
      and the current stock index and outputs the maximum profit for this stock
      It is an iterative DP approach
      */
    public static Result maxProfitEach3b(int k, int[] stockPricesEach) {
        int n = stockPricesEach.length;

        // Base case
        if (n <= 0 || k <= 0) {
            return new Result();
        }

        // dpCache[day][used_k][ishold] = balance
        // ishold: 0 nothold, 1 hold
        dpCache = new int[n][k + 1][2];
        buyday = new ArrayList<>();
        sellday = new ArrayList<>();

        // initialize the array with -infinity
        // we use -1e9 here to prevent overflow
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                dpCache[i][j][0] = -1000000000;
                dpCache[i][j][1] = -1000000000;
            }
        }

        // set starting value
        dpCache[0][0][0] = 0;
        dpCache[0][1][1] = -stockPricesEach[0];

        // fill the dpCache array
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                // transition equation
                dpCache[i][j][0] = Math.max(dpCache[i - 1][j][0], dpCache[i - 1][j][1] + stockPricesEach[i]);
                // you can't hold stock without any transaction
                if (j > 0) {
                    dpCache[i][j][1] = Math.max(dpCache[i - 1][j][1], dpCache[i - 1][j - 1][0] - stockPricesEach[i]);
                }
            }
        }

        int res = 0;
        for (int j = 0; j <= k; j++) {
            res = Math.max(res, dpCache[n - 1][j][0]);
        }

        int maxk = 0;
        for (int i = 0; i <= k; i++) {
            if (res == dpCache[n - 1][i][0]) {
                maxk = i;
            }
        }
        Result resultantValue = new Result();
        findTransactionDays3b(stockPricesEach, n - 1, maxk, 0);
        for (int i = 0; i < buyday.size(); i++) {
            resultantValue.buyIndexDay = buyday.get(i);
            resultantValue.sellIndexDay = sellday.get(i);
        }
        resultantValue.maxProfit = res;
        return resultantValue;
    }

     /*
      * This is a recursive method which will accept each stock array, current transaction day,
      holdState(0 or 1 depending on whether the stock is previously bought or not) and k which
      is the transaction index.
      */
    public static void findTransactionDays3b(int[] prices, int day, int k, int holdstate) {
        if (day == 0)
            return;

        if (holdstate == 0) {

            // dpCache[i][j][0] = Math.max(dpCache[i - 1][j][0], dpCache[i - 1][j][1] +
            // prices[i]);

            if (dpCache[day][k][0] == dpCache[day - 1][k][1] + prices[day]) {
                sellday.add(day);
                findTransactionDays3b(prices, day - 1, k, 1);
            } else {
                findTransactionDays3b(prices, day - 1, k, 0);
            }
        } else if (k > 0) {

            // dpCache[i][j][1] = Math.max(dpCache[i - 1][j][1], dpCache[i - 1][j - 1][0] -
            // prices[i]);

            if (dpCache[day][k][1] == dpCache[day - 1][k - 1][0] - prices[day]) {
                buyday.add(day);
                findTransactionDays3b(prices, day - 1, k - 1, 0);
            } else {
                if (day - 1 == 0) {
                    buyday.add(day);
                }
                findTransactionDays3b(prices, day - 1, k, 1);
            }
        }

    }


    /**************************************************************************************************************
     * Algorithm 4
     * ************************************************************************************************************
     */


    public static void maxProfitStocks4(int[][] stockPrices, int k) {
        int m = stockPrices.length;
        int n = stockPrices[0].length;
        stockDetails = new int[k+1][n][3];

        int maxProfit = maximize_profit4(k, n - 1, stockPrices, m);

        System.out.println("Max profit: " + maxProfit);
 
    }

    public static int maximize_profit4(int i, int j, int prices[][], int m) {

        // base cases
        if (i == 0 || j == 0) {
            stockDetails[i][j][0] = -1;
            stockDetails[i][j][1] = -1;
            stockDetails[i][j][2] = -1;
            return 0;
        }

        // fill the rest of the table
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
        System.out.println("Output:");
        for(List<Integer> result : resList) {
            System.out.println("Output:");
            System.out.println(result.get(0) + " " + result.get(1) + " " + result.get(2));
        }
    }

    /**************************************************************************************************************
     * Algorithm 5
     * ************************************************************************************************************
     */

    public static void maxProfitStocks5(int[][] stockPrices, int k) {
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
                Result transaction_details = transaction5(i, j, stockPrices, m);
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

    public static Result transaction5(int i, int j, int[][] stockPrices, int m) {
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

    private static void print_solution5() {
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
        System.out.println("Output:");
        for (List<Integer> result : resList) {
            System.out.println(result.get(0) + " " + result.get(1) + " " + result.get(2));
        }
    }

    /**************************************************************************************************************
     * Algorithm 6a
     * ************************************************************************************************************
     */
    public static void maxProfitStocks6a(int[][] stockPrices, int k) {
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

        maximize_profit6a(k, n - 1, stockPrices, m);
    }

    public static int[] maximize_profit6a(int i, int j, int[][] stockPrices, int m) {
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
        int[] no_transaction_details = maximize_profit6a(i, j - 1, stockPrices, m);

        Result res = new Result();
        res.maxProfit = 0;
        res.sellIndexDay = -1;
        res.buyIndexDay = -1;
        res.indexShare = -1;

        for (int x = 0; x < m; x++) {
            int difference = -stockPrices[x][j - 1] + maximize_profit6a(i - 1, j - 1, stockPrices, m)[3];
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

    private static void print_solution6a() {
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
        System.out.println("Output:");
        for (List<Integer> result : resList) {
            System.out.println(result.get(0) + " " + result.get(1) + " " + result.get(2));
        }
    }

    public static void maxProfitStocks7(int[][] stockPrices, int c) {
        int m = stockPrices.length;
        int n = stockPrices[0].length;

        int max_profit = maximizeProfit7(0, true, -1, m, n, stockPrices, c);

        System.out.println("Maximum profit: " + max_profit);
    }

    /**************************************************************************************************************
    * Algorithm 7
    * ************************************************************************************************************
    */

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

    /**************************************************************************************************************
     * Algorithm 8
     * ************************************************************************************************************
     */

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
            for (int y = 0; y < j; y++) { // y goes from 0 to j-1-C
                int dp_val;
                if ((y - c - 1) >= 0) {
                    dp_val = dpCache_2[y - c - 1][3];
                } else {
                    dp_val = 0;
                }
                int profit = stockPrices[x][j] - stockPrices[x][y] + dp_val;
                // keep track of sell date, buy date and stock
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
        System.out.println("Output:");
        for (List<Integer> result : resList) {
            System.out.println(result.get(0) + " " + result.get(1) + " " + result.get(2));
        }
    }


    /**************************************************************************************************************
    * Algorithm 9a
    *************************************************************************************************************
    */

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
            return new int[] {-1, -1, -1, 0};
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

        // Make Transaction
        for (int x = 0; x < m; x++) {
            int difference = -stockPrices[x][j - 1] + maximize_profit9a(j - c - 2, stockPrices, m, c)[3];
            if (difference > max_difference_2[x][0]) {
                // update max diff and corresponding buy date
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
        Collections.reverse(resList);
        System.out.println("Output:");
        for (List<Integer> result : resList) {
            System.out.println(result.get(0) + " " + result.get(1) + " " + result.get(2));
        }
    }

    /**************************************************************************************************************
    * Algorithm 9b
    *************************************************************************************************************
    */

    public static void maxProfitStocks9b(int[][] stockPrices, int c) {
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

        // [difference,sell_date] is stored in each cell
        max_difference_2 = new int[m][2];

        for (int j = 0; j < m; j++) {
            max_difference_2[j][0] = Integer.MIN_VALUE;
            max_difference_2[j][1] = -1;
        }

        // Fill the dp table
        for (int j = 1; j < n; j++) {
            int[] no_transaction_details = dpCache_2[j - 1];
            Result transaction_details = transaction9b(j, stockPrices, m, c);
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

    public static Result transaction9b(int j, int[][] stockPrices, int m, int c) {
        Result res = new Result();
        res.maxProfit = 0;
        res.sellIndexDay = -1;
        res.buyIndexDay = -1;
        res.indexShare = -1;
        for (int x = 0; x < m; x++) {
            int dp_val;
            if ((j - c - 1) >= 0) {
                dp_val = dpCache_2[j - c - 1][3];
            } else {
                dp_val = 0;
            }
            int difference = -stockPrices[x][j - 1] + dp_val;
            if (difference > max_difference_2[x][0]) {
                // update max diff and corresponding buy date
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
        return res;
    }

    private static void print_solution9b(int c) {
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
        System.out.println("Output:");
        for (List<Integer> result : resList) {
            System.out.println(result.get(0) + " " + result.get(1) + " " + result.get(2));
        }
    }

    /**************************************************************************************************************
     * Algorithm 6b
     * ************************************************************************************************************
     */

    public static void maxProfitStocks6b(int[][] stockPrices, int k) {
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

        // [difference,sell_date] is stored in each cell
        int[][][] max_diff = new int[k + 1][m][2];

        for (int i = 0; i < k + 1; i++) {
            for (int j = 0; j < m; j++) {
                max_diff[i][j][0] = Integer.MIN_VALUE;
                max_diff[i][j][1] = -1;
            }
        }

        // Fill the dp table
        for (int i = 1; i < k + 1; i++) {
            for (int j = 1; j < n; j++) {
                int[] no_transaction_details = dpCache[i][j - 1];
                Result transaction_details = transaction6b(i, j, stockPrices, m, max_diff);
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

    public static Result transaction6b(int i, int j, int[][] stockPrices, int m,
            int[][][] max_difference) {
        Result res = new Result();
        res.maxProfit = 0;
        res.sellIndexDay = -1;
        res.buyIndexDay = -1;
        res.indexShare = -1;
        for (int x = 0; x < m; x++) {
            int difference = -stockPrices[x][j - 1] + dpCache[i - 1][j - 1][3];
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
        return res;
    }

    private static void print_solution6b() {
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
        System.out.println("Output:");
        for (List<Integer> result : resList) {
            System.out.println(result.get(0) + " " + result.get(1) + " " + result.get(2));
        }
    }

}
