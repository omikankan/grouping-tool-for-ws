package jp.toolforws.grouping;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * @author omikankan
 */
public class GroupAttributesTest {

    GroupAttributes sut;
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
        sut = new GroupAttributes(keywords);
    }

    @Test
    public void testIncrementHeadcountAddOneTime(){
        assertThat(sut.attributes.get("item1").get("1a"), is(0));
        sut.incrementHeadcount("item1", "1a");
        assertThat(sut.attributes.get("item1").get("1a"), is(1));
        assertNull(sut.attributes.get("item1").get("not-exist"));
    }

    @Test
    public void testIncrementHeadcountAddMoreThanOnce(){
        assertThat(sut.attributes.get("item1").get("1b"), is(0));
        assertThat(sut.attributes.get("item2").get("2a"), is(0));
        sut.incrementHeadcount("item1", "1b");
        sut.incrementHeadcount("item2", "2a");
        assertThat(sut.attributes.get("item1").get("1b"), is(1));
        assertThat(sut.attributes.get("item2").get("2a"), is(1));
    }

    @Test
    public void testIncrementHeadcountNotExistSubkey(){
        sut.incrementHeadcount("item1", "1a");
        assertNull(sut.attributes.get("item1").get("not-exist"));
    }

}
