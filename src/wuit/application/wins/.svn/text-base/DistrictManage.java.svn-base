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
import javax.swing.BoxLayout;
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
import wuit.crawler.database.DistrictDB;
import wuit.crawler.database.DistrictInfo;
import wuit.crawler.database.PageFeaturesDB;

/**
 *
 * @author lxl
 */
public class DistrictManage {
    private DistrictDB db;    
    JPanel panel1;    
    private MainTable main;
    private SubTable subTable;   
    
    public DistrictManage(){
        db = new DistrictDB();
        initComponents();
    }
    
    private void initComponents(){
        panel1 = new JPanel ();
        panel1.setPreferredSize (new Dimension (600,400));
//        panel1.setBackground (Color.black);        
        
        JPanel panelRight = new JPanel ();
        JPanel panelLeft = new JPanel ();
        
        //panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        panelRight.setLayout(new BoxLayout(panelRight,BoxLayout.Y_AXIS));
        panelLeft.setLayout(new BoxLayout(panelLeft,BoxLayout.Y_AXIS));
        
        JSplitPane jSplitPane1 = new JSplitPane();
        jSplitPane1.setOneTouchExpandable(true);//让分割线显示出箭头
        jSplitPane1.setContinuousLayout(true);//操作箭头，重绘图形
        jSplitPane1.setPreferredSize(new Dimension (800,400));
        jSplitPane1.setOrientation(JSplitPane.HORIZONTAL_SPLIT);//设置分割线方向
        jSplitPane1.setLeftComponent(panelRight);
        jSplitPane1.setRightComponent(panelLeft);
        jSplitPane1.setDividerSize(2);
        jSplitPane1.setDividerLocation(200);//设置分割线位于中央 
        
        main = new MainTable(db);
        main.initComponents(panelRight);
        
        subTable = new SubTable(db);
        subTable.initComponents(panelLeft);
        
        panel1.add(jSplitPane1);
        
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel1);
            panel1.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                .addComponent(panelRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
  //                  .addComponent(panelRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jSplitPane1))
            ); 
            panel1.setBackground(Color.white);        
    }
    
    public void getSubTable(String province){
        subTable.getSubInfo(province);
        subTable.display();
    }
    
    class MainTable{
        DefaultTableModel modeProvince = null;
        public int rowProvince = -1; 
        JTable mainTable;
        
        private DistrictDB db;
        private List<DistrictInfo> rs = new ArrayList<DistrictInfo>();
        
        public MainTable(DistrictDB db){
            this.db = db;
            db.SelectProvince(rs);
        }
        
        public void InitTable(){
            if(mainTable == null)
                return;
            mainTable.setRowHeight(24);
            String[] header = { "序号","代码","省份"}; //定义表头
            modeProvince = new DefaultTableModel(header,0){
                public boolean isCellEditable(int row, int column){
                    if(column == 0)
                        return false;
                    else
                        return true;
                }
            };
            modeProvince.setColumnCount(3);
            mainTable.setModel(modeProvince);
            mainTable.getTableHeader().setFont(new Font("Dialog", 0, 14));         

            TableColumn Col0 = mainTable.getColumnModel().getColumn(0);
            Col0.setPreferredWidth(40);            
            Col0.setMaxWidth(40);  
            Col0.setMinWidth(40);  

            TableColumn Col1 = mainTable.getColumnModel().getColumn(1);
            Col1.setPreferredWidth(100);            
            Col0.setMinWidth(100);
            Col1.setMaxWidth(200);
            
            display();
        }
    
        public void display( ){
            if(mainTable == null || rs == null)
                return;
            while(modeProvince.getRowCount()>0){
                modeProvince.removeRow(0);
            }
            
            int rowNum = 0;
            for(int i=0; i<rs.size();i++){
                if(rs.get(i).stat == 3)
                    continue;
                String[] row = {rowNum+"",rs.get(i).code,rs.get(i).province};
                modeProvince.addRow(row);
                rowNum++;
            }
        } 
        
        public void refresh(){
            if(db == null)
                db = new DistrictDB();
            if(rs == null)
                rs = new ArrayList<DistrictInfo>();
            else
                rs.clear();
            db.SelectProvince(rs);
            display();
            rowProvince = -1;
        }        
        
        private int getRsIndex(){
           if(rowProvince<0)
                return -1;
           int i = 0;
           int delRow = 0;
           while(i<= rowProvince + delRow){
               if(rs.get(i).stat == 3){
                   delRow++;
               }
               i++;
           }
           if(rowProvince + delRow< rs.size())
                return rowProvince + delRow;
           else
               return rs.size()-1;
        }        
        
  
        
        public void addnew(){
            int id = rs.get(rs.size()-1).id + 1;
            String[] row = {id + "","",""};
            modeProvince.addRow(row);
            
            DistrictInfo info = new DistrictInfo();
            info.id = id;
            info.code = "";
            info.province = "";
            info.stat = 1;
            rs.add(info);
        }        
        
        public void update(){
           int row = getRsIndex();
           if(row<0)
               return;
           String code = (String)modeProvince.getValueAt(row,1);
           String province = (String)modeProvince.getValueAt(row,2);
           
           String _code = rs.get(row).code + "";
           String _province = rs.get(row).province + "";
           
           if(((_province != null && !_province.equals(province)) 
                   || (_code != null && !_code.equals(code)))
                   && rs.get(row).stat == 0 ){
                rs.get(row).stat = 2;
            }
           
           rs.get(row).code = code;
           rs.get(row).province = province;           
        }  
        
        public void delete(){
            int row = getRsIndex();            
            if(row<0)
                return;            
            if(rs.get(row).stat == 1)
                rs.remove(row);
            else
                rs.get(row).stat = 3;
            display();
            rowProvince = -1;
        }
        
        public void save(){
            if(rowProvince>=0)
                update();
            for(int i=0; i<rs.size(); i++){
                DistrictInfo _clear = new DistrictInfo();                
                _clear.code = rs.get(i).code;
                _clear.province = rs.get(i).province;
                _clear.id = rs.get(i).id;
//                _feature.fields = rs.get(i).fields;
                if(rs.get(i).stat == 1){                    
//                    db.InsertFeature(_feature);
                    rs.get(i).stat = 0;
                    continue;
                }
                if(rs.get(i).stat == 2){
                    db.UpdateProvince(rs.get(i).id, rs.get(i).code, rs.get(i).province);
                    rs.get(i).stat = 0;
                    continue;
                }
                if(rs.get(i).stat == 3){
//                    db.DeleteFeature(_feature);
//                    rs.remove(i);
                    continue;
                }
            }
        } 
        
        private void initComponents(JPanel panelRight){
            mainTable = new JTable ();
            mainTable.setPreferredScrollableViewportSize(new Dimension(600, 100));//设置表格的大小
            mainTable.setRowHeight (30);//设置每行的高度为20
    //            friends.setRowHeight (0, 20);//设置第1行的高度为15
            mainTable.setRowMargin (5);//设置相邻两行单元格的距离
            mainTable.setRowSelectionAllowed (true);//设置可否被选择.默认为false
            mainTable.setSelectionBackground (Color.white);//设置所选择行的背景色
            mainTable.setSelectionForeground (Color.red);//设置所选择行的前景色
            mainTable.setGridColor (Color.black);//设置网格线的颜色
            mainTable.selectAll ();//选择所有行
    //            table.setRowSelectionInterval (0,2);//设置初始的选择行,这里是1到3行都处于选择状态
            mainTable.clearSelection ();//取消选择
            mainTable.setDragEnabled (false);//不懂这个
            mainTable.setShowGrid (false);//是否显示网格线
            mainTable.setShowHorizontalLines (true);//是否显示水平的网格线
            mainTable.setShowVerticalLines (true);//是否显示垂直的网格线
    //            table.setValueAt ("tt", 0, 0);//设置某个单元格的值,这个值是一个对象
            mainTable.doLayout ();
            mainTable.setBackground (Color.lightGray);

            mainTable.addMouseListener(new MouseAdapter() { 
                public void mouseClicked(MouseEvent e){
                    int selRow = ((JTable)e.getSource()).getSelectedRow();
                    update();
                    rowProvince = selRow;
                    String province = (String)modeProvince.getValueAt(selRow,2);
                    getSubTable(province);
                } 
            });

            JScrollPane pane3 = new JScrollPane (mainTable); 
            
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

            panelRight.add(panel2);
            panelRight.add(pane3);
            InitTable();
        }        
    }
    
    class SubTable{
        DefaultTableModel modeTable = null;
        public int rowSel = -1; 
        JTable table;    
        private List<DistrictInfo> rs = new ArrayList<DistrictInfo>();
        private DistrictDB db;
        public SubTable(DistrictDB db){
            this.db = db;
        }
        
        public void InitTable(){
            if(table == null)
                return;
            table.setRowHeight(24);
            String[] header = { "序号","代码","省份","地市","区县","镇|路"}; //定义表头
            modeTable = new DefaultTableModel(header,0){
                public boolean isCellEditable(int row, int column){
                    if(column == 0)
                        return false;
                    else
                        return true;
                }
            };
            modeTable.setColumnCount(6);
            table.setModel(modeTable);
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
            while(modeTable.getRowCount()>0){
                modeTable.removeRow(0);
            }
            
            int rowNum = 0;
            for(int i=0; i<rs.size();i++){
                if(rs.get(i).stat == 3)
                    continue;
                String[] row = {rowNum+"",rs.get(i).code,rs.get(i).province,rs.get(i).city,rs.get(i).county,rs.get(i).townroad};
                modeTable.addRow(row);
                rowNum++;
            }
        } 
        
        public void getSubInfo(String province){
            rs.clear();
            db.SelectDistrictByProvince(province, rs);
        }
        
        public void refresh(){
            if(db == null)
                db = new DistrictDB();
            if(rs == null)
                rs = new ArrayList<DistrictInfo>();
            else
                rs.clear();
            db.SelectProvince(rs);
            display();
            rowSel = -1;
        }        
        
        private int getRsIndex(){
           if(rowSel<0)
                return -1;
           int i = 0;
           int delRow = 0;
           while(i<= rowSel + delRow){
               if(rs.get(i).stat == 3){
                   delRow++;
               }
               i++;
           }
           if(rowSel + delRow< rs.size())
                return rowSel + delRow;
           else
               return rs.size()-1;
        }        
        
  
        
        public void addnew(){
            int id = rs.get(rs.size()-1).id + 1;
            String[] row = {id + "","",""};
            modeTable.addRow(row);
            
            DistrictInfo info = new DistrictInfo();
            info.id = id;
            info.code = "";
            info.province = "";
            info.stat = 1;
            rs.add(info);
        }        
        
        public void update(){
           int row = getRsIndex();
           if(row<0)
               return;
           String code = (String)modeTable.getValueAt(row,1);
           String province = (String)modeTable.getValueAt(row,2);
           
           String _code = rs.get(row).code + "";
           String _province = rs.get(row).province + "";
           
           if(((_province != null && !_province.equals(province)) 
                   || (_code != null && !_code.equals(code)))
                   && rs.get(row).stat == 0 ){
                rs.get(row).stat = 2;
            }
           
           rs.get(row).code = code;
           rs.get(row).province = province;           
        }  
        
        public void delete(){
            int row = getRsIndex();            
            if(row<0)
                return;            
            if(rs.get(row).stat == 1)
                rs.remove(row);
            else
                rs.get(row).stat = 3;
            display();
            rowSel = -1;
        }
        
        public void save(){
            if(rowSel>=0)
                update();
            for(int i=0; i<rs.size(); i++){
                DistrictInfo _clear = new DistrictInfo();                
                _clear.code = rs.get(i).code;
                _clear.province = rs.get(i).province;
                _clear.id = rs.get(i).id;
//                _feature.fields = rs.get(i).fields;
                if(rs.get(i).stat == 1){                    
//                    db.InsertFeature(_feature);
                    rs.get(i).stat = 0;
                    continue;
                }
                if(rs.get(i).stat == 2){
                    db.UpdateProvince(rs.get(i).id, rs.get(i).code, rs.get(i).province);
                    rs.get(i).stat = 0;
                    continue;
                }
                if(rs.get(i).stat == 3){
//                    db.DeleteFeature(_feature);
//                    rs.remove(i);
                    continue;
                }
            }
        }
        private void initComponents(JPanel panel){
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

                } 
            });
            JScrollPane jScrollPaneLeft = new JScrollPane(table);
            JSplitPane jSplitPane1 = new JSplitPane();

          

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

            panel.add(panel2);
            panel.add(jScrollPaneLeft);
            
            InitTable();

        }        
    }
    
        public static void main(String[] args){
            DistrictManage items = new DistrictManage();
            
            JFrame frame = new JFrame ("JTableDemo");
            frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
            frame.setContentPane (items.panel1);            
            
            frame.pack();
            frame.show();  
        }    
}
