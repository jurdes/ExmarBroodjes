package broodjes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Bank {

    private final Map<String,Currency> users = new HashMap<String,Currency>();
    
    public Bank() {
    }
    
    public void addDebt(String payer, String buyer, Currency price) {
        if (!users.containsKey(payer)) {
            users.put(payer,Currency.ZERO);
        }
        if (!users.containsKey(buyer)) {
            users.put(buyer,Currency.ZERO);
        }
        
        if (payer.equals(buyer)) {
            return;
        }
        
        users.put(payer, users.get(payer).add(price.negate()));
        users.put(buyer, users.get(buyer).add(price));

    }
    
    public String toString() {
        StringBuffer result = new StringBuffer();
        List<Entry<String,Currency>> sortedUsers = new ArrayList<>(users.entrySet());
        Collections.sort(sortedUsers, new Comparator<Entry<String,Currency>>() {
			@Override
			public int compare(Entry<String, Currency> o1, Entry<String, Currency> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}});
        
        for (Entry<String,Currency> user : sortedUsers) {
        	if (user.getValue().isZero()){
            	result.append(user.getKey()+" has a clean slate\n");
            }  else if (user.getValue().isPositive()) {
                result.append(user.getKey()+" has a debt of "+user.getValue()+"\n");
            } else {
                result.append(user.getKey()+" has a credit of "+user.getValue().negate()+"\n");
            }
        }
        return result.toString();
    }

}
