
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class HelpCanvas extends Canvas {

    private Rpg parent;
    private Font font = Font.getDefaultFont();
    private String[] text = {
        "方向键(以及2468):", " 移动",
        "确定键(5)     :", "对话及查找",
        "GAME_A        :", " 确认状态",
        "GAME_C        :", " 显示菜单",
        "GAME_D        :", " 怪物笔记",
        "", "",
        "", "",
        "设计、程序： Fade", "",
        "               @ OGAME 2005 ", ""
    };

    protected void paint(Graphics g) {
        g.setColor(0);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(0xf000f0);
        g.drawString("帮助信息", 120 - 40, 0, Graphics.TOP | Graphics.LEFT);
        int y = font.getHeight() + 5;
        for (int i = 0; i < text.length / 2; i++) {
            g.setColor(0xf0f0f0);
            g.drawString(text[i * 2], 5, y, Graphics.TOP | Graphics.LEFT);
            g.setColor(128, 128, 200);
            g.drawString(text[i * 2 + 1], 110, y, Graphics.TOP | Graphics.LEFT);
            y = y + font.getHeight() + 5;
        }
    }

    public HelpCanvas(Rpg p) {
        parent = p;
    }

    public void keyPressed(int keyCode) {
        parent.setState(parent.STATE_MENU);
    }
}
