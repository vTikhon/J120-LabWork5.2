package ru.avalon.vergentev.j120.labwork5b;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class CsvViewer extends JFrame implements ActionListener, WindowListener {
    JComboBox comboBox = new JComboBox();
    JButton showTable = new JButton("Show table");
    JFrame frameForTable = new JFrame();
    JScrollPane panelForTable;
    String[] testColumn = {"111", "222", "333", "444", "555"};
    String [][] testData = {{"a","a","a","a","a"},{"b","b","b","b","b"},{"c","c","c","c","c"}};
    JTable table = new JTable();

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
        if (e.getSource() == showTable) algorithmShowTablePushed();
    }

    private void algorithmShowTablePushed() {
        if (!frameForTable.isShowing()) {
            addTable();
            frameForTable.setVisible(true);
        } else {
            frameForTable.dispose();
        }
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
