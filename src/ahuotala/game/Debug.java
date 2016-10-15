package ahuotala.game;

/**
 *
 * @author Aleksi Huotala
 */
public class Debug {

    private static boolean ENABLED = true;

    public static void log(String message) {
        if (ENABLED) {
            System.out.println("# " + message);
        }
    }
}
