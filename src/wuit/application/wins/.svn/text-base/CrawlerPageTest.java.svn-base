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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import wuit.common.crawler.composite.DSComposite;
import wuit.common.crawler.db.KeyValue;
import wuit.crawler.database.ExtractorDB;
import wuit.crawler.database.PageFeatureInfo;
import wuit.crawler.database.PageFeaturesDB;
import wuit.crawler.extract.ExtractItems;
import wuit.crawler.searcher.Crawler;

/**
 *
 * @author lxl
 */
public class CrawlerPageTest {
   private String html = "";
   private String text = "";
   private String ruleTxt = "";

   public JPanel panel1 = new JPanel();
   private JTextArea htmlArea;
   private JTextArea textArea;
   private JTextArea itemArea;
   private JTextArea ruleArea;
   private JTextField urlTxt;
   
   private JTable ruleTable;
   DefaultTableModel mode1 = null;
   
   Crawler crawler ;
   ExtractorDB extractDb;
   List<KeyValue> libRuler;
   ExtractItems extract ;
   
   public CrawlerPageTest(){
       initComponents();
       InitTable();
       extractDb = new ExtractorDB();
       extract = new ExtractItems(extractDb.defaultRule,extractDb.clearHtml);
   }
        private void initComponents(){
            htmlArea = new JTextArea();

            JScrollPane paneRight = new JScrollPane (htmlArea);      
            JPanel panelHtml = new JPanel();
            panelHtml.setLayout(new BoxLayout(panelHtml,BoxLayout.Y_AXIS));
            JLabel lbHtml = new JLabel("HTML文档");
            panelHtml.add(lbHtml);
            panelHtml.add(paneRight);
            
            panel1.setPreferredSize (new Dimension (600,400));
            panel1.setBackground (Color.black);
            panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
  
            textArea = new JTextArea();
            //textArea.setFont(new Font("Dialog", 1, 14));
            JScrollPane jScrollPaneLeft = new JScrollPane(textArea);
            JPanel panelText = new JPanel();
            panelText.setLayout(new BoxLayout(panelText,BoxLayout.Y_AXIS));
            JLabel lbText = new JLabel("Text 文档");
            panelText.add(lbText);
            panelText.add(jScrollPaneLeft);
            

            JSplitPane jSplitPane2 = new JSplitPane();           
            jSplitPane2.setOneTouchExpandable(true);//让分割线显示出箭头
            jSplitPane2.setContinuousLayout(true);//操作箭头，重绘图形
            jSplitPane2.setPreferredSize(new Dimension (800,400));
            jSplitPane2.setOrientation(JSplitPane.HORIZONTAL_SPLIT);//设置分割线方向
            jSplitPane2.setLeftComponent(panelHtml);
            jSplitPane2.setRightComponent(panelText);
            jSplitPane2.setDividerSize(2);
            jSplitPane2.setDividerLocation(200);//设置分割线位于中央             
            
            JPanel panel2 = new JPanel (new GridLayout(1,1));
            panel2.setPreferredSize(new Dimension (200,30));
            panel2.setMaximumSize(new Dimension (200,30));
            panel2.setMinimumSize(new Dimension (200,30)); 
            panel2.setSize(200, 30);
            
            
            JToolBar toolBar = new JToolBar();
            panel2.add(toolBar);
            
            urlTxt = new JTextField("url");
            JButton jButtonAdd = new JButton("下载");
            //jButtonAdd.setSize(200, 30);
            jButtonAdd.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt) {
                    String txtUrl = urlTxt.getText();
                    html = Crawler.doGetHttp(txtUrl);
                    htmlArea.setText(html);
                }
            });
            
            JButton jButtonDel = new JButton("清理");
            jButtonDel.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt) {
                    text = Crawler.clearHtml(html,extractDb.clearHtml);
                    textArea.setText(text);
                }
            });            
            
            JButton jButtonSave = new JButton("提取");
            jButtonSave.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt) {
                    ruleTxt = ruleArea.getText();
                    ruleTxt = ruleTxt.replaceAll("\\{2,}", "\\");
                    libRuler = new ArrayList<KeyValue>();
                    getItemRules(libRuler,ruleTxt);
                    List<DSComposite> items = new ArrayList<DSComposite>();
                    extract.extractItems(text, libRuler, items);
                    
                    String val = "";
                    for(int i=0;i<items.size();i++){
                        val = val + ";" + items.get(i).label + ";" + items.get(i).name  + ";" + items.get(i).local.address;
                        val = val + ";" + items.get(i).phone + ";" + items.get(i).postcode;
                        val = val + "\n\r";
                    }
                    itemArea.setText(val);
                }
            });            

            toolBar.add(jButtonAdd);
            toolBar.add(jButtonDel);
            toolBar.add(jButtonSave);
            
            
            
            toolBar.add(urlTxt);
            
            itemArea = new JTextArea();
            //itemArea.setFont(new Font("Dialog", 1, 14));
            //itemArea.setPreferredSize(new Dimension (800,400));
            JScrollPane jScrollPaneItem = new JScrollPane(itemArea);

            ruleArea = new JTextArea();
            //ruleArea.setFont(new Font("Dialog", 1, 14));
            //itemArea.setPreferredSize(new Dimension (800,400));
            
            ruleTable = new JTable();
            
            ruleTable = new JTable ();
            ruleTable.setPreferredScrollableViewportSize(new Dimension(600, 100));//设置表格的大小
            ruleTable.setRowHeight (30);//设置每行的高度为20
//            friends.setRowHeight (0, 20);//设置第1行的高度为15
            ruleTable.setRowMargin (5);//设置相邻两行单元格的距离
            ruleTable.setRowSelectionAllowed (true);//设置可否被选择.默认为false
            ruleTable.setSelectionBackground (Color.white);//设置所选择行的背景色
            ruleTable.setSelectionForeground (Color.red);//设置所选择行的前景色
            ruleTable.setGridColor (Color.black);//设置网格线的颜色
            ruleTable.selectAll ();//选择所有行
//            table.setRowSelectionInterval (0,2);//设置初始的选择行,这里是1到3行都处于选择状态
            ruleTable.clearSelection ();//取消选择
            ruleTable.setDragEnabled (false);//不懂这个
            ruleTable.setShowGrid (false);//是否显示网格线
            ruleTable.setShowHorizontalLines (true);//是否显示水平的网格线
            ruleTable.setShowVerticalLines (true);//是否显示垂直的网格线
//            table.setValueAt ("tt", 0, 0);//设置某个单元格的值,这个值是一个对象
            ruleTable.doLayout ();
            ruleTable.setBackground (Color.lightGray);            
            
            JScrollPane jScrollPaneRule = new JScrollPane(ruleArea);            
            
            panel1.add(panel2);
            
            JPanel panelRule = new JPanel();
            panelRule.setLayout(new BoxLayout(panelRule,BoxLayout.Y_AXIS));
//            panel3.add(jScrollPaneItem);
            JLabel lbRule = new JLabel("提取规则");
            panelRule.add(lbRule);
            panelRule.add(jScrollPaneRule);

            JPanel panelItem = new JPanel();
            panelItem.setLayout(new BoxLayout(panelItem,BoxLayout.Y_AXIS));
//            panel3.add(jScrollPaneItem);
            JLabel lbItem = new JLabel("提取结果");
            panelItem.add(lbItem);
            panelItem.add(jScrollPaneItem);
            
            
            JSplitPane jSplitPane22 = new JSplitPane();         
            jSplitPane22.setOneTouchExpandable(true);//让分割线显示出箭头
            jSplitPane22.setContinuousLayout(true);//操作箭头，重绘图形
            jSplitPane22.setPreferredSize(new Dimension (800,400));
            jSplitPane22.setOrientation(JSplitPane.HORIZONTAL_SPLIT);//设置分割线方向
            jSplitPane22.setLeftComponent(panelRule);
            jSplitPane22.setRightComponent(panelItem);
            jSplitPane22.setDividerSize(2);
            jSplitPane22.setDividerLocation(200);//设置分割线位于中央 
            
            
            JSplitPane jSplitPane1 = new JSplitPane();           
            jSplitPane1.setOneTouchExpandable(true);//让分割线显示出箭头
            jSplitPane1.setContinuousLayout(true);//操作箭头，重绘图形
            jSplitPane1.setPreferredSize(new Dimension (800,400));
            jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);//设置分割线方向
            jSplitPane1.setLeftComponent(jSplitPane2);
            jSplitPane1.setRightComponent(jSplitPane22);
            jSplitPane1.setDividerSize(2);
            jSplitPane1.setDividerLocation(200);//设置分割线位于中央           
            
            //panel3.add(jSplitPane1);
            
            
            //panel1.add (jSplitPane1);
            //JSplitPane jScrollPaneItem = new JSplitPane();
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
        
        public void InitTable(){
            if(ruleTable == null)
                return;
            ruleTable.setRowHeight(24);
            String[] header = { "序号","名称","匹配模式"}; //定义表头
            mode1 = new DefaultTableModel(header,0){
                public boolean isCellEditable(int row, int column){
                    if(column == 0)
                        return false;
                    else
                        return true;
                }
            };
            mode1.setColumnCount(3);
            ruleTable.setModel(mode1);
            ruleTable.getTableHeader().setFont(new Font("Dialog", 0, 14));         
            
            TableColumn Col0 = ruleTable.getColumnModel().getColumn(0);
            Col0.setPreferredWidth(40);            
            Col0.setMaxWidth(40);  
            Col0.setMinWidth(40);  

            TableColumn Col1 = ruleTable.getColumnModel().getColumn(1);
            Col1.setPreferredWidth(100);            
            Col0.setMinWidth(100);
            Col1.setMaxWidth(200);
        }        
        
    public void getItemRules(List<KeyValue> libRuler,String txtRule){        
            String xml = txtRule;
            Document doc = null;
            try{
                if(xml==null || xml.equals(""))
                    return;
                StringReader read = new StringReader(xml);
                InputSource source = new InputSource(read);
                //SAXBuilder sb = new SAXBuilder();
                
                SAXReader saxReader = new SAXReader();
                doc = saxReader.read(source);                
                List list = doc.selectNodes("/feature/item");
                for(int j=0; j<list.size();j++){
                    Element ele = (Element)list.get(j);
                    KeyValue vals = new KeyValue();
                    vals.key = ele.attributeValue("name");
                    vals.value = ele.getTextTrim();
                    libRuler.add(vals);
                }
            }catch(DocumentException e) {
                System.out.println(e.getMessage());
            } 
    }        
        
        
        public static void main(String[] args){
            CrawlerPageTest items = new CrawlerPageTest();
            
            JFrame frame = new JFrame ("JTableDemo");
            frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
            frame.setContentPane (items.panel1);            
            
            frame.pack();
            frame.show();  
        }       
}
