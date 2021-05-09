package com.example.finalproject;

import android.graphics.Color;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import java.util.ArrayList;

/*
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

 */

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class ExampleUnitTest {

    private ColorValueConverter colorValueConverter;

    @Before
    public void setUp() {
        colorValueConverter = new ColorValueConverter();
    }

    @Test
    public void RGBtoHex() {
        String hex1 = colorValueConverter.RGBToHex(255, 255, 255);
        assertEquals("FFFFFF", hex1); // pass

        String hex2 = colorValueConverter.RGBToHex(-1, 255, 255);
        assertEquals(6, hex2.length()); // fail

        String hex3 = colorValueConverter.RGBToHex(-1, 255, 255);
        assertEquals("", hex3); // pass

        String hex4 = colorValueConverter.RGBToHex(-1, 255, 255);
        assertEquals(0, hex4.length()); // pass
    }
}