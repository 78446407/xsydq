package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 初始化设置字体主界面
 */
public class SetFontClass extends JFrame implements ActionListener {
    private JPanel centerPanel;
    private JPanel southPanel;
    //JLabel
    private JLabel sizeLabel;
    private JLabel sytleLabel;

    private JTextField sizeField;
    private JComboBox sytleBox;
    //相关组件
    private JTextArea showTextArea;

    private JButton sureButton;//确认按钮
    private JButton defaultFontButton;//默认字体

    public SetFontClass(JTextArea showTextArea){
        init();
        this.showTextArea = showTextArea;
        this.setTitle("设置字体");
        this.setSize(new Dimension(300, 200));
        this.setResizable(false);
        this.setVisible(true);
        this.setLocation(400,150);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void init(){
        this.setLayout(new BorderLayout());
        centerPanel = new JPanel();
        southPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2,2));
        southPanel.setLayout(new FlowLayout());
        sizeLabel = new JLabel("字体大小：");
        sytleLabel = new JLabel("字体样式");
        sizeField = new JTextField(15);

        //初始化下拉列表
        String[] styleBoxS = new String[]{"宋体","楷体","Symbol","仿宋"};
        sytleBox = new JComboBox(styleBoxS);
        sytleBox.setSelectedIndex(0);

        sureButton = new JButton("确认");
        defaultFontButton = new JButton("恢复默认");
        sureButton.addActionListener(this);//添加监听
        defaultFontButton.addActionListener(this);

        //放在界面中
        centerPanel.add(sizeLabel);
        centerPanel.add(sizeField);
        centerPanel.add(sytleLabel);
        centerPanel.add(sytleBox);
        southPanel.add(sureButton);//按钮1
        southPanel.add(defaultFontButton);//按钮2
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * 更改字体相关设置
     */
    public void setFontFunc(){
        if(sizeField.getText().equals("")){
            JOptionPane.showMessageDialog(this, "不能为空", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        String str = sizeField.getText();
        int sizeF = Integer.parseInt(str);
        String sytleF = (String) sytleBox.getSelectedItem();

        Font font = new Font(sytleF, Font.PLAIN, sizeF);
        showTextArea.setFont(font);//改变文本域中的字符样式
    }

    /**
     * 恢复默认
     */
    public void defaultFontFunc(){
        Font font = new Font("宋体",Font.PLAIN,20);
        showTextArea.setFont(font);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source == sureButton) {
            this.setFontFunc();
        } else if (source == defaultFontButton) {
            this.defaultFontFunc();
        }
    }

}
