package ru.avalon.vergentev.j120.labwork5b;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class CsvViewer extends JFrame implements ActionListener, WindowListener, ItemListener {
    File file = new File(System.getProperty("user.dir"));
    File[] files;
    StringBuilder data;
//    String[] dataEachString, dataEachCell;
    String[] testComboBox = {"aaa", "bbb", "ccc", "ddd", "eee"};
//    JComboBox comboBox = new JComboBox(testComboBox);
    JComboBox comboBox;
    JButton showTable = new JButton("Show table");
    JFrame frameForTable = new JFrame();
    JScrollPane panelForTable;
    String[] testColumn = {"111", "222", "333", "444", "555"};
    String [][] testData = {{"a","a","a","a","a"},{"b","b","b","b","b"},{"c","c","c","c","c"}};
    JTable table = new JTable();
    String [][] datatable;

    public CsvViewer() {
        setTitle("CSV Viewer");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 100);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        addWindowListener(this);
        add(showTable);
        showTable.addActionListener(this);
        comboBox = new JComboBox(getCsvFilesInDirectory());
        add(comboBox);
        comboBox.addItemListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showTable) algorithmShowTableIsPushed();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItemSelectable() == comboBox) {
            algorithmItemBoxIsSelected(comboBox);
        }
    }

    private File algorithmItemBoxIsSelected(JComboBox comboBox) {
        return (File) comboBox.getSelectedItem();
    }

    private void algorithmShowTableIsPushed() {
        if (!frameForTable.isShowing()) {
            addTable();
            frameForTable.setVisible(true);
        } else {
            frameForTable.dispose();
        }
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

    public File[] getCsvFilesInDirectory () {
        if (file.getPath().equals(System.getProperty("user.dir"))) {
            files = file.listFiles(file -> file.getName().endsWith(".csv"));
        }
        return files;
    }

    //метод читающий файл и возвращающий данные в память компьютера
    public StringBuilder reader () {
        file = new File(String.valueOf(algorithmItemBoxIsSelected(comboBox)));
        if (!file.canRead()) throw new SecurityException("File can't be readable !!!");
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

    public String[][] getTableFromData () {
        //формируем массив отдельных строк с которым будем работать
        String [] dataEachString = reader().toString().split("\n");
        //формируем двумерный массив таблицу для JTable
        datatable = new String[dataEachString.length][];
        int k = 0;
        for (String i : dataEachString) {
            String [] dataEachCell = i.split(";");
            datatable[k] = new String[]{String.valueOf(dataEachCell)};
            k++;
        }
        return datatable;
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
