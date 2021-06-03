package sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MyGymManagerTest {

    @Test
    public void deleteMember() {
        int output = 0;
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        numbers.add(7);
        numbers.add(14);
        numbers.add(4);
        numbers.add(21);
        numbers.add(18);
        for (int i=0; i<numbers.size(); i++) {
            int number = numbers.get(i);
            if (number == 4) {
                numbers.remove(i);
                output = numbers.get(i);
                break;
            }
        }
        /*If 4 is removed or any other number is removed,
        the number after that index will take the place of removed number*/
        assertEquals(21 , output);
    }

    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream realOutput = System.out;

    @Before
    public void setup() {
        System.setOut(new PrintStream(output));
    }

    @Test
    public void printMembers() {
        int data1;
        String data2;
        String data3;
        String data4;

        ArrayList<Object> membershipNumber = new ArrayList<Object>();
        ArrayList<Object> name = new ArrayList<Object>();
        ArrayList<Object> startMembershipDate = new ArrayList<Object>();
        ArrayList<Object> memberType = new ArrayList<Object>();

        membershipNumber.add(1);
        membershipNumber.add(2);
        membershipNumber.add(3);

        name.add("scott");
        name.add("bratt");
        name.add("naomi");

        startMembershipDate.add("2019-09-02");
        startMembershipDate.add("2020-01-09");
        startMembershipDate.add("2020-01-11");

        memberType.add("Student Member");
        memberType.add("Over60 Member");
        memberType.add("Default Member");

        System.out.println("Membership Number          Member Name          Start Membership Date            Member Type");
        System.out.println("=================          ===========          =====================          ===============");

        //Looping and printing each data
        for (int i = 0; i < membershipNumber.size(); i++) {
            data1 = (int) membershipNumber.get(i);
            data2 = (String) name.get(i);
            data3 = (String) startMembershipDate.get(i);
            data4 = (String) memberType.get(i);
            System.out.format("%-27s%-26s%-26s%-25s\r\n", data1, data2, data3, data4);
        }
        assertEquals("Membership Number          Member Name          Start Membership Date            Member Type\r\n" +
                "=================          ===========          =====================          ===============\r\n" +
                "1                          scott                     2019-09-02                Student Member           \r\n" +
                "2                          bratt                     2020-01-09                Over60 Member            \r\n" +
                "3                          naomi                     2020-01-11                Default Member           \r\n", output.toString());
                    }

    @After
    public void realOutput() {
        System.setOut(realOutput);
    }

    @Test
    public void sortNames() {
        String holder;
        String name[] = {"scott", "brad", "abby", "nat", "charlie"};
        String sortedName[] = {"abby", "brad", "charlie", "nat", "scott"};
        for (int i = 0; i < name.length; i++) {
            for (int j = i + 1; j < name.length; j++) {
                if (name[i].compareTo(name[j]) > 0) {
                    holder = name[i];
                    name[i] = name[j];
                    name[j] = holder;
                }
            }
        }
        assertArrayEquals(sortedName, name);
    }
}