public class Result {
    int maxProfit;
    int indexShare;
    int buyIndexDay;
    int sellIndexShare;
    int sellIndexDay;

    Result() {

    }

    Result(Result res) {
        this.maxProfit = res.maxProfit;
        this.indexShare = res.indexShare;
        this.buyIndexDay = res.buyIndexDay;
        this.sellIndexDay = res.sellIndexDay;
    }
    
}
