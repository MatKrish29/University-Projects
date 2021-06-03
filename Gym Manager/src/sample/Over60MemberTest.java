package sample;

import org.junit.Test;

import static org.junit.Assert.*;

public class Over60MemberTest {

    @Test
    public void setAgeAndGetAge() {
        Over60Member test = new Over60Member();
        test.setAge(72);
        assertTrue(test.getAge()==72);
    }
}