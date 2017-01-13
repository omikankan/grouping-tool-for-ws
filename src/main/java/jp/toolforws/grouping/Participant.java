package jp.toolforws.grouping;

import java.util.HashMap;

/**
 * @author omikankan
 */
public class Participant{

    private int id;
    private String name;

    private HashMap<String, String> attributes;

    public Participant(){
        attributes = new HashMap<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setAttribute(String key, String value){
        attributes.put(key, value);
    }
    public String getAttribute(String key){
        return attributes.get(key);
    }
    public HashMap<String, String> getAttributes(){
        return attributes;
    }
}
