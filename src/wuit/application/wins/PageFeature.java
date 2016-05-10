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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import wuit.crawler.database.PageFeatureInfo;
import wuit.crawler.database.PageFeaturesDB;

/**
 *
 * @author lxl
 */
public class PageFeature {
        public int rowSel = -1;
        PageFeaturesDB db;
        List<PageFeatureInfo> rs;
        
        JTable table = null;
        JTextArea txtArea = null;
        DefaultTableModel mode1 = null;        
        JPanel panel1 = new JPanel ();
        
        public PageFeature(){
            initComponents();
            
            InitTable();
            //_table = table;
            db = new PageFeaturesDB();
            rs = new ArrayList<PageFeatureInfo>();
            db.SelectPageFeatures(rs);
            //feature = new FeatureTable(jTableFeature);
            display();
        }
        
        public void InitTable(){
            table.setRowHeight(24);
            if(table == null)
                return;
            String[] header = { "序号","名称","模式"}; //定义表头
            mode1 = new DefaultTableModel(header,0){
                public boolean isCellEditable(int row, int column){
                    if(column==0)
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

            TableColumn Col1 = table.getColumnModel().getColumn(1);
            Col1.setPreferredWidth(100);            
            Col1.setMaxWidth(300);
            
            TableColumn Col2 = table.getColumnModel().getColumn(2);
            Col2.setPreferredWidth(40);            
            Col2.setMaxWidth(80);            
        }        
        
        public void addnew(){
            int id = rs.get(rs.size()-1).id + 1;
            String[] row = {id + "","",0 + "",""};
            mode1.addRow(row);
            
            PageFeatureInfo info = new PageFeatureInfo();
            info.mode = 0;
            info.id = id;
            info.featrue = "";
            info.stat = 1;
            rs.add(info);
        }
        
        public void delete(){
            if(rowSel<0)
                return;            
           int row = rowSel;
           int i = 0;
           for(i = 0; i < rs.size();i ++){
               if(rs.get(i).stat == 3)
                   continue;
               row --;
               if(row < 0)
                   break;
           }
           row = i;            
            if(row<0)
                return;
            for(i = 0; i< rs.get(row).fields.size(); i++){
                if(rs.get(row).fields.get(i).stat == 1)
                    rs.get(row).fields.remove(i);
                else
                    rs.get(row).fields.get(i).stat = 3;
            }
            
            if(rs.get(row).stat == 1)
                rs.remove(row);
            else
                rs.get(row).stat = 3;
            display();
            rowSel = -1;
        }        
        
        private int getRsIndex(){
           if(rowSel<0)
                return -1;
           int i = 0;
           int delRow = 0;
           while(i<=rowSel + delRow){
               if(rs.get(i).stat == 3)
                   delRow++;
               i++;
           }
           return rowSel + delRow; 
        }
        
        public void update(){
           int row = getRsIndex();
           if(row<0)
               return;
           
           String name = (String)mode1.getValueAt(row,1);
           int mode = Integer.parseInt((String)mode1.getValueAt(row,2));
           String feature = txtArea.getText();
           //feature = feature.replaceAll("\\{2,}", "\\");
           
           String _name = rs.get(row).name;
           String _feature = rs.get(row).featrue;
           int _mode = rs.get(row).mode;
           
           if((_mode != mode || (_name != null && !_name.equals(name)) 
                   || (_feature != null && !_feature.equals(feature)))
                   && rs.get(row).stat == 0 ){
                rs.get(row).stat = 2;
            }
           
           rs.get(row).name = name;
           rs.get(row).featrue = feature;
           rs.get(row).mode = mode;           
        }
        
        public void display( ){
            if(table == null || rs == null)
                return;
            while(mode1.getRowCount()>0){
                mode1.removeRow(0);
            }            
            
            int rowNum = 0;
            for(int i=0; i<rs.size();i++){
                if(rs.get(i).stat == 3)
                    continue;
                String[] row = {rowNum+"",rs.get(i).name,rs.get(i).mode+""};
                mode1.addRow(row);
                rowNum++;
            }
        }
        
        public void setTextArea(){
           int row = getRsIndex();
           if(row<0)
               return;           
           txtArea.setText(rs.get(row).featrue);
        }        
        
        public void refresh(){
            if(db == null)
                db = new PageFeaturesDB();
            if(rs == null)
                rs = new ArrayList<PageFeatureInfo>();
            else
                rs.clear();
            db.SelectPageFeatures(rs);
            display();
            rowSel = -1;
        }
        
        public void save(){
            if(rowSel>=0)
                update();
            for(int i=0; i<rs.size(); i++){
                PageFeatureInfo _feature = new PageFeatureInfo();                
                _feature.name = rs.get(i).name;
                _feature.featrue = rs.get(i).featrue;
                _feature.mode = rs.get(i).mode;
                _feature.id = rs.get(i).id;
//                _feature.fields = rs.get(i).fields;
                if(rs.get(i).stat == 1){                    
                    db.InsertFeature(_feature);
                    rs.get(i).stat = 0;
                    continue;
                }
                if(rs.get(i).stat == 2){
                   
                    db.UpdateFeature(_feature);
                    rs.get(i).stat = 0;
                    continue;
                }
                if(rs.get(i).stat == 3){
                    db.DeleteFeature(_feature);
                    rs.remove(i);
                    continue;
                }
            }
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
                    rowSel = selRow;
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
            panel2.setLocation(0, 0);
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
}
