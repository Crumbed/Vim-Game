package gui;

import main.Operations;

public class Option {
    int operationId;
    public String name;
    public Option(int operationId, String name) {
        this.operationId = operationId;
        this.name = name;
    }

    public void run() {
        Operations.runTask(operationId);
    }

    public String getLabel(int index) {
        return "("+(index+1)+") "+name;
    }

}
