package hujkay.help;

import hujkay.Block;
import javax.microedition.lcdui.*;

public class HelpCanvas extends Canvas implements CommandListener {

    private Image imagecontent;
    private int picturex, picturey;
    private int wide, height;
    private boolean loadsuccess = true;
    private Block block;
    private Command okcommand = new Command("OK", Command.OK, 1);

    public HelpCanvas() {
        wide = this.getWidth();
        height = this.getHeight();
        try {
            imagecontent = Image.createImage("/block/help/content.png");
        } catch (Exception e) {
            e.printStackTrace();
            loadsuccess = false;
            return;
        }

        if (wide > imagecontent.getWidth()) {
            picturex = (wide - imagecontent.getWidth()) / 2;
            picturey = (height - imagecontent.getHeight()) / 2;
        } else {
            picturex = picturey = 0;
        }
        this.addCommand(okcommand);
        this.setCommandListener(this);
    }

    public HelpCanvas(Block block) {
        this.block = block;
        this.addCommand(okcommand);
        this.setCommandListener(this);
        wide = this.getWidth();
        height = this.getHeight();
        try {
            imagecontent = Image.createImage("/block/help/content.png");
        } catch (Exception e) {
            e.printStackTrace();
            loadsuccess = false;
            return;
        }

        if (wide > imagecontent.getWidth()) {
            picturex = (wide - imagecontent.getWidth()) / 2;
            picturey = (height - imagecontent.getHeight()) / 2;
        } else {
            picturex = picturey = 0;
        }
    }

    protected void paint(Graphics g) {
        // TODO Auto-generated method stub
        if (loadsuccess) {
            g.setColor(0, 240, 240);
            g.fillRect(0, 0, wide, height);
            g.drawImage(imagecontent, picturex, picturey, Graphics.LEFT | Graphics.TOP);
        } else {
            g.drawString("Load failed", wide / 2 - 10, height / 2 - 5, Graphics.LEFT | Graphics.TOP);
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c.getLabel().equals("OK")) {
            block.mangeaction(0, 3);
        }
    }
}
