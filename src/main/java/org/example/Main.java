package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main {

    private JFrame frame;
    private JPanel mainPanel, inputPanelMouse_X_Axis, inputPanelMouse_Y_Axis, listPanel;
    private JTextField inputFieldMouse_Y_Axis, inputFieldMouse_X_Axis;
    private JButton testMouseXYButton, addRow;
    private ArrayList<JLabel> labelList;
    private ArrayList<String> arrayListString;
    private ArrayList<JButton> editButtonList, deleteButtonList,testXAxisKeyButtonList,testYAxisKeyButtonList;
    private ArrayList<JTextField> xAxisKeyList,yAxisKeyList, recordsList;

    private JPanel panel;

    public Main() {

        panel = new JPanel(new BorderLayout());

        frame = new JFrame("Load calls 1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);

        inputFieldMouse_Y_Axis = createPlaceholderTextField("mouse X axis",20);
        inputFieldMouse_X_Axis = createPlaceholderTextField("mouse Y axis",10);

        testMouseXYButton = new JButton("Test");
        testMouseXYButton.addActionListener(new testMouseXY());

        addRow = new JButton("Add Row");

        inputPanelMouse_X_Axis = new JPanel(new BorderLayout());

        inputPanelMouse_X_Axis.add(inputFieldMouse_X_Axis, BorderLayout.CENTER);
        inputPanelMouse_X_Axis.add(inputFieldMouse_Y_Axis, BorderLayout.WEST);
        inputPanelMouse_X_Axis.add(testMouseXYButton, BorderLayout.EAST);
        inputPanelMouse_X_Axis.add(addRow, BorderLayout.PAGE_END);

        labelList = new ArrayList<>();
        editButtonList = new ArrayList<>();
        deleteButtonList = new ArrayList<>();
        xAxisKeyList  = new ArrayList<>();
        yAxisKeyList = new ArrayList<>();
        recordsList = new ArrayList<>();
        testXAxisKeyButtonList = new ArrayList<>();
        testYAxisKeyButtonList = new ArrayList<>();

        arrayListString = new ArrayList<>();

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(inputPanelMouse_X_Axis, BorderLayout.NORTH);
        listPanel = new JPanel(new GridLayout(0, 1, 0, 5));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        addRow.addActionListener(e -> { createRow("pathImage"); });

        JButton button = new JButton("OK");
        button.addActionListener(e -> { ok(); });

        mainPanel.add(button, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Main app = new Main();
    }


    private void ok(){
        int mouseX =  Integer.parseInt(inputFieldMouse_X_Axis.getText());
        int mouseY =  Integer.parseInt(inputFieldMouse_Y_Axis.getText());
        MouseMove(mouseX,mouseY);

        ArrayList<String[]> arrayOfArraysRecords = new ArrayList<>();
        for(int i = 0; i<recordsList.size(); i++){
            String records = recordsList.get(i).getText();
            String text = records.trim();
            String[] recordArray = text.split("'");
            arrayOfArraysRecords.add(recordArray);
        }

        ArrayList<int[]> arrayOfArrays = new ArrayList<>();
        for(int i = 0; i<xAxisKeyList.size(); i++){
            int x = Integer.parseInt(xAxisKeyList.get(i).getText());
            int y = Integer.parseInt(yAxisKeyList.get(i).getText());
            int[] array = {x, y};
            arrayOfArrays.add(array);
        }

        startProgram( arrayOfArraysRecords,  arrayOfArrays);
    }


    public static void startProgram(ArrayList<String[]> arrayOfArraysRecords, ArrayList<int[]> arrayOfArrays){
        // Access and print elements
        for (int y = 0; y < arrayOfArraysRecords.get(0).length; y++) {;
            for (int i = 0; i < arrayOfArraysRecords.size(); i++) {
                String[] array = arrayOfArraysRecords.get(i);

                int xAxis = arrayOfArrays.get(i)[0];
                int yAxis = arrayOfArrays.get(i)[1];

                Sleep(2000);
                printRecord(xAxis, yAxis, array[y].trim());
                Sleep(2000);
            }
            KeyPress(KeyEvent.VK_T);
        }
    }

    private void createRow(String path){
        JLabel label = new JLabel();

        JButton deleteButton = new JButton("X");
        JButton testXAxisKeyButton = new JButton("Test");
        JButton testYAxisKeyButton = new JButton("Test");
        deleteButton.addActionListener(new DeleteButtonListener());

        testYAxisKeyButton.addActionListener(new testYAxisKey());
        testXAxisKeyButton.addActionListener(new testXAxisKey());

        JTextField inputRecords = createPlaceholderTextField("Records",10);
        JTextField xAxisKey = createPlaceholderTextField("X axis key",6);
        JTextField yAxisKey = createPlaceholderTextField("Y axis key",6);

        JPanel panel = new JPanel(new  FlowLayout());

        panel.add(label, BorderLayout.CENTER);
        panel.add(deleteButton, BorderLayout.WEST);

        panel.add(inputRecords);
        panel.add(xAxisKey);
        panel.add(testXAxisKeyButton);
        panel.add(yAxisKey);
        panel.add(testYAxisKeyButton);

        deleteButtonList.add(deleteButton);
        xAxisKeyList.add(xAxisKey);
        yAxisKeyList.add(yAxisKey);
        recordsList.add(inputRecords);
        testXAxisKeyButtonList.add(testXAxisKeyButton);
        testYAxisKeyButtonList.add(testYAxisKeyButton);
        arrayListString.add(path);

        listPanel.add(panel, BorderLayout.NORTH);
        listPanel.revalidate();
        listPanel.repaint();
    }

    private class testXAxisKey implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = testXAxisKeyButtonList.indexOf(e.getSource());

            int steps = Integer.parseInt(xAxisKeyList.get(index).getText());
            int mouseX =  Integer.parseInt(inputFieldMouse_X_Axis.getText());
            int mouseY =  Integer.parseInt(inputFieldMouse_Y_Axis.getText());
            MouseMove(mouseX,mouseY);

            Sleep(1000);
            for(int i = 0; i < steps; i++){
                KeyPress(KeyEvent.VK_RIGHT);
            }
            Sleep(2000);
            for(int i = 0; i < steps; i++){
                KeyPress(KeyEvent.VK_LEFT);
            }
        }
    }

    private class testYAxisKey implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int mouseX =  Integer.parseInt(inputFieldMouse_X_Axis.getText());
            int mouseY =  Integer.parseInt(inputFieldMouse_Y_Axis.getText());
            MouseMove(mouseX, mouseY);

            int index = testYAxisKeyButtonList.indexOf(e.getSource());
            int steps = Integer.parseInt(yAxisKeyList.get(index).getText());

            Sleep(1000);
            for(int i = 0; i < steps; i++){
                KeyPress(KeyEvent.VK_DOWN);
            }
            Sleep(2000);
            for(int i = 0; i < steps; i++){
                KeyPress(KeyEvent.VK_UP);
            }
        }
    }

    public static void  KeyPress(int keyCode){
        try {
            Robot robot = new Robot();

            // Simulate pressing the 't' key
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class testMouseXY implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int mouseX =  Integer.parseInt(inputFieldMouse_X_Axis.getText());
            int mouseY =  Integer.parseInt(inputFieldMouse_Y_Axis.getText());
            MouseMove(mouseX,mouseY);
            System.out.print(  inputFieldMouse_Y_Axis.getText());
            System.out.print(   inputFieldMouse_X_Axis.getText());
        }
    }

    public static void MouseMove(int targetX, int targetY){
        try {
            Robot robot = new Robot();

            robot.mouseMove(targetX, targetY);

            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // Simulate left mouse button press
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // Simulate left mouse button release

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = deleteButtonList.indexOf(e.getSource());

            arrayListString.remove(index);
            deleteButtonList.remove(index);

            listPanel.remove(index);
            listPanel.revalidate();
            listPanel.repaint();
        }
    }

    private static JTextField createPlaceholderTextField(String placeholder, int columns) {
        JTextField textField = new JTextField(columns);
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });

        return textField;
    }
    public static void  Sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void  printRecord(int recordArrowKeyXAxis, int recordArrowKeyYAxis, String record){
        int mi = 10;
        //Move arrow key, Y axis
        for(int i = 0; i < recordArrowKeyYAxis; i++){
            KeyPress(KeyEvent.VK_DOWN);
            Sleep(mi);
        }

        //Move arrow key, X axis
        for(int i = 0; i < recordArrowKeyXAxis; i++){
            KeyPress(KeyEvent.VK_RIGHT);
            Sleep(mi);
        }

        //Pressing shift and R key
        Pressing_shift_R();

        //Input text
        InputText(record);

        //Pressing the Escape key
        KeyPress(KeyEvent.VK_ESCAPE);

        //Reset arrow key, X axis
        for(int i = 0; i < recordArrowKeyXAxis + record.length(); i++){
            KeyPress(KeyEvent.VK_LEFT);
            Sleep(mi);
        }
        //Reset arrow key, Y axis
        for(int i = 0; i < recordArrowKeyYAxis; i++){
            KeyPress(KeyEvent.VK_UP);
            Sleep(mi);
        }
    }

    public static void Pressing_shift_R(){
        try {
            Robot robot = new Robot();

            // Simulate pressing the Shift key
            robot.keyPress(KeyEvent.VK_SHIFT);

            // Simulate pressing the 'r' key
            robot.keyPress(KeyEvent.VK_R);

            // Simulate releasing the 'r' key
            robot.keyRelease(KeyEvent.VK_R);

            // Simulate releasing the Shift key
            robot.keyRelease(KeyEvent.VK_SHIFT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void InputText(String inputText){
        try {
            Robot robot = new Robot();

            for (char c : inputText.toCharArray()) {
                if (c == ':') {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_SEMICOLON); // Colon character
                    robot.keyRelease(KeyEvent.VK_SEMICOLON);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                } else {
                    int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

