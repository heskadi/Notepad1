package notepad1;

import java.awt.Color;

public class Function_Color {
	
	GUI gui;
	
	public Function_Color(GUI gui) {
		this.gui = gui;
	}
	
	public void changeColor(String color) {
		switch(color) {
		case "White":
			gui.window.getContentPane().setBackground(Color.white);
			gui.textArea.setBackground(Color.white);
			gui.textArea.setForeground(Color.black);
			break;
		case "Black":
			gui.window.getContentPane().setBackground(new Color(64, 64, 64));
			gui.textArea.setBackground(new Color(64, 64, 64));
			gui.textArea.setForeground(Color.white);
			break;
		case "Blue":
			gui.window.getContentPane().setBackground(new Color(54, 80, 141));
			gui.textArea.setBackground(new Color(54, 80, 141));
			gui.textArea.setForeground(Color.white);
			break;
		}
	}
}
