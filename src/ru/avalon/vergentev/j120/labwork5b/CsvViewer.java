package ru.avalon.vergentev.j120.labwork5b;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class CsvViewer extends JFrame implements ActionListener, WindowListener {
    String[] testComboBox = {"aaa", "bbb", "ccc", "ddd", "eee"};
    JComboBox comboBox = new JComboBox(testComboBox);
    JButton showTable = new JButton("Show table");
    JFrame frameForTable = new JFrame();
    JScrollPane panelForTable;
    String[] testColumn = {"111", "222", "333", "444", "555"};
    String [][] testData = {{"a","a","a","a","a"},{"b","b","b","b","b"},{"c","c","c","c","c"}};
    JTable table = new JTable();


    File file = new File(System.getProperty("user.dir"));
    File[] listFiles;
    StringBuilder data;
    String[] dataEachString, dataEachCell;

    public CsvViewer() {
        setTitle("CSV Viewer");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        addWindowListener(this);
        add(showTable);
        showTable.addActionListener(this);
        add(comboBox);
        comboBox.addActionListener(this);
    }

    private void addTable () {
        if (frameForTable.isShowing()) {
            frameForTable.dispose();
        } else {
            frameForTable = new JFrame();
            table = new JTable(testData, testColumn);
            panelForTable = new JScrollPane(table);
            frameForTable.setBounds(30, 40, 600, 600);
            frameForTable.add(panelForTable);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showTable) algorithmShowTableIsPushed();
        else if (e.getSource() == comboBox) algorithmComboBoxIsChoosed();
    }

    private void algorithmShowTableIsPushed() {
        if (!frameForTable.isShowing()) {
            addTable();
            frameForTable.setVisible(true);
        } else {
            frameForTable.dispose();
        }
    }

    private void algorithmComboBoxIsChoosed() {
        System.out.println("cry");
    }


    //метод читающий файл и возвращающий данные в память компьютера
    public StringBuilder reader (File file) {
        if (!file.canRead())  throw new SecurityException("File can't be readable !!!");
        int symbolExisting;
        try {
            FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
            data = new StringBuilder();
            while ((symbolExisting = fileReader.read()) != -1) {
                data.append((char)symbolExisting);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public String[] getEachString () {
        //формируем массив отдельных слов с которым будем работать
        dataEachString = reader(file).toString().split("\n");
        return dataEachString;
    }

    public String[] getEachCell () {
        //формируем массив отдельных слов с которым будем работать
        dataEachCell = reader(file).toString().split("[\n ]");
        return dataEachCell;
    }

    public File[] getFilesInDirectory (File file) {
        if (file.isDirectory() && file.getName().endsWith("csv")) {
            listFiles = file.listFiles();
        }
        return listFiles;
    }





    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {frameForTable.dispose();}
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
}
