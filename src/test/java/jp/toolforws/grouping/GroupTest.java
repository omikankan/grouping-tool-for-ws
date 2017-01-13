package jp.toolforws.grouping;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author omikankan
 */
public class GroupTest {

    Group sut;
    HashMap<String, String> participantAttributes;

    @Before
    public void setup(){
        HashMap<String, List<String>> keywords = new HashMap<>();
        List<String> value1 = new ArrayList<>();
        value1.add("1a");
        value1.add("1b");
        keywords.put("item1", value1);
        List<String> value2 = new ArrayList<>();
        value2.add("2a");
        value2.add("2b");
        value2.add("2c");
        keywords.put("item2", value2);
        List<String> value3 = new ArrayList<>();
        value3.add("3a");
        value3.add("3b");
        keywords.put("item3", value3);
        sut = new Group("1", keywords);

        participantAttributes = new HashMap<>();
    }

    @Test
    public void testUpdateScoreAttributeIsMuch(){
        sut.groupAttributes.incrementHeadcount("item1", "1a");

        participantAttributes.put("item1", "1a");

        assertThat(sut.score, is(0));
        sut.updateScore(participantAttributes);
        assertThat(sut.score, is(1));
    }
    @Test
    public void testUpdateScoreMultipleAttributesAreMuch(){
        sut.groupAttributes.incrementHeadcount("item1", "1a");
        sut.groupAttributes.incrementHeadcount("item2", "2c");
        sut.groupAttributes.incrementHeadcount("item3", "3b");

        participantAttributes.put("item1", "1a");
        participantAttributes.put("item2", "2c");
        participantAttributes.put("item3", "3b");

        assertThat(sut.score, is(0));
        sut.updateScore(participantAttributes);
        assertThat(sut.score, is(3));
    }

    @Test
    public void testUpdateScorePartAttributesAreMuch(){
        sut.groupAttributes.incrementHeadcount("item1", "1a");
        sut.groupAttributes.incrementHeadcount("item3", "3b");

        participantAttributes.put("item1", "1a");
        participantAttributes.put("item2", "2c");

        assertThat(sut.score, is(0));
        sut.updateScore(participantAttributes);
        assertThat(sut.score, is(1));
    }

    @Test
    public void testUpdateScoreAllAttributesAreNotMuch(){
        sut.groupAttributes.incrementHeadcount("item1", "1a");
        sut.groupAttributes.incrementHeadcount("item2", "2c");
        sut.groupAttributes.incrementHeadcount("item2", "3c");

        participantAttributes.put("item1", "1b");
        participantAttributes.put("item2", "2a");
        participantAttributes.put("item3", "3a");

        assertThat(sut.score, is(0));
        sut.updateScore(participantAttributes);
        assertThat(sut.score, is(0));
    }
}
