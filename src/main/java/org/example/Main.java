package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Arrays;
public class Main {

    private JFrame frame;
    private JPanel mainPanel, inputPanelMouse_X_Axis, panelSleep, listPanel;
    private JTextField inputFieldMouse_Y_Axis, inputFieldMouse_X_Axis;
    private JTextField inputStepSleep, inputRecordSleep;
    private JButton testMouseXYButton, addRow, stopProgram;
    private ArrayList<JLabel> labelList;
    private ArrayList<String> arrayListString;
    private ArrayList<JButton> editButtonList, deleteButtonList,testXAxisKeyButtonList,testYAxisKeyButtonList;
    private ArrayList<JTextField> xAxisKeyList,yAxisKeyList, recordsList;

    private JPanel panel;

    public Main() {

        panel = new JPanel(new BorderLayout());
        mainPanel = new JPanel(new BorderLayout());

        frame = new JFrame("Load calls 1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 600);
        frame.setLocationRelativeTo(null);

        inputFieldMouse_Y_Axis = createPlaceholderTextField("mouse X axis: 200",16);
        inputFieldMouse_X_Axis = createPlaceholderTextField("mouse Y axis: 200",10);


        testMouseXYButton = new JButton("Test");
        testMouseXYButton.addActionListener(new testMouseXY());

        addRow = new JButton("Add Row");

        inputPanelMouse_X_Axis = new JPanel(new BorderLayout());

        ////////////////////////////////////////////////////////////////////////////
        //panelSleep = new JPanel(new BorderLayout());
        panelSleep = new JPanel(new GridLayout(1, 2));
        inputStepSleep = createPlaceholderTextField("Step sleep: 5",5);
        inputRecordSleep = createPlaceholderTextField("Record sleep: 10",5);


       //panelSleep.setPreferredSize(new Dimension(300, 1));
       // inputStepSleep.setPreferredSize(new Dimension(inputStepSleep.getPreferredSize().width, 10));
        panelSleep.add(inputStepSleep, BorderLayout.CENTER);
        panelSleep.add(inputRecordSleep, BorderLayout.WEST);

        /*JButton stopProgram = new JButton("stop program");
        stopProgram.addActionListener(e -> { System.out.print("ffffffffffffffffffff"); });
        panelSleep.add(stopProgram, BorderLayout.PAGE_END);*/


        //panelSleep.setBorder(new EmptyBorder(10, 10, 10, 10));

        //mainPanel.add(panelSleep, BorderLayout.PAGE_START);
        //inputPanelMouse_X_Axis.add(inputStepSleep, BorderLayout.CENTER);
        ////////////////////////////////////////////////////////////////////////////

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

        JPanel panelTop = new JPanel(new GridLayout(2, 1));

        panelTop.add(panelSleep);
        panelTop.add(inputPanelMouse_X_Axis);


        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(panelTop, BorderLayout.NORTH);


        listPanel = new JPanel(new GridLayout(0, 1, 0, 5));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        addRow.addActionListener(e -> { createRow("pathImage"); });

       JPanel panelButtonOkStop = new JPanel(new GridLayout(1, 2));
        // JPanel panelButtonOkStop= new JPanel(new BorderLayout());

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> {
            stopProgram.setEnabled(true);
            Sleep(1000);
            ok();
        });
        panelButtonOkStop.add(ok);

        stopProgram = new JButton("stop");
        stopProgram.setEnabled(false);
        stopProgram.addActionListener(e -> { ok(); });
        panelButtonOkStop.add(stopProgram);

        mainPanel.add(panelButtonOkStop,BorderLayout.SOUTH );



        frame.setContentPane(mainPanel);


        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Main app = new Main();
    }


    private void ok(){
        /*int mouseX =  Integer.parseInt(inputFieldMouse_X_Axis.getText());
        int mouseY =  Integer.parseInt(inputFieldMouse_Y_Axis.getText());*/

        int mouseX = 200;
        int mouseY = 200;
        if(inputFieldMouse_Y_Axis.getText().equals("mouse Y axis: 200") != true){
            mouseY =  Integer.parseInt(inputFieldMouse_Y_Axis.getText());
        }
        if(inputFieldMouse_X_Axis.getText().equals("mouse X axis: 200") != true){
            mouseX =  Integer.parseInt(inputFieldMouse_X_Axis.getText());
        }

        MouseMove(mouseX,mouseY);

        ArrayList<String[]> arrayOfArraysRecords = new ArrayList<>();
        for(int i = 0; i<recordsList.size(); i++){
            String records = recordsList.get(i).getText();
            String text = records.trim();
            String[] recordArray;

            if(containsCharacter(text, '\'')){
                recordArray = text.split("'");
            }else{
                recordArray = text.split(" ");
            }

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
        stopProgram.setEnabled(false);
    }

    private static boolean containsCharacter(String text, char targetChar) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == targetChar) {
                return true;
            }
        }
        return false;
    }


    public void startProgram(ArrayList<String[]> arrayOfArraysRecords, ArrayList<int[]> arrayOfArrays){
        // Access and print elements
        for (int y = 0; y < arrayOfArraysRecords.get(0).length; y++) {;
            for (int i = 0; i < arrayOfArraysRecords.size(); i++) {
                String[] array = arrayOfArraysRecords.get(i);

                int xAxis = arrayOfArrays.get(i)[0];
                int yAxis = arrayOfArrays.get(i)[1];

                Sleep(400);
                printRecord(xAxis, yAxis, array[y].trim());
                Sleep(400);
            }
            KeyPress(KeyEvent.VK_T);
        }
    }

    private void createRow(String path){
        JLabel label = new JLabel();

        JButton deleteButton = new JButton("X");
        //JButton testXAxisKeyButton = new JButton("Test");
        JButton testYAxisKeyButton = new JButton("Test");
        deleteButton.addActionListener(new DeleteButtonListener());

        testYAxisKeyButton.addActionListener(new testYAxisKey());
        //testXAxisKeyButton.addActionListener(new testXAxisKey());

        JTextField inputRecords = createPlaceholderTextField("Records",10);
        JTextField xAxisKey = createPlaceholderTextField("X axis key",6);
        JTextField yAxisKey = createPlaceholderTextField("Y axis key",6);

        JPanel panel = new JPanel(new  FlowLayout());

        panel.add(label, BorderLayout.CENTER);
        panel.add(deleteButton, BorderLayout.WEST);

        panel.add(inputRecords);

        ////////////////////////////////////
        JPanel panel2 = new JPanel(new GridLayout(2, 1)); // 2 rows, 1 column
        JLabel label1 = new JLabel("|||||");
        JLabel label2 = new JLabel("0");
        panel2.add(label1);
        panel2.add(label2);
        panel.add(panel2);
        ////////////////////////////////////

        panel.add(yAxisKey);
        panel.add(xAxisKey);
        panel.add(testYAxisKeyButton);

        setupTextChangeListener(inputRecords, label2, label1);

        deleteButtonList.add(deleteButton);
        xAxisKeyList.add(xAxisKey);
        yAxisKeyList.add(yAxisKey);
        recordsList.add(inputRecords);
        //testXAxisKeyButtonList.add(testXAxisKeyButton);
        testYAxisKeyButtonList.add(testYAxisKeyButton);
        arrayListString.add(path);

        listPanel.add(panel, BorderLayout.NORTH);
        listPanel.revalidate();
        listPanel.repaint();
    }

    private class ddd implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.print("fffffffffffff");
        }
    }

    private class testYAxisKey implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int mouseX =  Integer.parseInt(inputFieldMouse_X_Axis.getText());
            int mouseY =  Integer.parseInt(inputFieldMouse_Y_Axis.getText());
            MouseMove(mouseX, mouseY);

            int index = testYAxisKeyButtonList.indexOf(e.getSource());
            int stepsY = Integer.parseInt(yAxisKeyList.get(index).getText());
            int stepsX = Integer.parseInt(xAxisKeyList.get(index).getText());

            Sleep(1000);
            for(int i = 0; i < stepsY; i++){
                KeyPress(KeyEvent.VK_DOWN);
            }
            ///////////////////////////////
            for(int i = 0; i < stepsX; i++){
                KeyPress(KeyEvent.VK_RIGHT);
            }
            Sleep(5000);
            for(int i = 0; i < stepsX; i++){
                KeyPress(KeyEvent.VK_LEFT);
            }
            ///////////////////////////////
            for(int i = 0; i < stepsY; i++){
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

    public void  printRecord(int recordArrowKeyXAxis, int recordArrowKeyYAxis, String record){
        int mi = 1000;

        /*if(inputStepSleep.getText().equals("Step sleep: 5") != true){
            mi =  Integer.parseInt(inputStepSleep.getText());
        }*/

        //Move arrow key, Y axis
        for(int i = 0; i < recordArrowKeyYAxis; i++){
            KeyPress(KeyEvent.VK_DOWN);
            Sleep(mi);
        }

        //Move R arrow key, X axis
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


    private static void setupTextChangeListener(JTextField textField,  JLabel recordsLength,  JLabel equal) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleTextChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleTextChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }

            private void handleTextChange() {
                if(textField.getText().equals("Records") == false && textField.getText().equals("") == false){
                    String records = textField.getText();
                    String text = records.trim();
                    String[] recordArray;

                    if(containsCharacter(text, '\'')){
                        recordArray = text.replaceAll("\\s", "").split("'");
                    }else{
                        recordArray = text.split(" ");
                    }
                    recordsLength.setText(Integer.toString( recordArray.length));

                    boolean allSameLength = Arrays.stream(recordArray)
                            .allMatch(str -> str.length() == recordArray[0].length());
                    if(allSameLength){
                        equal.setForeground(Color.green);
                    } else {
                        equal.setForeground(Color.red);
                    }
                }

            }
        });
    }
}

