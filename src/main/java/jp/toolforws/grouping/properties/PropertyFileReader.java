package jp.toolforws.grouping.properties;

import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author omikankan
 */
public class PropertyFileReader {

    private Properties properties = new Properties();
    private Set keySet = new HashSet();

    public PropertyFileReader(String path) throws IOException {
        InputStream in = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(in, "UTF-8");
        properties.load(isr);
        keySet = properties.keySet();

        if(!(checkProperties())){
            System.out.println(" [ERROR] The following items are required in properties file: groupNumber, maxHeadCount, include");
            throw new FileFormatException();
        }
    }

    public HashMap<String, List<String>> getKeywords() {
        Iterator iterator = keySet.iterator();

        HashMap<String, List<String>> keywords = new HashMap<>();
        while(iterator.hasNext()){
            String key = (String)iterator.next();
            if(!(key.equals("groupNumber")) && !(key.equals("maxHeadCount")) && !(key.equals("include"))){
                String[] value = properties.get(key).toString().split(",");
                keywords.put(key, Arrays.asList(value));
            }
        }
        return keywords;
    }

    public int getGroupNumber(){
        return Integer.parseInt(properties.get("groupNumber").toString());
    }

    public int getMaxHeadCount(){
        return Integer.parseInt(properties.get("maxHeadCount").toString());
    }

    public String[] getInclude(){
        return properties.get("include").toString().split(",");
    }

    private boolean checkProperties(){
        boolean result = true;
        if(!(keySet.contains("groupNumber")) || !(keySet.contains("maxHeadCount")) || !(keySet.contains("include"))){
            result = false;
        }
        return result;
    }
}
