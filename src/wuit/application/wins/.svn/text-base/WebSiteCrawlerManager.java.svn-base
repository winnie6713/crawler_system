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
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import wuit.crawler.database.DistrictDB;
import wuit.crawler.database.DistrictInfo;
import wuit.crawler.database.WebSitSearchInfo;
import wuit.crawler.database.WebSiteSearchDB;

/**
 *
 * @author lxl
 */
public class WebSiteCrawlerManager {
    JPanel panel1;    
    private BuildingBrands brands;
    private SubTable subTable;
    private BuildingBrands province;
    private WebSiteSearchDB db;
    private DistrictDB provinceDB;
    JTree jTreeProvince;
    
    public WebSiteCrawlerManager(){
        db = new WebSiteSearchDB();
        initComponents();        
    }
    
    private void initComponents(){
        panel1 = new JPanel ();
        panel1.setPreferredSize (new Dimension (600,400));
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
        

        province = new BuildingBrands();     
        province.initComponents(panelRight);
        
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
    
    
    public void getSubTable(String province,String city,String county){
        subTable.getSubInfo(province,city,county);        
        subTable.display();
    }    
    
    
    public void search(){
        String className="";
        if(rowSel!= -1){
            className = rs.get(rowSel).object;
        }else{
            return;
        }
        getInfo info = new getInfo(className);
        new Thread(info).start();
    }
    
    class getInfo  implements Runnable{
        String className = "";
        public getInfo(String className){
            this.className = className;
        }
        
        public void run(){
            try {
                search(className);
                Thread.sleep(10);
//                jLbState.setText("停止运行...");
            } catch (InterruptedException ex) {
                Logger.getLogger(CrawlersMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
        public void search(String className){
            if(className.equals(""))
                return;
            try {
                //QunGuangWH.query();
                //List<String> listVals= QunGuangWH.ListVals;"wuit.common.crawler.WebSit.QunGuangWH"

                
                Class cls = Class.forName(className);

                Method m[] = cls.getDeclaredMethods();
                Object tt = cls.newInstance();
                Class partypes = String.class;

                List<String> listVals= new ArrayList<String>();
                Method method= cls.getMethod("query", partypes);
                method.invoke(tt, "welcome");
                Thread.sleep(100);
                Field field = cls.getField("Vals"); 
                Object property = field.get(cls);            
                Thread.sleep(100);
                for (int i = 0; i < Array.getLength(property); i++) {  
                    listVals.add((String)Array.get(property, i));  
                }            
                

                //List<String> listVals = (Class<List>)fld;
                for(int i=0; i<listVals.size();i++){
                    System.out.println(i + "  &&&  " + listVals.get(i));
                }

            }catch (SecurityException ex) {
                Logger.getLogger(WebSitCrawler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(WebSitCrawler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(WebSitCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }catch (NoSuchMethodException ex) {
                Logger.getLogger(WebSitCrawler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(WebSitCrawler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(WebSitCrawler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(WebSitCrawler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(WebSitCrawler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(WebSitCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    List<DistrictInfo> rsProvince = new ArrayList<DistrictInfo>();
    class BuildingBrands{
        private List<WebSitSearchInfo> rs = new ArrayList<WebSitSearchInfo>();        
        
        public void initComponents(JPanel panel){
            jTreeProvince = new JTree();
            jTreeProvince.setFont(new Font("Dialog", 0, 14)); 

            jTreeProvince.addMouseListener(new MouseAdapter() { 
                public void mouseClicked(MouseEvent e){
                    if(e.getClickCount()==2){
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTreeProvince.getLastSelectedPathComponent();
                        if(!node.isLeaf())
                            return;                        
                        System.out.println(jTreeProvince.getSelectionPath());
                        TreePath vals = jTreeProvince.getSelectionPath();            
                        System.out.println(vals.getLastPathComponent() + ":" + vals.toString() + (vals.getPathCount()+""));                    
                        String val = (String)vals.getLastPathComponent().toString();
                        String [] _vals = vals.toString().replace("]", "").replace("[", "").split(",");                    
                        String province = "";
                        String city = "";
                        String county = "";
                        if(_vals.length>1)  province = _vals[1].trim();
                        if(_vals.length>2)  city = _vals[2].trim();
                        if(_vals.length>3)  county = _vals[3].trim();
                        System.out.println(province +":" + city + ":" + county);          
                        getSubTable(province,city,county);
                    }        

                } 
            });        
            JScrollPane jScrollPaneRight = new JScrollPane(jTreeProvince);
            panel.add(jScrollPaneRight);
            InlitializeTree();
        }
        
        private void InlitializeTree(){
//            this.db = db;
            //db.SelectWebSearch(rs);  
            provinceDB = new DistrictDB();
            rsProvince.clear();
            provinceDB.SelectProvinceCityCounty(rsProvince);
            
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("行政区划");
    //        addSubNode(mdl.id,root,list);
            DefaultMutableTreeNode nodeProvince;
            DefaultMutableTreeNode nodeCity;
            DefaultMutableTreeNode nodeCounty;
                
            Map<String,DefaultMutableTreeNode> mapNodes = new HashMap<String,DefaultMutableTreeNode>();
            for(int i=0; i<rsProvince.size(); i++){
                if(!mapNodes.containsKey(rsProvince.get(i).province)){
                    nodeProvince = new DefaultMutableTreeNode(rsProvince.get(i).province);
                    root.add(nodeProvince);
                    mapNodes.put(rsProvince.get(i).province, nodeProvince);
                }
            }
            for(int i=0; i<rsProvince.size(); i++){
                nodeProvince = mapNodes.get(rsProvince.get(i).province);
                if(rsProvince.get(i).city.equals(""))
                    rsProvince.get(i).city = rsProvince.get(i).province;
                if(!mapNodes.containsKey(rsProvince.get(i).province + rsProvince.get(i).city)){
                    nodeCity = new DefaultMutableTreeNode(rsProvince.get(i).city);
                    nodeProvince.add(nodeCity);
                    mapNodes.put(rsProvince.get(i).province+rsProvince.get(i).city, nodeCity);
                    if(!mapNodes.containsKey(rsProvince.get(i).province + rsProvince.get(i).city + rsProvince.get(i).county)){
                        nodeCounty = new DefaultMutableTreeNode(rsProvince.get(i).county);
                        nodeCity.add(nodeCounty);
                        mapNodes.put(rsProvince.get(i).province + rsProvince.get(i).city + rsProvince.get(i).county, nodeCounty);
                    }
                }else{
                    nodeCity = mapNodes.get(rsProvince.get(i).province + rsProvince.get(i).city);
                    if(!mapNodes.containsKey(rsProvince.get(i).province + rsProvince.get(i).city + rsProvince.get(i).county)){
                        nodeCounty = new DefaultMutableTreeNode(rsProvince.get(i).county);
                        nodeCity.add(nodeCounty);
                        mapNodes.put(rsProvince.get(i).province + rsProvince.get(i).city + rsProvince.get(i).county, nodeCounty);
                    }                    
                }
            }
            TreeModel treeModel = new DefaultTreeModel(root);
            jTreeProvince.setModel(treeModel);
        }          
    }

    public int rowSel = -1;
    private List<WebSitSearchInfo> rs = new ArrayList<WebSitSearchInfo>();
    class SubTable{
        DefaultTableModel modeTable = null;
         
        JTable table;    
        
        private WebSiteSearchDB db;
        public SubTable(WebSiteSearchDB db){
            this.db = db;
        }
        
        public void InitTable(){
            if(table == null)
                return;
            table.setRowHeight(24);
            String[] header = { "序号","名称","区号","电话","邮编","乡镇","村","路","牌号","类名","方法"}; //定义表头  "省份","地市","区县"
            modeTable = new DefaultTableModel(header,0){
                public boolean isCellEditable(int row, int column){
                    if(column == 0)
                        return false;
                    else
                        return true;
                }
            };
            modeTable.setColumnCount(11);
            table.setModel(modeTable);
            table.getTableHeader().setFont(new Font("Dialog", 0, 14));         

            TableColumn Col0 = table.getColumnModel().getColumn(0);
            Col0.setPreferredWidth(40); Col0.setMaxWidth(40);Col0.setMinWidth(40);  

            TableColumn Col1 = table.getColumnModel().getColumn(1);
            Col1.setPreferredWidth(100);Col1.setMinWidth(100);Col1.setMaxWidth(200);
            
            TableColumn Col2 = table.getColumnModel().getColumn(2);
            Col2.setPreferredWidth(40);Col2.setMinWidth(40);Col2.setMaxWidth(80);
            
            TableColumn Col3 = table.getColumnModel().getColumn(3);
            Col3.setPreferredWidth(40);Col3.setMinWidth(80);Col3.setMaxWidth(80);            
            TableColumn Col4 = table.getColumnModel().getColumn(4);
            Col3.setPreferredWidth(40);Col4.setMinWidth(80);Col4.setMaxWidth(80);
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
                String[] row = {rowNum+"", rs.get(i).building,rs.get(i).phonecode, rs.get(i).phone, rs.get(i).postcode,
                    rs.get(i).township, rs.get(i).village, rs.get(i).road, rs.get(i).roadNo,rs.get(i).object,rs.get(i).method};
                modeTable.addRow(row);
                rowNum++;
            }
        } 
        
        public void getSubInfo(String province,String city,String county){
            rs.clear();
            if(!county.isEmpty() && !city.isEmpty() && !province.isEmpty()){
                if(city.equals(province))
                    city = "";
                db.SelectLocalInfosByProvinceCityCounty(rs, province,city,county);
            }
            if(county.isEmpty() && !city.isEmpty() && !province.isEmpty()){
                if(city.equals(province))
                    city = "";
                db.SelectLocalInfosByProvinceCity(rs, province,city);
            }
            if(county.isEmpty() && city.isEmpty() && !province.isEmpty())
                db.SelectLocalInfosByProvince(rs, province);

        }
        
        public void refresh(){
            if(db == null)
                db = new WebSiteSearchDB();
            if(rs == null)
                rs = new ArrayList<WebSitSearchInfo>();
            else
                rs.clear();
            db.SelectWebSearch(rs);
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
            int id = Integer.parseInt(rs.get(rs.size()-1).id + 1);
            String[] row = {id + "","",""};
            modeTable.addRow(row);
            
            WebSitSearchInfo info = new WebSitSearchInfo();
            info.id = Integer.toString(id);
/*
            info.name = "";
            info.address = "";
            info.stat = 1;
            rs.add(info);
*/            
        }        
        
        public void update(){
           int row = getRsIndex();
           if(row<0)
               return;
           String name = (String)modeTable.getValueAt(row,1);
           String province = (String)modeTable.getValueAt(row,2);
           
           /*
           String _name = rs.get(row).name + "";
           String _province = rs.get(row).province + "";
           
           if(((_province != null && !_province.equals(province)) 
                   || (_name != null && !_name.equals(name)))
                   && rs.get(row).stat == 0 ){
                rs.get(row).stat = 2;
            }
           
           rs.get(row).name = _name;
           rs.get(row).province = province;
           */
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
            /*
            for(int i=0; i<rs.size(); i++){
                LocalInfo _Info = new LocalInfo();                
                _Info.name = rs.get(i).name;
                _Info.province = rs.get(i).province;
                _Info.id = rs.get(i).id;
//                _feature.fields = rs.get(i).fields;
                if(rs.get(i).stat == 1){                    
//                    db.InsertFeature(_feature);
                    rs.get(i).stat = 0;
                    continue;
                }
                if(rs.get(i).stat == 2){
//                    db.UpdateProvince(rs.get(i).id, rs.get(i).code, rs.get(i).province);
                    rs.get(i).stat = 0;
                    continue;
                }
                if(rs.get(i).stat == 3){
//                    db.DeleteLocalInfo(Integer.parseInt(rs.get(i).id));
                    continue;
                }
            }
            */
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
            
//            JToolBar toolBar = new JToolBar();
//            panel2.add(toolBar);

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

            /*
            JButton jButtonRefresh = new JButton("刷新");
            jButtonRefresh.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt) {
                    
                        search();
                }
            });
            */

            panel2.add(jButtonAdd);
            panel2.add(jButtonDel);
            panel2.add(jButtonSave);
            //toolBar.add(jButtonRefresh);

            panel.add(panel2);
            panel.add(jScrollPaneLeft);
            InitTable();
        }        
    }
    
    public static void main(String[] args){
            WebSiteCrawlerManager items = new WebSiteCrawlerManager();
            
            JFrame frame = new JFrame ("JTableDemo");
            frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
            frame.setContentPane (items.panel1);            
            
            frame.pack();
            frame.show();  
        }        
     
}
