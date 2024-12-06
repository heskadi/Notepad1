package notepad1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.undo.UndoManager;

public class GUI implements ActionListener {
	//Importa os requisitos:
	JFrame window;
	JTextArea textArea;
	boolean wordWrapOn = false; 
	JScrollPane scrollPane;
	JMenuBar menuBar;
	JMenu menuFile, menuEdit, menuOptions;
	JMenuItem iNew, iOpen, iSave, iSaveAs, iExit;
	JMenuItem iUndo, iRedo;
	JMenuItem iWrap, iFontEnchant, iFontArial, iFontTNR, iFontSize8, iFontSize12, iFontSize16, iFontSize20, iFontSize24, iFontSize28;
	JMenu menuFont, menuFontSize;
	JMenu iColorMenu;
	JMenuItem iColor1, iColor2, iColor3;
	JLabel wordCountLabel, charCountLabel;
	
	Function_File file = new Function_File(this);
	Function_Options format = new Function_Options(this);
	Function_Color color = new Function_Color(this);
	Function_Edit edit = new Function_Edit(this);
	
	KeyHandler kHandler = new KeyHandler(this);
	
	UndoManager um = new UndoManager();
	
	//Inicializa o GUI
	public static void main(String[] args) {
		new GUI();
	}
	
	//Executa o GUI e permite visualização
	public GUI() {
		createWindow();
		createTextArea();
		createMenuBar();
		createFileMenu();
		createEditMenu();
		createOptionsMenu();
		createColorMenu();
		format.selectedFont = "Arial";
		format.createFont(16);
		format.wordWrap();
		color.changeColor("Black");
		window.setVisible(true);
	}
	
	//Cria a janela base do projeto
	public void createWindow() {
		window = new JFrame("Notepad1");
		window.setSize(800,600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//Cria a TextBox do projeto
	public void createTextArea() {
		textArea = new JTextArea();
		textArea.setMargin(new Insets(0, 15, 0, 0));
		
		// Adiciona um CaretListener para detectar onde está o cursor
	    textArea.addCaretListener(e -> {
	        try {
	            // Remove os destaques antigos
	            Highlighter highlighter = textArea.getHighlighter();
	            highlighter.removeAllHighlights();

	            // Posição do cursor
	            int caretPos = textArea.getCaretPosition();

	            // Identifica a linha onde o cursor está
	            int line = textArea.getLineOfOffset(caretPos);

	            // Obtém o início e o final da linha onde o cursor está
	            int start = textArea.getLineStartOffset(line);
	            int end = textArea.getLineEndOffset(line);

	            // Estilo de destaque da linha com fundo suave
	            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(new Color(32, 32, 32)); // Azul claro suave

	            // Destaca a linha inteira
	            highlighter.addHighlight(start, end, painter);
	        } catch (BadLocationException ex) {
	            ex.printStackTrace();
	        }
	    });
		
		textArea.addKeyListener(kHandler);
		
		textArea.getDocument().addUndoableEditListener(
				new UndoableEditListener() {
					public void undoableEditHappened(UndoableEditEvent e) {
						um.addEdit(e.getEdit());
					}
				});
		
		// Adiciona DocumentListener para contagem de palavras e caracteres
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateCounters();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateCounters();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateCounters();
            }
        });
		
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEtchedBorder());
		window.add(scrollPane);
		//window.add(textArea);
		
		// Painel para exibir a contagem de palavras e caracteres
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));  // Definindo o layout vertical

		wordCountLabel = new JLabel("Words: 0");
		charCountLabel = new JLabel("Characters: 0");

		bottomPanel.add(wordCountLabel);
		bottomPanel.add(charCountLabel);
		window.add(bottomPanel, BorderLayout.EAST);
		
		
		
	}
	
	private void updateCounters() {
        String text = textArea.getText();

        // Conta as palavras (separadas por espaços)
        int wordCount = text.split("\\s+").length;
        if (text.trim().isEmpty()) {
            wordCount = 0; // Se o texto estiver vazio
        }

        // Conta os caracteres
        int charCount = text.length();

        // Atualiza os rótulos
        wordCountLabel.setText("Words: " + wordCount);
        charCountLabel.setText("Characters: " + charCount);
    }
	
	//Cria a barra do menu e implementa as opções nele
	public void createMenuBar() {
		menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);
		
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);
		menuOptions = new JMenu("Options");
		menuBar.add(menuOptions);
	}
	
	public void createFileMenu() {
		//Sub-lista de File
		iNew = new JMenuItem("New");
		iNew.addActionListener(this);
		iNew.setActionCommand("New");
		menuFile.add(iNew);
		//
		iOpen = new JMenuItem("Open");
		iOpen.addActionListener(this);
		iOpen.setActionCommand("Open");
		menuFile.add(iOpen);
		//
		iSave = new JMenuItem("Save");
		iSave.addActionListener(this);
		iSave.setActionCommand("Save");
		menuFile.add(iSave);
		//
		iSaveAs = new JMenuItem("Save as...");
		iSaveAs.addActionListener(this);
		iSaveAs.setActionCommand("SaveAs");
		menuFile.add(iSaveAs);
		//
		iExit = new JMenuItem("Exit");
		iExit.addActionListener(this);
		iExit.setActionCommand("Exit");
		menuFile.add(iExit);
		
	}
	
	public void createEditMenu() {
		iUndo = new JMenuItem("Undo");
		iUndo.addActionListener(this);
		iUndo.setActionCommand("Undo");
		menuEdit.add(iUndo);
		
		iRedo = new JMenuItem("Redo");
		iRedo.addActionListener(this);
		iRedo.setActionCommand("Redo");
		menuEdit.add(iRedo);
	}
	
	public void createOptionsMenu() {
		iWrap = new JMenuItem("Word Wrap: Off");
		iWrap.addActionListener(this);
		iWrap.setActionCommand("Word Wrap");
		menuOptions.add(iWrap);
		menuFont = new JMenu("Font");
		menuOptions.add(menuFont);
		
		iFontEnchant = new JMenuItem("Minecraft Enchantment");
		iFontEnchant.addActionListener(this);
		iFontEnchant.setActionCommand("Minecraft Enchantment");
		menuFont.add(iFontEnchant);
		
		iFontArial = new JMenuItem("Arial");
		iFontArial.addActionListener(this);
		iFontArial.setActionCommand("Arial");
		menuFont.add(iFontArial);
		
		iFontTNR = new JMenuItem("Time New Roman");
		iFontTNR.addActionListener(this);
		iFontTNR.setActionCommand("Times New Roman");
		menuFont.add(iFontTNR);
		
		menuFontSize = new JMenu("Font Size");
		menuOptions.add(menuFontSize);
		
		//8, 12, 16, 20, 24, 48
		iFontSize8 = new JMenuItem("8");
		iFontSize8.addActionListener(this);
		iFontSize8.setActionCommand("size8");
		menuFontSize.add(iFontSize8);
		
		iFontSize12 = new JMenuItem("12");
		iFontSize12.addActionListener(this);
		iFontSize12.setActionCommand("size12");
		menuFontSize.add(iFontSize12);
		
		iFontSize16 = new JMenuItem("16");
		iFontSize16.addActionListener(this);
		iFontSize16.setActionCommand("size16");
		menuFontSize.add(iFontSize16);
		
		iFontSize20 = new JMenuItem("20");
		iFontSize20.addActionListener(this);
		iFontSize20.setActionCommand("size20");
		menuFontSize.add(iFontSize20);
		
		iFontSize24 = new JMenuItem("24");
		iFontSize24.addActionListener(this);
		iFontSize24.setActionCommand("size24");
		menuFontSize.add(iFontSize24);
		
		iFontSize28 = new JMenuItem("28");
		iFontSize28.addActionListener(this);
		iFontSize28.setActionCommand("size28");
		menuFontSize.add(iFontSize28);
	}
	
	public void createColorMenu() {
		iColorMenu = new JMenu("Color");
		menuOptions.add(iColorMenu);
		
		iColor1 = new JMenuItem("White");
		iColor1.addActionListener(this);
		iColor1.setActionCommand("White");
		iColorMenu.add(iColor1);
		iColor2 = new JMenuItem("Black");
		iColor2.addActionListener(this);
		iColor2.setActionCommand("Black");
		iColorMenu.add(iColor2);
		iColor3 = new JMenuItem("Blue");
		iColor3.addActionListener(this);
		iColor3.setActionCommand("Blue");
		iColorMenu.add(iColor3);

	}
	
	//Controla as ações dos botões de barra de menu
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch(command) {
		case "New":  file.newFile(); break;
		case "Open":  file.openFile(); break;
		case "Save":  file.saveFile(); break;
		case "SaveAs":  file.saveAsFile(); break;
		case "Exit": file.exitFile(); break;
		
		case "Undo": edit.undo(); break;
		case "Redo": edit.redo(); break;
		
		case "Word Wrap": format.wordWrap(); break;
		
		case "Arial": format.setFont(command); break;		
		case "Minecraft Enchantment": format.setFont(command); break;
		case "Times New Roman": format.setFont(command); break;
		
		case "size8": format.createFont(8); break;
		case "size12": format.createFont(12); break;
		case "size16": format.createFont(16); break;
		case "size20": format.createFont(20); break;
		case "size24": format.createFont(24); break;
		case "size28": format.createFont(28); break;
		
		case "White": color.changeColor(command);
		case "Black": color.changeColor(command);
		case "Blue": color.changeColor(command);
		}
	}
}
