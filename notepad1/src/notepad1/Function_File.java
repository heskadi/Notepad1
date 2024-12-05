package notepad1;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Function_File {
	//Conecta com o arquivo GUI
	
	GUI gui;
	String fileName;
	String fileAddress;
	
	public Function_File(GUI gui) {
		this.gui = gui;
	}
	
	//Criar novo arquivo
	public void newFile() {
		gui.textArea.setText("");
		gui.window.setTitle("New");
		fileName = null;
		fileAddress = null;
	}
	
	//Abrir arquivos existentes
	public void openFile() {
		FileDialog fd = new FileDialog(gui.window, "Select the file", FileDialog.LOAD);
		fd.setVisible(true);
		
		if(fd.getFile()!=null) {
			fileName = fd.getFile();
			fileAddress = fd.getDirectory();
			gui.window.setTitle(fileName);
		} try {
			BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName)); //Precisa do Address do arquivo para poder ler ele
			gui.textArea.setText("");
			String line = null;
			while((line = br.readLine())!=null) {
				gui.textArea.append(line + "\n");
			} br.close();
		} catch(Exception e) {
			System.out.println("FILE NOT OPENED!");
		}
	}
	
	public void saveFile() {
		if(fileName==null) {
			saveAsFile();
		} else {
			try {
				FileWriter fw = new FileWriter(fileAddress + fileName);
				fw.write(gui.textArea.getText());
				gui.window.setTitle(fileName);
				fw.close();
			} catch(Exception e) {
				System.out.println("SOMETHING WORNG!");
			}
		}
	}
	
	public void saveAsFile() {
		FileDialog fd = new FileDialog(gui.window, "Save", FileDialog.SAVE);
		fd.setVisible(true);
		
		if(fd.getFile()!=null) {
			fileName = fd.getFile();
			fileAddress = fd.getDirectory();
			gui.window.setTitle(fileName);
		} try {
			FileWriter fw = new FileWriter(fileAddress + fileName);
			fw.write(gui.textArea.getText());
			fw.close();
		} catch (Exception e) {
			System.out.println("SOMETHING WRONG!");
		}
	}
	public void exitFile() {
		System.exit(0);
	}
}
