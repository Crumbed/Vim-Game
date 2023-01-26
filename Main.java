

class Movement extends Thread {
    
    private boolean detect = true;
    
    public void run() {
        KeyInput.startCharInput();
        while(detect) {
            if (System.in.available() == 0 ) continue;
            char c = (char) System.in.read();
            switch(c) {
                case 'j':
                    System.out.println("DOWN");
                    break;
                case 'k':
                    System.out.println("UP");
                    break;
                case 'h':
                    System.out.println("LEFT");
                    break;
                case 'l':
                    System.out.println("RIGHT");
                    break;
                
            }
        }
    }

    public void stop() {
        KeyInput.stopCharInput();
        detect = false;
    }
}


class Main{

    static Movement movement = new Movement();

    public static void main(String[] args){
        movement.start();
        
        while(true) {
            System.out.println("running");
        }
        
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}