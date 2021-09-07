package com.epam.savenko.cashmachine.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest extends Assertions {


    @Test
    void givenText_whenGetHashApply_thenHaveHashString(){
        String given = "test";
        String hash = User.getHash().apply(given);
        assertNotNull(hash);
    }

    @Test
    void givenText_whenGetHashApply_thenHaveHashStringLength32(){
        String given = "hello";
        String actual = User.getHash().apply(given);
        assertEquals(32, actual.length());
    }
}