package jp.toolforws.grouping;

import jp.toolforws.grouping.properties.PropertyFileReader;
import jp.toolforws.grouping.data.ParticipationDataReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author omikankan
 */
public class Main {

    public static void main(String[] args) {

        if(args.length < 1){
            throw new IllegalArgumentException(" First argument is the data file and the properties file path. ");
        }

        // プロパティからキーの取得
        final String propertiesPath = args[0] + "\\grouping.properties";
        PropertyFileReader property = null;
        try{
            property = new PropertyFileReader(propertiesPath);
        }catch (IOException ex){
            System.out.println(ex);
            System.exit(1);
        }
        HashMap<String, List<String>> keywords = property.getKeywords();
        int groupNumber = property.getGroupNumber();
        int maxHeadCount = property.getMaxHeadCount();

        // データ読み込み
        final String path = args[0] + "\\data.xlsx";
        ParticipationDataReader reader = new ParticipationDataReader(path);
        List<Participant> allParticipants = new ArrayList<>();
        try{
            allParticipants = reader.read();
        }catch(Exception ex){
            System.out.println(ex);
            System.exit(1);
        }

        List<Group> tmpGroupList = createGroupList(groupNumber, keywords);
        List<Group> finalGroupList = new ArrayList<>() ;

        for(Participant participant : allParticipants) {
            if(!(property.getInclude()[0].equals(""))) {
                if(!(participant.getAttribute(property.getInclude()[0]).equals(property.getInclude()[1]))) {
                    continue;
                }
            }

            Group group = getLowestScoreGroup(tmpGroupList, participant);
            group.setMember(participant);
            if(group.getMembers().size() >= maxHeadCount){
                finalGroupList.add(tmpGroupList.get(0));
                tmpGroupList.remove(0);
            }
        }

        finalGroupList.addAll(tmpGroupList);
        outputFinalGroup(finalGroupList);
    }

    private static List<Group> createGroupList(int groupNumber, HashMap<String, List<String>> keywords){
        List<Group> tmpGroupList = new ArrayList<>();
        for(int groupCount = 0; groupCount < groupNumber; groupCount++){
            tmpGroupList.add(new Group(Integer.toString(groupCount + 1), keywords));
        }
        return tmpGroupList;
    }

    private static Group getLowestScoreGroup(List<Group> tmpGroupList, Participant participant){
        for(int index = 0; index < tmpGroupList.size(); index++){
            tmpGroupList.get(index).updateScore(participant.getAttributes());
        }
        tmpGroupList.sort(new GroupComperatorByScore());
        return tmpGroupList.get(0);
    }

    private static void outputFinalGroup(List<Group> finalGroupList){
        System.out.println("*************** result");
        String separator = "\t";
        System.out.println(
                "group-ID" + separator +
                "participant-ID" + separator +
                "participant-NAME");
        for(Group group : finalGroupList){
            for(Participant participant : group.getMembers()) {
                System.out.print(group.getId() + separator);
                System.out.print(participant.getId() + separator);
                System.out.println(participant.getName());
            }
        }
    }
}
