public class SalesOrderData {

    private int orderID;
    private String orderDate;
    private int custmerID;
    private Double totalAmount;

    public SalesOrderData(int orderID, String orderDate, int custmerID, Double totalAmount) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.custmerID = custmerID;
        this.totalAmount = totalAmount;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getCustmerID() {
        return custmerID;
    }

    public void setCustmerID(int custmerID) {
        this.custmerID = custmerID;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }



    
    
}
