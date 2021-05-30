package sample;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GUITest {

    @Test
    public void search() {
        int data1 = 0;
        String data2 = "";

        ArrayList<Integer> membershipNumber = new ArrayList<Integer>();
        ArrayList<String> name = new ArrayList<String>();

        membershipNumber.add(1);
        membershipNumber.add(2);
        membershipNumber.add(3);
        membershipNumber.add(4);
        membershipNumber.add(5);

        name.add("scott");
        name.add("veron");
        name.add("brad");
        name.add("charlie");
        name.add("abby");

        int memberNumber = 3;
        for (int i=0; i<membershipNumber.size(); i++) {
            int number = membershipNumber.get(i);
            if (number==memberNumber) {
                data1 = membershipNumber.get(i);
                data2 = name.get(i);
            }
        }
        assertEquals(data1, 3);
        assertEquals(data2, "brad");
    }
}