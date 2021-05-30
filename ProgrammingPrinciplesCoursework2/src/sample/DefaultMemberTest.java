package sample;

import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultMemberTest {

    @Test
    public void setMembershipNumberAndGetMembershipNumber() {
        DefaultMember test1 = new DefaultMember();
        test1.setMembershipNumber(3);
        assertTrue(test1.getMembershipNumber()==3);
    }

    @Test
    public void setNameAndGetName() {
        DefaultMember test1 = new DefaultMember();
        test1.setName("Mike");
        assertTrue(test1.getName().equals("Mike"));
    }

    @Test
    public void setStartMembershipDateAndGetStartMembershipDate() {
        DefaultMember test1 = new DefaultMember();
        test1.setStartMembershipDate("2020-08-01");
        assertTrue(test1.getStartMembershipDate().equals("2020-08-01"));
    }
}