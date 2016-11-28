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

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        renderer = new Renderer(256, 256, new int[256 * 256]);
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
        System.out.println("Running test setColor");
        renderer.setColor(0, 0, 0, 0, 0);
        renderer.setColor(1, 0, 255, 0, 0);
        renderer.setColor(2, 0, 0, 255, 0);
        renderer.setColor(3, 0, 0, 0, 255);
        renderer.setColor(4, 0, 255, 255, 255);
        assertEquals(0x000000, renderer.getPixels()[0]);
        assertEquals(0xFF0000, renderer.getPixels()[1]);
        assertEquals(0x00FF00, renderer.getPixels()[2]);
        assertEquals(0x0000FF, renderer.getPixels()[3]);
        assertEquals(0xFFFFFF, renderer.getPixels()[4]);

    }

    /**
     * Test of getColor method, of class Renderer.
     */
    @Test
    public void testGetColor() {
        System.out.println("Running test getColor");
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
