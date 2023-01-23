Declare dpCache[][][]
Declare List of buyday and List of sellday;

MaxProfit(stockPrices)
//Initialize m and n to the number of stocks and the number of days of the stockPrices matrix
   m <- number of stocks as per stockPrices
   n <- number of days as per stockPrices
   //Initialize a new Result object (Result object consists of stock, buydate, selldate and maxProfit)
   result <- new Result object
   //Initialize result's maxProfit to MIN_VALUE
   result.maxProfit <- 0
   for i <- 0 to m do
        k <- 1
        //Initialize a new Result object to keep track of each stock's maxProfit
        //(Result object consists of stock, buydate, selldate and maxProfit)
        resultEach <- maxProfitEach(stockPrices[i], n)
        resultEach.indexShare <- i
        if resultEach.maxProfit is greater than result.maxProfit 
            set result to resultEach;
        end if
    end for
end


MaxProfitEach(k, stockPricesEach, n)
   if n <= 0 or k <= 0
       return empty object
    
    initialize dpCache to matrix of dimentions n, k + 1, 2, buyday to empty List, sellday to empty List
    
    for i <- 0 to n do
        for j = 0 to k + 1 do
            dpCache[i][j][0] <- -1000000000
            dpCache[i][j][1] <- -1000000000
        end for
    end for
    
    dpCache[0][0][0] <- 0
    dpCache[0][1][1] <- -stockPricesEach[0]
    
    for i <- 1 to n do
        int j <- 0 to k + 1 do
            dpCache[i][j][0] <- max(dpCache[i - 1][j][0], dpCache[i - 1][j][1] + stockPricesEach[i])
            if j is greater than 0
                dpCache[i][j][1] <- max(dpCache[i - 1][j][1], dpCache[i - 1][j - 1][0] - stockPricesEach[i])
            end if
        end for
    end for
    res <- 0
    for j = 0 to k + 1 do 
        res <- max(res, dpCache[n - 1][j][0])
    end for

    int maxk <- 0

    for i <- 0 to k + 1
        if res equals to dpCache[n - 1][i][0]
            maxk <- i
        end if 
    end for
    initialize resultantValue to new Result object
    findTransactionDays(stockPricesEach, n - 1, maxk, 0)
    for i = 0 to buyday.size do
        resultantValue.buyIndexDay <- buyday.get(i)
        resultantValue.sellIndexDay <- sellday.get(i)
    end for
    resultantValue.maxProfit <- res
return resultantValue


findTransactionDays(prices, day, k, holdstate)
    if day == 0
        return
    end if
    
    if holdstate == 0
        if dpCache[day][k][0] <- dpCache[day - 1][k][1] + prices[day]
            sellday.add(day);
            findTransactionDays(prices, day - 1, k, 1)
        else 
            findTransactionDays(prices, day - 1, k, 0)
        end if
    else if k is greater 0
        if dpCache[day][k][1] == dpCache[day - 1][k - 1][0] - prices[day]
             buyday.add(day)
             findTransactionDays(prices, day - 1, k - 1, 0)
         else 
             if day - 1 == 0
                 buyday.add(day - 1)
             end if
             findTransactionDays(prices, day - 1, k, 1)
         end if
     end if
 end
