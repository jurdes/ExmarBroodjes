package broodjes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ListMap<KT, LT extends Comparable<LT>> {
    
    public ListMap() {
    }
    
    private Map<KT, List<LT>> items = new HashMap<KT, List<LT>>(); 

    public void add(KT key, LT item) {
        List<LT> list = items.get(key);
        if (list == null) {
            list = new ArrayList<LT>();
            items.put(key, list);
        }
        list.add(item);
        Collections.sort(list);
    }

    public Set<Entry<KT, List<LT>>> entrySet() {
        return items.entrySet();
    }

}
