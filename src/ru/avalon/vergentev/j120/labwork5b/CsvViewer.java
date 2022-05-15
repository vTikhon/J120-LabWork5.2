package ru.avalon.vergentev.j120.labwork5b;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class CsvViewer extends JFrame implements WindowListener {
    private File file = new File(System.getProperty("user.dir"));
    private File[] files;
    private StringBuilder data;
    private String [] titleTable;
    private final JComboBox<File[]> comboBox;
    private JFrame frameForTable = new JFrame();
    private final JCheckBox checkBox = new JCheckBox("<<<< Have the title ??? Check On is YES");

    private final JMenu menuFile = new JMenu("Choose file");

    public CsvViewer() {
        setTitle("CSV Viewer");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        addWindowListener(this);

        JButton showTable = new JButton("Show table");
        add(showTable, BorderLayout.NORTH);
        showTable.addActionListener(e -> {algorithmShowTableIsPushed();});
        add(checkBox, BorderLayout.WEST);
        checkBox.addChangeListener(e -> {isCheckBoxOn();});
        comboBox = new JComboBox(getCsvFilesInDirectory());
        add(comboBox, BorderLayout.SOUTH);
        comboBox.addItemListener(e -> {getItemBoxIsSelected(comboBox);});

        addJMenuBar();
    }

    private void addJMenuBar() {
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        bar.add(menuFile);
        addOpenFileOption();
    }

    private void addOpenFileOption(){
        JMenuItem open = new JMenuItem("Open file");
        open.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int i = chooser.showOpenDialog(this);
            if (i == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
                if (file.getName().endsWith(".csv")) {
                    comboBox.setSelectedItem(file);
                    if (frameForTable.isShowing()) {
                        frameForTable.dispose();
                    }
                    algorithmShowTableIsPushed();
                }
            }
        });
        menuFile.add(open);
    }

    private boolean isCheckBoxOn () {return checkBox.isSelected();}

    private File getItemBoxIsSelected (JComboBox<File[]> comboBox) {
        if (frameForTable.isShowing()) {
            frameForTable.dispose();
            addTable();
            frameForTable.setVisible(true);
        }
        setTitle(String.valueOf(comboBox.getSelectedItem()));
        return (File) comboBox.getSelectedItem();
    }

    private void algorithmShowTableIsPushed () {
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
            JTable table = new JTable(getTableFromData(), titleTable);
            JScrollPane panelForTable = new JScrollPane(table);
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
        String[] dataEachString = reader().toString().split("\n");
        //задаём заголовок таблицы
        titleTable = dataEachString[0].split(";");
        String[][] dataTable;
        if (isCheckBoxOn()) {
            //формируем двумерный массив для JTable
            dataTable = new String[dataEachString.length-1][];
            for (int i = 1; i < dataEachString.length; i++) {
                dataTable[i-1] = dataEachString[i].split(";");
            }
        } else {
            //задаём заголовок таблицы
            for (String i : titleTable) {i = "";}
            //формируем двумерный массив для JTable
            dataTable = new String[dataEachString.length][];
            for (int i = 0; i < dataEachString.length; i++) {
                dataTable[i] = dataEachString[i].split(";");
            }
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

