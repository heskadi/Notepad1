package notepad1;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class Function_Options {
	GUI gui;
	Font arial, enchant, timesNewRoman, digital7;
	String selectedFont;
	
	public Function_Options (GUI gui) {
		this.gui = gui;
	}
	
	public void wordWrap() {
		if(gui.wordWrapOn==false) {
			gui.wordWrapOn=true;
			gui.textArea.setLineWrap(true);
			gui.textArea.setWrapStyleWord(true);
			gui.iWrap.setText("Word Wrap: On");
		} else if(gui.wordWrapOn==true) {
			gui.wordWrapOn=false;
			gui.textArea.setLineWrap(false);
			gui.textArea.setWrapStyleWord(false);
			gui.iWrap.setText("Word Wrap: Off");
		}
	}
	
	public void createFont(int fontSize) {
		arial = new Font("Arial", Font.PLAIN, fontSize);
		enchant = new Font("Minecraft Enchantment", Font.PLAIN, fontSize);
		timesNewRoman = new Font("Times New Roman", Font.PLAIN, fontSize);
		digital7 = loadDigital7Font(fontSize);  // Carregar a fonte Digital-7
		setFont(selectedFont);
	}
	
	public void setFont(String font) {
		selectedFont = font;
		switch(selectedFont) {
		case "Arial":
			gui.textArea.setFont(arial);
			break;
		case "Minecraft Enchantment":
			gui.textArea.setFont(enchant);
			break;
		 case "Digital-7":
             gui.textArea.setFont(digital7);  // Aplicar a fonte Digital-7
             break;
		case "Times New Roman":
			gui.textArea.setFont(timesNewRoman);
			break;
		}
	}
	private Font loadDigital7Font(int fontSize) {
        try {
            // Substitua o caminho pelo caminho correto do arquivo .ttf no seu projeto
            File fontFile = new File("src/fonts/Digital-7.ttf");
            Font digital7Font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            return digital7Font.deriveFont(Font.PLAIN, fontSize);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, fontSize);  // Fallback para Arial caso haja erro
        }
    }
	
}
