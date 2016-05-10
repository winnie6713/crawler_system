/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.application.wins;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author lxl
 */
public class JTabbedPaneTitle extends JPanel implements MouseListener{
    private static final long serialVersionUID = 1L;
//    private String title;
    private JLabel txtTitle;
    private JLabel lblClose;
    private JTabbedPane tab;
    private Object object;
    public JTabbedPaneTitle(JTabbedPane tab, String title,Object object) {
        this.setOpaque(true);
        this.tab = tab;
        this.object = object;
        //
        txtTitle = new JLabel(title);
//        txtTitle.setEditable(false);
        txtTitle.addMouseListener(this);
        txtTitle.setOpaque(true);
        txtTitle.setLocation(0, 0);
        txtTitle.setFont(new java.awt.Font("宋体", 0, 14));
  
        lblClose = new JLabel(" ");
        lblClose.addMouseListener(this);
        lblClose.setOpaque(true);
        //lblClose.setPreferredSize(new Dimension (100,20));
        //
        this.add(txtTitle);
        this.add(lblClose);
        //
        this.addMouseListener(this);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.lblClose)) {
            System.out.println(this.txtTitle.getText());
            if(this.txtTitle.getText().equals("垂直搜索") || this.txtTitle.getText().equals("关键词搜索")){
                PageCrawler crawler = (PageCrawler)this.object;
                crawler.stopCrawler();
            }
            tab.remove(tab.getSelectedComponent());
        }
    }
 
 
    @Override
    public void mouseEntered(MouseEvent e) {
        lblClose.setText("X");
    }
    @Override
    public void mouseExited(MouseEvent e) {
        lblClose.setText(" ");
    }
    @Override
    public void mousePressed(MouseEvent e) {
    // 此处注意希望保留原有Tab选项的换页功能，必须覆盖press方法，而不是click方法
        MouseEvent ne = SwingUtilities.convertMouseEvent(txtTitle, e, tab);
        tab.dispatchEvent(ne); 
    }
    @Override
    public void mouseReleased(MouseEvent e) {
     // TODO Auto-generated method stub
    } 
}


