package ru.avalon.vergentev.j120.labwork5b;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class CsvViewer extends JFrame implements  WindowListener {
    File file = new File(System.getProperty("user.dir"));
    File[] files;
    StringBuilder data;
    JComboBox comboBox;
    JButton showTable = new JButton("Show table");
    JFrame frameForTable = new JFrame();
    JScrollPane panelForTable;
    JTable table;
    String [][] dataTable;
    String [] dataEachString, titleTable;

    public CsvViewer() {
        setTitle("CSV Viewer");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        addWindowListener(this);
        add(showTable, BorderLayout.CENTER);
        showTable.addActionListener(e -> {algorithmShowTableIsPushed();});
        comboBox = new JComboBox(getCsvFilesInDirectory());
        add(comboBox, BorderLayout.SOUTH);
        comboBox.addItemListener(e -> {getItemBoxIsSelected(comboBox);});
    }

    private File getItemBoxIsSelected(JComboBox comboBox) {
        if (frameForTable.isShowing()) {
            frameForTable.dispose();
            addTable();
            frameForTable.setVisible(true);
        }
        setTitle(String.valueOf(comboBox.getSelectedItem()));
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
            table = new JTable(getTableFromData (), titleTable);
            panelForTable = new JScrollPane(table);
            frameForTable.setBounds(30, 40, 600, 600);
            frameForTable.add(panelForTable);
        }
    }

    public File[] getCsvFilesInDirectory () {
        //директория приложения
        if (file.getPath().equals(System.getProperty("user.dir"))) {
            //Обращаемся по всем файлам ListFiles с фильтром csv...
            files = file.listFiles(file -> file.getName().endsWith(".csv"));
            //...этот метод принимает объект FileNameFilter на базе которого мы хотим получить файлы .csv...
            //...поэтому проще сделать через лямбда выражение
        }
        return files;
    }

    //метод читающий файл и возвращающий данные в память компьютера
    public StringBuilder reader () {
        file = new File(String.valueOf(getItemBoxIsSelected(comboBox)));
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
        dataEachString = reader().toString().split("\n");
        //задаём заголовок таблицы
        titleTable = dataEachString[0].split(";");
        //формируем двумерный массив для JTable
        dataTable = new String[dataEachString.length-1][];
        int k = 0;
        for (int i = 1; i < dataEachString.length; i++) {
            dataTable[k] = dataEachString[i].split(";");
            k++;
        }
        return dataTable;
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
