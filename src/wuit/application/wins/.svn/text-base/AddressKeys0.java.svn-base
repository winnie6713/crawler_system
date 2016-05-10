/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.application.wins;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import wuit.crawler.database.SemanticDB;
import wuit.crawler.database.PageLocalInfo;

/**
 *
 * @author lxl
 */
public class AddressKeys0 {
    JTable table;
    JTextArea txtArea;
    SemanticDB db;
    List<PageLocalInfo> rs = new ArrayList<PageLocalInfo>();
    DefaultTableModel mode1 = null;  
    int rowClear = -1;
    
    JPanel panel1 = new JPanel ();
    
    public AddressKeys0(){
        initComponents();
        InitTable();
        db = new SemanticDB();
        db.SelectKeywords0(rs);; 

        display( );        
    }

    public void InitTable(){
        if(table == null)
            return;
        table.setRowHeight(24);
        String[] header = { "序号","名称","分级"}; //定义表头
        mode1 = new DefaultTableModel(header,0){
            public boolean isCellEditable(int row, int column){
                if(column == 0)
                    return false;
                else
                    return true;
            }
        };
        mode1.setColumnCount(3);
        table.setModel(mode1);
        table.getTableHeader().setFont(new Font("Dialog", 0, 14));         

        TableColumn Col0 = table.getColumnModel().getColumn(0);
        Col0.setPreferredWidth(40);            
        Col0.setMaxWidth(40);  
        Col0.setMinWidth(40);  

        TableColumn Col1 = table.getColumnModel().getColumn(1);
        Col1.setPreferredWidth(100);            
        Col0.setMinWidth(100);
        Col1.setMaxWidth(200);
    }    
    
    public void display( ){
        if(table == null || rs == null)
            return;
        while(mode1.getRowCount()>0){
            mode1.removeRow(0);
        }

        int rowNum = 0;
        for(int i=0; i<rs.size();i++){
            if(rs.get(i).state == 3)
                continue;
            String[] row = {rowNum+"",rs.get(i).name,rs.get(i).level+""};
            mode1.addRow(row);
            rowNum++;
        }
    }
    
    private int getRsIndex(){
       if(rowClear<0)
            return -1;
       int i = 0;
       int delRow = 0;
       while(i<= rowClear + delRow){
           if(rs.get(i).state == 3){
               delRow++;
           }
           i++;
       }
       if(rowClear + delRow< rs.size())
            return rowClear + delRow;
       else
           return rs.size()-1;
    } 
    
    public void update(){
       int row = getRsIndex();
       if(row<0)
           return;
       String name = (String)mode1.getValueAt(row,1);
       String xml = txtArea.getText();
       String level = (String)mode1.getValueAt(row,2);

       String _name = rs.get(row).name + "";
       String _xml = rs.get(row).content + "";
       String _level = rs.get(row).level + "";

       if(((_name != null && !_name.equals(name)) 
               || (_xml != null && !_xml.equals(xml)) 
               || (level != null && !_level.equals(level)))
               && rs.get(row).state == 0 ){
            rs.get(row).state = 2;
        }

       rs.get(row).name = name;
       rs.get(row).content = xml;
       if(level != null)
            rs.get(row).level = Integer.parseInt(level);
       else
           rs.get(row).level = 0;
    }     
    
    public void setTextArea(){
       int row = getRsIndex();
       if(row<0)
           return;           
       txtArea.setText(rs.get(row).content);
    }    
    
    public void addnew(){
        int id = rs.get(rs.size()-1).id + 1;
        String[] row = {id + "",""};
        mode1.addRow(row);

        PageLocalInfo info = new PageLocalInfo();
        info.id = id;
        info.name = "";
        info.state = 1;
        info.level = 0;
        rs.add(info);
    }     
    
    public void delete(){
        int row = getRsIndex();            
        if(row<0)
            return;            
        if(rs.get(row).state == 1)
            rs.remove(row);
        else
            rs.get(row).state = 3;
        display();
        rowClear = -1;
    }    
    
    public void save(){
        if(rowClear>=0)
            update();
        for(int i=0; i<rs.size(); i++){
            if(rs.get(i).state == 1){                    
                db.InsertKeywords0(rs.get(i).name, rs.get(i).level, rs.get(i).content);
                rs.get(i).state = 0;
                continue;
            }
            if(rs.get(i).state == 2){
                db.UpdateKeywords0(rs.get(i).content,rs.get(i).level, rs.get(i).name, rs.get(i).id);
                rs.get(i).state = 0;
                continue;
            }
            if(rs.get(i).state == 3){
                db.DeleteKeywords0(rs.get(i).id);
//                    rs.remove(i);
                continue;
            }
        }
    }    
    
    public void refresh(){
        if(db == null)
            db = new SemanticDB();
        if(rs == null)
            rs = new ArrayList<PageLocalInfo>();
        else
            rs.clear();
        db.SelectKeywords0(rs);;
        display();
        rowClear = -1;
    }     
    
    private void initComponents(){
        table = new JTable ();
        table.setPreferredScrollableViewportSize(new Dimension(600, 100));//设置表格的大小
        table.setRowHeight (30);//设置每行的高度为20
//            friends.setRowHeight (0, 20);//设置第1行的高度为15
        table.setRowMargin (5);//设置相邻两行单元格的距离
        table.setRowSelectionAllowed (true);//设置可否被选择.默认为false
        table.setSelectionBackground (Color.white);//设置所选择行的背景色
        table.setSelectionForeground (Color.red);//设置所选择行的前景色
        table.setGridColor (Color.black);//设置网格线的颜色
        table.selectAll ();//选择所有行
//            table.setRowSelectionInterval (0,2);//设置初始的选择行,这里是1到3行都处于选择状态
        table.clearSelection ();//取消选择
        table.setDragEnabled (false);//不懂这个
        table.setShowGrid (false);//是否显示网格线
        table.setShowHorizontalLines (true);//是否显示水平的网格线
        table.setShowVerticalLines (true);//是否显示垂直的网格线
//            table.setValueAt ("tt", 0, 0);//设置某个单元格的值,这个值是一个对象
        table.doLayout ();
        table.setBackground (Color.lightGray);

        table.addMouseListener(new MouseAdapter() { 
            public void mouseClicked(MouseEvent e){
                int selRow = ((JTable)e.getSource()).getSelectedRow();
                update();
                rowClear = selRow;
                setTextArea();
            } 
        });

        JScrollPane pane3 = new JScrollPane (table);            
        panel1.setPreferredSize (new Dimension (600,400));
        panel1.setBackground (Color.black);

        txtArea = new JTextArea();
        txtArea.setFont(new Font("Dialog", 1, 14));
        JScrollPane jScrollPaneLeft = new JScrollPane(txtArea);
        JSplitPane jSplitPane1 = new JSplitPane();

        jSplitPane1.setOneTouchExpandable(true);//让分割线显示出箭头
        jSplitPane1.setContinuousLayout(true);//操作箭头，重绘图形
        jSplitPane1.setPreferredSize(new Dimension (800,400));
        jSplitPane1.setOrientation(JSplitPane.HORIZONTAL_SPLIT);//设置分割线方向
        jSplitPane1.setLeftComponent(pane3);
        jSplitPane1.setRightComponent(jScrollPaneLeft);
        jSplitPane1.setDividerSize(2);
        jSplitPane1.setDividerLocation(200);//设置分割线位于中央           

        JPanel panel2 = new JPanel (new GridLayout(1,1));
        panel2.setPreferredSize(new Dimension (200,30));
        panel2.setMaximumSize(new Dimension (200,30));
        panel2.setMinimumSize(new Dimension (200,30)); 
        panel2.setSize(200, 30);


        JToolBar toolBar = new JToolBar();
        panel2.add(toolBar);

        JButton jButtonAdd = new JButton("添加");
        //jButtonAdd.setSize(200, 30);
        jButtonAdd.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                addnew();
            }
        });

        JButton jButtonDel = new JButton("删除");
        jButtonDel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                delete();
            }
        });            

        JButton jButtonSave = new JButton("保存");
        jButtonSave.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                save();
            }
        });            

        JButton jButtonRefresh = new JButton("刷新");
        jButtonRefresh.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                refresh();
            }
        });            

        toolBar.add(jButtonAdd);
        toolBar.add(jButtonDel);
        toolBar.add(jButtonSave);
        toolBar.add(jButtonRefresh);


        panel1.add(panel2);
        panel1.add (jSplitPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1))
        ); 
        panel1.setBackground(Color.white);
    }   

    public static void main(String[] args){
        AddressKeys0 items = new AddressKeys0();

        JFrame frame = new JFrame ("JTableDemo");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setContentPane (items.panel1);            

        frame.pack();
        frame.show();  
    }    
}
