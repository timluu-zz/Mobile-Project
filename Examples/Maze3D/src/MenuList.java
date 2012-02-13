
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

class MenuList
        extends List
        implements CommandListener {

    private final Maze3DMIDlet midlet;
    private boolean gameStarted = false;

    MenuList(Maze3DMIDlet midlet, boolean apiAvailable) {
        super("Maze3D", List.IMPLICIT);

        this.midlet = midlet;

        setFitPolicy(List.TEXT_WRAP_ON);

        // in case the 3D API is not available
        if (apiAvailable) {
            append("New Game", null);
            append("3D Properties", null);
        }
        // add an exit command
        addCommand(new Command("Exit", Command.EXIT, 0));
        setCommandListener(this);
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == List.SELECT_COMMAND) {
            int index = getSelectedIndex();
            switch (index) {
                case 0:
                    //  new game
                    midlet.newGame();
                    // add more commands the first time the game runs
                    if (!gameStarted) {
                        insert(1, "Top view", null);
                        addCommand(new Command("Back", Command.BACK, 0));
                        gameStarted = true;
                    }
                    break;
                case 1:
                    if (gameStarted) {
                        midlet.switchView();
                    } else {
                        midlet.show3DProperties();
                    }
                    break;
                case 2:
                    midlet.show3DProperties();
                    break;
            }
        } else if (command.getCommandType() == Command.BACK) {
            midlet.showMain();
        } else if (command.getCommandType() == Command.EXIT) {
            midlet.quitApp();
        }
    }

    void viewSwitched() {
        // switch the labels for normal/top view
        if (getString(1).equals("Top view")) {
            set(1, "Normal view", null);
        } else {
            set(1, "Top view", null);
        }
    }
}
