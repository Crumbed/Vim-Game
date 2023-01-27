package utils;



public class KeyInput {
    
    public static void startCharInput() {
        try {
            String[] cmd = { "/bin/sh", "-c", "stty raw -echo </dev/tty" };
            Runtime.getRuntime().exec(cmd).waitFor();
            //rawMode = true;
        } catch (Exception e) {
            System.out.println("Exception in startCharInput: " + e);
        }
    }
    
    public static void stopCharInput() {
        try {
            String[] cmd = { "/bin/sh", "-c", "stty sane </dev/tty" };
            Runtime.getRuntime().exec(cmd).waitFor();
            //rawMode = false;
        } catch (Exception e) {
            System.out.println("Exception in startCharInput: " + e);
        }
    }
    
    
}
