package broodjes;
import java.util.HashMap;
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
        for (Entry<String,Currency> user : users.entrySet()) {
            if (user.getValue().isPositive()) {
                result.append(user.getKey()+" heeft "+user.getValue()+" schulden\n");
            } else {
                result.append(user.getKey()+" moet nog "+user.getValue().negate()+" krijgen\n");
            }
        }
        return result.toString();
    }

}
