/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Aleksi
 */
public class RendererTest {

    private Renderer renderer;

    public RendererTest() {
        renderer = new Renderer(256, 256);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of render method, of class Renderer.
     */
    @Test
    public void testRender() {

    }

    /**
     * Test of setColor method, of class Renderer.
     */
    @Test
    public void testSetColor() {

    }

    /**
     * Test of getColor method, of class Renderer.
     */
    @Test
    public void testGetColor() {
        System.out.println("getColor");
        int expResult = 0;
        int result = renderer.getColor(0, 0, 0);
        int expResult2 = 16776960;
        int result2 = renderer.getColor(255, 255, 0);
        int expResult3 = 16711935;
        int result3 = renderer.getColor(255, 0, 255);
        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
    }

    /**
     * Test of renderObject method, of class Renderer.
     */
    @Test
    public void testRenderObject() {

    }

}
