package jp.toolforws.grouping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author omikankan
 */
public class Group {
    private String id;
    private List<Participant> members;
    protected GroupAttributes groupAttributes;
    protected int score;

    public Group(String id, HashMap<String, List<String>> keywords){
        setId(id);
        members = new ArrayList<>();
        groupAttributes = new GroupAttributes(keywords);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Participant> getMembers() {
        return this.members;
    }

    public void setMember(Participant member) {
        this.members.add(member);
        for(String key : member.getAttributes().keySet()){
            groupAttributes.incrementHeadcount(key, member.getAttribute(key));
        }
    }

    public void updateScore(HashMap<String, String> participantAttributes){
        score = 0;
        for(String mainKey : participantAttributes.keySet()){
            if(groupAttributes.attributes.containsKey(mainKey)){
                if(groupAttributes.attributes.get(mainKey).containsKey(participantAttributes.get(mainKey))){
                    score = score + groupAttributes.attributes.get(mainKey).get(participantAttributes.get(mainKey));
                }
            }
        }
    }

}
