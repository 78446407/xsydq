package com.company;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * 导入小说主界面
 */
public  class Novelreader extends JFrame implements ActionListener {  //创建Novelreader类用来建立记事本小说阅读器GUI

    //整个框家的内容面板
    Container container;
    //上
    private JLabel alreadyBook;
    //中
    private JPanel centerPanel = new JPanel();//放按钮
    //下
    private JButton button;
    //定义窗体组件
    JFileChooser fc = new JFileChooser();
    JButton tempButton;

    public  Novelreader () {
        init();
        //设置窗体标题
        this.setTitle("小说阅读器");
        //设置窗体大小
        this.setSize(500,500);
        //设置窗体起始位置
        this.setLocation(400,150);
        //设置窗体关闭方式
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗体可见
        this.setVisible(true);
        //设置不可改变大小
        this.setResizable(false);
    }

    /**
     * 初始化此界面
     */
    public void init(){
        container = this.getContentPane();//获取内容面板
        container.setLayout(new BorderLayout());

        //上
        alreadyBook = new JLabel("已导入的小说:");
        container.add(alreadyBook,BorderLayout.NORTH);

        //中
        centerPanel.setLayout(new GridLayout(5,5));
        container.add(centerPanel, BorderLayout.CENTER);

        //下
        button=new JButton("导入小说");//设置按钮名称
        Container tempCon = new Container();
        tempCon.setLayout(new FlowLayout());
        //设置按钮
        tempCon.add(button);//添加按钮
        button.addActionListener(this);
        container.add(tempCon, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        //创建NotePad实例对象
        Novelreader read =new Novelreader();
    }

    /**
     * 打开文件
     */
    public void OpenFunc(){
        int returnVal =  fc.showOpenDialog(this);
        File file = fc.getSelectedFile();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            tempButton = new JButton(file.getName());
            centerPanel.add(tempButton);
            tempButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ReadingClass(file.getAbsolutePath());
                }
            });
            centerPanel.revalidate();//重绘面板
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == button) {
            this.OpenFunc();
        }
    }
}