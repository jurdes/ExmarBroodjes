package broodjes;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import sun.util.calendar.Gregorian;


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
                history.addEvent(order.getPayer(), order.getLineBuyer(i), order.getLineDate(i) , order.getLineName(i), order.getLinePrice(i));
            }
        }
        
        System.out.println(bank);
        System.out.println();
        
        //Calendar c=GregorianCalendar.getInstance();
        //c.add(Calendar.DAY_OF_YEAR, -1);
        //Date date = c.getTime();
        Date date = history.getLatestDate();
        
        System.out.println(history.toString(new LastDayHistoryValidator(date)));
        
        //URI mailUri = URI.create("mailto:jurgen.deswert@gmail.com?subject=First%20Email");
        //Desktop.getDesktop().mail(mailUri);
        
    }


}
