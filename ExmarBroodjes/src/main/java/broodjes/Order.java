package broodjes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private String payer = "";
    private Currency reduction = Currency.ZERO;
    
    private List<OrderLine> orderLines = new ArrayList<OrderLine>();
    private List<Date> orderLineDates = new ArrayList<Date>();
    
    public Order() {
        
    }
    
    public String getPayer() {
        return payer;
    }
    
    private void addReduction(Currency reduction) {
        this.reduction = this.reduction.add(reduction);
    }
    
    private void addOrderLine(Date date, OrderLine orderLine) {
        orderLines.add(orderLine);
        orderLineDates.add(date);
    }

    private void setPayer(String payer) {
        this.payer = payer;
    }
    
    public Currency getTotal() {
        Currency total = Currency.ZERO;
        for (OrderLine line : orderLines) {
            total = total.add(line.getPrice());
        }
        return total;
    }
    
    public Currency getLinePrice(int lineNr) {
        OrderLine line = orderLines.get(lineNr);
        Currency lineReduction = getIndividualReduction(getTotal(), reduction, line.getPrice());
        return line.getPrice().add(lineReduction);
    }
    
    public int getNrOfLines() {
        return orderLines.size();
    }

    public String getLineName(int lineNr) {
        return orderLines.get(lineNr).getName();
    }
    
    public String getLineBuyer(int lineNr) {
        return orderLines.get(lineNr).getBuyer();
    }
    
    private static Currency getIndividualReduction(Currency total, Currency reduction, Currency amount) {
        return reduction.multiply(amount).divide(total);
    }
    
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("Payer: ["+payer+"]\n");
        result.append("Reduction: ["+reduction+"]\n");
        result.append("Total: ["+getTotal()+"]\n");
        result.append("Total with reduction:["+(getTotal().add(reduction))+"]\n");
        for (OrderLine line : orderLines) {
            Currency lineReduction = getIndividualReduction(getTotal(), reduction, line.getPrice());
            Currency realPrice = line.getPrice().add(lineReduction);
            result.append(line.toString()+" reduction:["+ lineReduction + "] price-red:["+realPrice+"]\n");
        }
        return result.toString();
    }

    public Date getLineDate(int i) {
        return orderLineDates.get(i);
    }

    public static Order readOrder(File orderFile) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(orderFile), "Cp1250"));
            
            String fileName = orderFile.getName();
            Date orderDate = null;
            if (fileName.indexOf("betaalt")>=0) {
                // date is in linedescription
            } else {
                // date is in filename
                String dateString = fileName.substring(0, fileName.indexOf('.'));
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                orderDate = format.parse(dateString);
            }
            
            Order order = new Order();
            try {
                String line = br.readLine();
                String payer = line.trim();
                order.setPayer(payer);
                line = br.readLine();
                while (line != null) {
                    line = line.trim();
                    if (line.indexOf("Korting")>=0) {
                        order.addReduction(getPrice(line));
                    } else if (!line.isEmpty()){
                        if (orderDate!=null) {
                            order.addOrderLine(orderDate, parseOrderLine(line.trim()));
                        } else {
                            int indexOf = line.indexOf('-');
                            String dateString = line.substring(indexOf-4, indexOf+6);
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Date orderLineDate = format.parse(dateString);
                            order.addOrderLine(orderLineDate, parseOrderLine(line.trim()));
                        }
                    }
                    line = br.readLine();
                }
            } finally {
                br.close();
            }
            
            return order;
        } catch (Exception e) {
            throw new RuntimeException("Could not read order from ["+orderFile+"]", e);
        }
    }
    
    private static OrderLine parseOrderLine(String line) {
        String buyer = getName(line);
        Currency price = getPrice(line);
        String description = Translator.translateSentence(getDescription(line));
        return new OrderLine(buyer, description, price);
    }

    public static String getName(String line) {
        int priceStart = line.indexOf("(");
        int nextHyphen = line.indexOf(" - ", priceStart+1) +1;
        int nextEnd = line.indexOf(")", priceStart+1);
        if (nextHyphen<nextEnd && nextHyphen>0) {
            String buyer = line.substring(nextHyphen+1, nextEnd).trim();
            return buyer;
        } else {
            int buyerEnd = line.indexOf(" ");
            String buyer = line.substring(0, buyerEnd);
            return buyer;
        }
    }
    
    public static Currency getPrice(String line) {
        int priceStart = line.indexOf("(");
        int nextHyphen = line.indexOf(" - ", priceStart+1) +1;
        int nextEnd = line.indexOf(")", priceStart+1);
        int priceEnd = nextHyphen > 0 ? Math.min(nextHyphen, nextEnd) : nextEnd; 
        String price = line.substring(priceStart+3,priceEnd).trim();
        price = price.replace(',', '.');
        return new Currency((int) Math.round(Double.valueOf(price)*100.0));
    }
        
    public static String getDescription(String line) {
        if (line.indexOf("terugbetaling")>=0) {
            return "terugbetaling";
        }
        int descriptionStart = line.indexOf("x")+1;
        int nextStart = line.indexOf("(", descriptionStart+1);
        return line.substring(descriptionStart, nextStart).trim();
    }
    
}
