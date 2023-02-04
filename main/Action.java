package main;




public class Action {

    private String command;
    private String count;
    private Movement movement;

    public Action() {
        command = "";
        count = "";
        movement = Movement.NONE;
    }
    public String getCommand() {
        return this.command;
    }
    public Movement getMovement() {
        return this.movement;
    }
    public int getCount() {
        if (count.equals("")) count = "1";
        return Integer.parseInt(count);
    }

    public void run() {
        utils.ControlThread.actionManager(this);
    }


    public void addCount(int num) {
        count += num;
        Main.screen.makeInfoBar();
    }

    public void up() {
        movement = Movement.UP;
        run();
    };
    public void down() {
        movement = Movement.DOWN;
        run();
    };
    public void left() {
        movement = Movement.LEFT;
        run();
    };
    public void right() {
        movement = Movement.RIGHT;
        run();
    };



    public static enum Movement {
        NONE,
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }

}
