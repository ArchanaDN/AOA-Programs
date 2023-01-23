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
       //Initialize a new Result object to keep track of each stock's maxProfit
       //(Result object consists of stock, buydate, selldate and maxProfit)
        resultEach <- maxProfitEach(stockPrices[i], n)
        resultEach.indexShare <- i
        if resultEach.maxProfit is greater than result.maxProfit 
            set result to resultEach;
        end if
    end for
end

MaxProfitEach(stockPrices, n) 
    k <- 1
    initialize dpCache matrix
    for i <- 0 to n
        for j <- 0 to k + 1
            dpCache[i][j][0] <- -1000000000;
            dpCache[i][j][1] <- -1000000000;
        end for
    end for
    initialize buyday, sellday
    GetMaximumProfitEach(stockPricesEach, n - 1, 0, k)
    res <- 0
    for j <- 0 to k + 1
        res <- max(res, dpCache[n - 1][j][0])
    end for
    maxk <- 0;
    for i <- 0 to k + 1
        if res equals dpCache[n - 1][i][0]
            maxk <- i
        end if
    end for
    
    initialize resultantValue
    findTransactionDays(stockPricesEach, n - 1, maxk, 0)
    
    for i <- 0 to buyday.size
        resultantValue.buyIndexDay <- buyday.get(i)
        resultantValue.sellIndexDay <- sellday.get(i)
    end for
    if res equals 0
        resultantValue.buyIndexDay <- 0
        resultantValue.sellIndexDay <- 0
    else 
        resultantValue.buyIndexDay <- resultantValue.buyIndexDay
        resultantValue.sellIndexDay <- resultantValue.sellIndexDay
    end if
    resultantValue.maxProfit <- res
    return resultantValue

GetMaximumProfitEach(stockPricesEach, day, holdState, k)
    if day == 0
        if holdState == 0 and k == 0
            return dpCache[0][0][0] <- 0
        else if holdState == 1 and k == 1
            return dpCache[0][1][1] <- -stockPricesEach[0]
        else
            return dpCache[0][k][holdState] <- -1000000000
     end if
     
     if dpCache[day][k][holdState] is not equal to -1000000000
         return dpCache[day][k][holdState]
     end if
     
     if holdState == 1
         if k is greater than 0 
             return dpCache[day][k][holdState] <- max(
                     getMaximumProfitEach(stockPricesEach, day - 1, 0, k - 1) - stockPricesEach[day],
                     getMaximumProfitEach(stockPricesEach, day - 1, 1, k))
         else
             return -1000000000
         end if
     else
         return dpCache[day][k][holdState] <- max(
                 getMaximumProfitEach(stockPricesEach, day - 1, 1, k) + stockPricesEach[day],
                 getMaximumProfitEach(stockPricesEach, day - 1, 0, k))
     end if
    end
    

findTransactionDays(prices, day, k, holdstate)
    if day == 0
        return
    end if
    
    if holdstate == 0
        if dpCache[day][k][0] <- dpCache[day - 1][k][1] + prices[day]
            sellday.add(day);
            if day - 1 == 0
               buyday.add(day - 1)
            end if
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
