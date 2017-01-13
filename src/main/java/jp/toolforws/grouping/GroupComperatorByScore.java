package jp.toolforws.grouping;

import java.util.Comparator;

/**
 * @author omikankan
 */
public class GroupComperatorByScore implements Comparator<Group>{
    @Override
    public int compare(Group g1, Group g2) {
        int order = 0;

        if(g1.score < g2.score){
            order =  -1;
        }else if(g1.score > g2.score){
            order = 1;
        }else{
            if(g1.getMembers().size() < g2.getMembers().size()){
                order = -1;
            }else if(g1.getMembers().size() < g2.getMembers().size()){
                order = 1;
            }
        }
        return order;
    }
}
