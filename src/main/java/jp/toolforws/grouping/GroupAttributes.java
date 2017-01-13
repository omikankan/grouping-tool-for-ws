package jp.toolforws.grouping;

import java.util.HashMap;
import java.util.List;

/**
 * @author omikankan
 */
public class GroupAttributes {
    HashMap<String, HashMap<String, Integer>> attributes;

    public GroupAttributes(HashMap<String, List<String>> keywords){
        attributes = new HashMap<>();
        for(String key : keywords.keySet()){
            HashMap<String, Integer> valueMap = new HashMap<>();
            for(String valueName : keywords.get(key)){
                valueMap.put(valueName, 0);
            }
            attributes.put(key, valueMap);
        }
    }

    public void incrementHeadcount(String mainKey, String subKey){
        if (attributes.containsKey(mainKey) && attributes.get(mainKey).containsKey(subKey)) {
            Integer oldHeadCount = attributes.get(mainKey).get(subKey);
            int headCount = oldHeadCount.intValue() + 1;
            attributes.get(mainKey).put(subKey, headCount);
        }
    }
}
