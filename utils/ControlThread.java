package utils;

import main.Action;
import main.Main;
import main.Main.Movement;

public class ControlThread extends Thread {
    private BetterRunnable runnable;

    public ControlThread(BetterRunnable runnable) {
        this.runnable = runnable;
    }

    public void setRunnable(BetterRunnable runnable) {
        this.runnable.stop();
        this.runnable = runnable;
        this.runnable.run();
    }

    @Override
    public void start() {
        runnable.run();
    }

    public void end() {
        runnable.stop();
    }

    public String getAction() {
        if (!(runnable instanceof Movement)) return "";
        Movement movement = (Movement) runnable;
        if (movement.currAction.getCommand().equals("") && movement.currAction.getCount() == 1) return "";
        return movement.currAction.getCommand() + " x" + movement.currAction.getCount() + " " + movement.currAction.getMovement().name();
    }

    public static void actionManager(Action action) {
        
        switch(action.getMovement()) {
            case UP:
                Main.screen.up(action.getCount());
                break;
            case DOWN:
                Main.screen.down(action.getCount());
                break;
            case LEFT:
                Main.screen.left(action.getCount());
                break;
            case RIGHT:
                Main.screen.right(action.getCount());
                break;
            case NONE:
                break;
        }
    }

    public interface BetterRunnable extends Runnable {
        public void stop();
    }
}


