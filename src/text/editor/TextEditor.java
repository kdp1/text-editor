package text.editor;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextEditor extends JFrame implements ActionListener {

    JMenuBar menubar;
    JMenu menu;
    JMenuItem openFile, saveFile;
    JFileChooser fc;
    JTextArea textArea;

    public TextEditor() {
        startUI();
    }

    private void startUI() {
        menuBar();
        textArea();
        setTitle("Text Editor");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void menuBar() {
        menubar = new JMenuBar();
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menubar.add(menu);
        openFile = new JMenuItem("Open File");
        menu.add(openFile);
        openFile.addActionListener(this);
        saveFile = new JMenuItem("Save File");
        menu.add(saveFile);
        saveFile.addActionListener(this);
        fc = new JFileChooser();
        setJMenuBar(menubar);
    }

    private void textArea() {
        textArea = new JTextArea(10, 40);
        textArea.setMargin(new Insets(5, 5, 5, 5));
        textArea.setFont(new Font("Arial", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openFile) {
            int returnValue = fc.showOpenDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    FileReader reader = new FileReader(file);
                    try {
                        textArea.read(reader, file);
                    } catch (IOException ex) {
                        Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    textArea.requestFocus();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else if (e.getSource() == saveFile) {
            int returnValue = fc.showSaveDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                if (!file.getName().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }
                BufferedWriter outFile = null;
                try {
                    outFile = new BufferedWriter(new FileWriter(file));
                    textArea.write(outFile);
                } catch (IOException ex) {}
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            TextEditor textEditor = new TextEditor();
            textEditor.setVisible(true);
        });
    }
}

