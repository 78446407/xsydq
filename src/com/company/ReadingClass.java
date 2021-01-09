package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 阅读小说主界面
 */
public class ReadingClass   extends JFrame implements ActionListener {

    //上
    private JButton SetFontButton;
    //中
    private JTextArea showArea;
    private JScrollPane scrollPane;
    //下
    private JLabel changeLabel;
    private JTextField PageField;
    private JButton changeButton;
    private JButton upButton;
    private JButton downButton;
    //
    private String filePath;
    private long PageCount;//总页数
    private long currentPage = 0;//当前页数,从0开始
    BufferedReader bufferedReader= null;

    public ReadingClass(String filePath){
        this.filePath = filePath;
        //初始化
        init();
        //操作
        OptionFunc(filePath);
        //设置窗体标题
        this.setTitle("阅读");
        //设置窗体大小
        this.setSize(1000,800);
        //设置窗体起始位置
        this.setLocation(400,150);
        //设置窗体关闭方式
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //设置窗体可见
        this.setVisible(true);
        //设置不可改变大小
        this.setResizable(false);
    }

    /**
     * 显示字体调节主界面
     */
    public void init(){
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        //上
        SetFontButton = new JButton("调整字体");
        SetFontButton.addActionListener(this);
        container.add(SetFontButton, BorderLayout.NORTH);
        //中
        showArea = new JTextArea();
        showArea.setFont(new Font("宋体",Font.PLAIN,20));
        showArea.setEnabled(false);
        scrollPane = new JScrollPane(showArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setSize(new Dimension(1000,700));
        container.add(scrollPane, BorderLayout.CENTER);
        //下
        Container tempCon = new Container();
        tempCon.setLayout(new FlowLayout());
        changeLabel = new JLabel("跳转页码");
        PageField = new JTextField(5);
        PageField.setText("1");
        changeButton = new JButton("跳转");
        changeButton.addActionListener(this);
        upButton = new JButton("上一页");
        upButton.addActionListener(this);
        downButton = new JButton("下一页");
        downButton.addActionListener(this);

        tempCon.add(changeLabel);
        tempCon.add(PageField);
        tempCon.add(changeButton);
        tempCon.add(upButton);
        tempCon.add(downButton);

        container.add(tempCon, BorderLayout.SOUTH);
    }


    /**
     * 进入小说第一页
     * @param path 路径
     */
    public void OptionFunc(String path){
        int count = 0;
        try {
            PageCount = (Files.lines(new File(filePath).toPath()).count())/50+1;//总页数
            String str = new String();
            bufferedReader  = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            bufferedReader.mark((int) new File(filePath).length()+1);
            while((str = bufferedReader.readLine())!=null){
                showArea.append(str+"\n");
                count++;
                if(count==50){
                    return ;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 翻到小说上一页
     */
    public void upPageFunc(){
        if(currentPage == 0) {
            JOptionPane.showMessageDialog(this,"这是第一页","Error",JOptionPane.INFORMATION_MESSAGE);
            return ;
        }
        String str = new String();
        long count = 50*(currentPage-1);
        try {
            showArea.setText("");//清空原来的文字
            bufferedReader.reset();
            long tempI = 0;
            while(tempI<=count){
                bufferedReader.readLine();
                tempI++;
            }
            while((str = bufferedReader.readLine())!=null){
                showArea.append(str+"\n");
                count++;
                if(count>50*currentPage){
                    return ;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            currentPage--;
            PageField.setText(String.valueOf(currentPage+1));
        }
     }

    /**
     * 翻到小说下一页
     */
    public void downPageFunc(){
        if(currentPage == PageCount) {
            JOptionPane.showMessageDialog(this,"已经是最后一页了","Error",JOptionPane.INFORMATION_MESSAGE);
            return ;
        }
         String str = new String();
         long count = 50*currentPage;
         try {
             showArea.setText("");//清空原来的文字
             while((str = bufferedReader.readLine())!=null){
                 showArea.append(str+"\n");
                 count++;
                 if(count > 50*(currentPage+1)){
                     return ;
                 }
             }
         } catch (IOException e) {
             e.printStackTrace();
         }finally {
             currentPage++;
             PageField.setText(String.valueOf(currentPage+1));
         }
     }

    /**
     * 跳转到用户指定页码
     */
     public void changePageFunc(){
         if(PageField.getText().equals("")) {
             JOptionPane.showMessageDialog(this, "不能为空", "Error", JOptionPane.ERROR_MESSAGE);
             return ;
         }
         if(!Pattern.matches("\\d+",PageField.getText())){
             JOptionPane.showMessageDialog(this, "只能包含数字", "Error", JOptionPane.ERROR_MESSAGE);
             return ;
         }
         long number = Long.parseLong(PageField.getText());
         if(!(number>=0&&number<=PageCount)){
             JOptionPane.showMessageDialog(this, "不包含此页码", "Error", JOptionPane.ERROR_MESSAGE);
             return ;
         }
         String str = new String();
         long count = 50*(number-1);
         try {
             showArea.setText("");//清空原来的文字
             bufferedReader.reset();
             long tempI = 0;
             while(tempI<count){
                 bufferedReader.readLine();
                 tempI++;
             }
             while((str = bufferedReader.readLine())!=null){
                 showArea.append(str+"\n");
                 count++;
                 if(count > 50*(number)){
                     return ;
                 }
             }
         } catch (IOException e) {
             e.printStackTrace();
         }finally {
             currentPage = number-1;
             PageField.setText(String.valueOf(currentPage+1));
         }
     }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == SetFontButton){
            new SetFontClass(showArea);
        }else if(source == upButton){
            this.upPageFunc();
        }else if(source == downButton){
            this.downPageFunc();
        }else if(source == changeButton){
            this.changePageFunc();
        }
    }
}
