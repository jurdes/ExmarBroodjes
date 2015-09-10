package broodjes;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Broodjes {

    public static void main(String[] args) throws IOException, ParseException, URISyntaxException {
        
        File orderDir = new File ("D:/Broodjes");
        File[] orderFileArray = orderDir.listFiles();
        List<File> orderFiles = new ArrayList<File>(Arrays.asList(orderFileArray));
        
        Bank bank = new Bank();
        History history = new History();

        for (int fileNr = 0; fileNr<orderFiles.size(); fileNr++) {
        	File orderFile = orderFiles.get(fileNr);
        	if (orderFile.isDirectory()) {
        		orderFileArray = orderFile.listFiles();
        		orderFiles.addAll(Arrays.asList(orderFileArray));
        		continue;
        	}
            Order order = Order.readOrder(orderFile);
            for (int i=0; i<order.getNrOfLines(); i++) {
                bank.addDebt(order.getPayer(), order.getLineBuyer(i), order.getLinePrice(i));
                if (order.getLineBuyer(i).equals(order.getPayer())) {
                    history.addEvent(order.getLineBuyer(i), order.getLineDate(i) , "Je kocht "+order.getLineName(i)+" prijs: "+order.getLinePrice(i)+" voor jezelf", Currency.ZERO);
                } else {
                    history.addEvent(order.getPayer(),order.getLineDate(i) , "Je kocht "+order.getLineName(i)+" voor "+order.getLineBuyer(i)+" prijs : "+order.getLinePrice(i), order.getLinePrice(i));
                    history.addEvent(order.getLineBuyer(i),order.getLineDate(i) , order.getPayer()+" betaalde "+order.getLineName(i)+" prijs : "+order.getLinePrice(i)+" voor jou", order.getLinePrice(i).negate());
                }
            }
        }
        
        System.out.println(bank);
        
        System.out.println();
        
        System.out.println(history);
        
        //URI mailUri = URI.create("mailto:jurgen.deswert@gmail.com?subject=First%20Email");
        //Desktop.getDesktop().mail(mailUri);
        
    }


}
