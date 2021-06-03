package sample;

import org.junit.Test;

import static org.junit.Assert.*;

public class StudentMemberTest {

    @Test
    public void setSchoolNameAndGetSchoolName() {
        StudentMember test = new StudentMember();
        test.setSchoolName("Loyola College");
        assertTrue(test.getSchoolName().equals("Loyola College"));
    }
}