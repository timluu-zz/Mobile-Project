
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.m3g.Graphics3D;

class Graphics3DProperties extends List implements CommandListener {

    private final Maze3DMIDlet midlet;

    Graphics3DProperties(Graphics3D g3d, Maze3DMIDlet midlet) {
        super("Graphics 3D Properties", List.IMPLICIT);
        this.midlet = midlet;
        Hashtable props = Graphics3D.getProperties();
        Enumeration propKeys = props.keys();
        while (propKeys.hasMoreElements()) {
            Object key = propKeys.nextElement();
            append(key.toString() + ": " + props.get(key).toString(), null);
        }
        // some entries are too long
        setFitPolicy(List.TEXT_WRAP_ON);
        addCommand(new Command("Back", Command.BACK, 1));
        setCommandListener(this);
    }

    public void commandAction(Command command, Displayable displayable) {
        midlet.showMenu();
    }
}
