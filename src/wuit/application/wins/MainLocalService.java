/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wuit.application.wins;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author lxl
 */
public class MainLocalService {
    JPanel panelMain = new JPanel ();
    JTabbedPane jTabPaneContent;
    JTree jTreeMnue;
    
    public MainLocalService(){
        initComponents();
    }
    
    
    private void initComponents(){
        panelMain.setLayout(new BoxLayout(panelMain,BoxLayout.Y_AXIS));
        
        JPanel panelTitle = new JPanel();
        JLabel lbTitle = new JLabel("互联网位置服务信息");
        lbTitle.setFont(new Font("黑体", 1, 28)); 
        lbTitle.setForeground(Color.blue);
        
        panelTitle.add(lbTitle);
        panelMain.add(panelTitle);
        
        JPanel panelRight = new JPanel();
        panelRight.setPreferredSize(new Dimension (400,400));
        
        JPanel panelLeft = new JPanel();
        panelLeft.setPreferredSize(new Dimension (400,400));
        
        JPanel panelContent = new JPanel();
        
        jTreeMnue = new JTree();
        jTreeMnue.setFont(new Font("Dialog", 0, 14)); 
        
        jTreeMnue.addMouseListener(new MouseAdapter() { 
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2){
                    System.out.println(jTreeMnue.getSelectionPath());
                    TreePath vals = jTreeMnue.getSelectionPath();            
                    System.out.println(vals.getLastPathComponent());
                    String val = (String)vals.getLastPathComponent().toString();
                    doWorks(val);
                }        
                
            } 
        });        
        
        JScrollPane jScrollPaneRight = new JScrollPane(jTreeMnue);
        jTabPaneContent = new JTabbedPane();
        JScrollPane jScrollPaneLeft = new JScrollPane(jTabPaneContent);
        
        JSplitPane jSplitPane1 = new JSplitPane();

        jSplitPane1.setOneTouchExpandable(true);//让分割线显示出箭头
        jSplitPane1.setContinuousLayout(true);//操作箭头，重绘图形
        jSplitPane1.setPreferredSize(new Dimension (800,400));
        jSplitPane1.setOrientation(JSplitPane.HORIZONTAL_SPLIT);//设置分割线方向
        jSplitPane1.setLeftComponent(jScrollPaneRight);
        jSplitPane1.setRightComponent(jScrollPaneLeft);
        jSplitPane1.setDividerSize(3);
        jSplitPane1.setDividerLocation(200);//设置分割线位于中央   
        
        panelContent.add(jSplitPane1);        
        panelMain.add(panelContent);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panelMain);
        panelMain.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1))
        ); 
        InlitializeTree();
    }
    
    private void InlitializeTree(){
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("互联网位置服务信息");
//        addSubNode(mdl.id,root,list);
        DefaultMutableTreeNode nodeSearchMode = new DefaultMutableTreeNode("搜索信息");
        nodeSearchMode.add(new DefaultMutableTreeNode("垂直搜索"));
        nodeSearchMode.add(new DefaultMutableTreeNode("关键字搜索"));
        nodeSearchMode.add(new DefaultMutableTreeNode("直接搜索"));
        
        DefaultMutableTreeNode nodeSearchConfig = new DefaultMutableTreeNode("搜索维护");
        nodeSearchConfig.add(new DefaultMutableTreeNode("直接搜索维护"));
        
        DefaultMutableTreeNode nodeSearchAddress = new DefaultMutableTreeNode("区划邮编维护");
        nodeSearchAddress.add(new DefaultMutableTreeNode("行政区划"));
        nodeSearchAddress.add(new DefaultMutableTreeNode("区号邮编"));
        
        DefaultMutableTreeNode nodeSearchLib = new DefaultMutableTreeNode("网页特征维护");
        nodeSearchLib.add(new DefaultMutableTreeNode("网页清理"));
        nodeSearchLib.add(new DefaultMutableTreeNode("默认提取"));
        nodeSearchLib.add(new DefaultMutableTreeNode("网页特征库"));
        nodeSearchLib.add(new DefaultMutableTreeNode("网页特征测试"));
        nodeSearchLib.add(new DefaultMutableTreeNode("网页过滤"));
        
        
        DefaultMutableTreeNode nodeLocalLib = new DefaultMutableTreeNode("位置服务信息");
        nodeLocalLib.add(new DefaultMutableTreeNode("省市查询"));
        nodeLocalLib.add(new DefaultMutableTreeNode("服务分类"));
        

        DefaultMutableTreeNode nodeLocal = new DefaultMutableTreeNode("位置语义");
        nodeLocal.add(new DefaultMutableTreeNode("绝对位置关键词"));
        nodeLocal.add(new DefaultMutableTreeNode("参照位置关键词"));
        
        root.add(nodeSearchMode);
        root.add(nodeSearchConfig);
        root.add(nodeLocalLib);
        root.add(nodeSearchLib);
        root.add(nodeSearchAddress);
        root.add(nodeLocal);
        
        TreeModel treeModel = new DefaultTreeModel(root);
        jTreeMnue.setModel(treeModel);
    }    
    
    private void doWorks(String val){
        if(val.equals("垂直搜索")){
            PageCrawler pageCrawler0 = new PageCrawler(0);
            jTabPaneContent.addTab("垂直搜索", pageCrawler0.panel1);
            //pageCrawler0.panel1.setBackground(Color.white);
            jTabPaneContent.setSelectedIndex(jTabPaneContent.getTabCount()-1);
            jTabPaneContent.setTabComponentAt(jTabPaneContent.getSelectedIndex(), new JTabbedPaneTitle(jTabPaneContent,"垂直搜索",pageCrawler0));
        }
        if(val.equals("关键字搜索")){
            PageCrawlerKeywords pageCrawler0 = new PageCrawlerKeywords();
            jTabPaneContent.addTab("关键词搜索", pageCrawler0.panel1);
            //pageCrawler0.panel1.setBackground(Color.white);
            jTabPaneContent.setSelectedIndex(jTabPaneContent.getTabCount()-1);
            jTabPaneContent.setTabComponentAt(jTabPaneContent.getSelectedIndex(), new JTabbedPaneTitle(jTabPaneContent,"关键词搜索",pageCrawler0));
        }
        if(val.equals("直接搜索")){
            WebSitCrawler items = new WebSitCrawler();
            jTabPaneContent.addTab("直接搜索", items.panel1);
            jTabPaneContent.setSelectedIndex(jTabPaneContent.getTabCount()-1);
            jTabPaneContent.setTabComponentAt(jTabPaneContent.getSelectedIndex(), new JTabbedPaneTitle(jTabPaneContent,"直接搜索",items));
        }
        
        if(val.equals("直接搜索维护")){
            WebSiteCrawlerManager items = new WebSiteCrawlerManager();
            jTabPaneContent.addTab("直接搜索维护", items.panel1);
            jTabPaneContent.setSelectedIndex(jTabPaneContent.getTabCount()-1);
            jTabPaneContent.setTabComponentAt(jTabPaneContent.getSelectedIndex(), new JTabbedPaneTitle(jTabPaneContent,"直接搜索维护",items));            
        }     
        if(val.equals("省市查询")){
            LocalInfoManage items = new LocalInfoManage();
            jTabPaneContent.addTab("省市查询", items.panel1);
            jTabPaneContent.setSelectedIndex(jTabPaneContent.getTabCount()-1);
            jTabPaneContent.setTabComponentAt(jTabPaneContent.getSelectedIndex(), new JTabbedPaneTitle(jTabPaneContent,"省市查询",items));                         
        }                     
        if(val.equals("服务分类")){
            LocalInfoLabelManage items = new LocalInfoLabelManage();        
            jTabPaneContent.addTab("服务分类", items.panel1);
            jTabPaneContent.setSelectedIndex(jTabPaneContent.getTabCount()-1);
            jTabPaneContent.setTabComponentAt(jTabPaneContent.getSelectedIndex(), new JTabbedPaneTitle(jTabPaneContent,"服务分类",items));                         
        }                    
        if(val.equals("网页特征库")){
            PageFeature feature = new PageFeature();
            jTabPaneContent.addTab("网页特征", feature.panel1);
            jTabPaneContent.setSelectedIndex(jTabPaneContent.getTabCount()-1);
            jTabPaneContent.setTabComponentAt(jTabPaneContent.getSelectedIndex(), new JTabbedPaneTitle(jTabPaneContent,"网页特征",feature));        
        }
        if(val.equals("网页特征测试")){
            CrawlerPageTest items = new CrawlerPageTest();        
            jTabPaneContent.addTab("网页特征测试", items.panel1);
            jTabPaneContent.setSelectedIndex(jTabPaneContent.getTabCount()-1);
            jTabPaneContent.setTabComponentAt(jTabPaneContent.getSelectedIndex(), new JTabbedPaneTitle(jTabPaneContent,"网页特征测试",items));  
        }                    
        if(val.equals("网页清理")){
            PageHtmlClear items = new PageHtmlClear();        
            jTabPaneContent.addTab("网页清理", items.panel1);
            jTabPaneContent.setSelectedIndex(jTabPaneContent.getTabCount()-1);
            jTabPaneContent.setTabComponentAt(jTabPaneContent.getSelectedIndex(), new JTabbedPaneTitle(jTabPaneContent,"网页清理",items)); 
        }            
        if(val.equals("默认提取")){
            PageItems items = new PageItems();
            jTabPaneContent.addTab("默认提取", items.panel1);
            jTabPaneContent.setSelectedIndex(jTabPaneContent.getTabCount()-1);        
            jTabPaneContent.setTabComponentAt(jTabPaneContent.getSelectedIndex(), new JTabbedPaneTitle(jTabPaneContent,"默认提取",items));
        }
        if(val.equals("网页过滤")){
            PageFilters items = new PageFilters();        
            jTabPaneContent.addTab("网页过滤", items.panel1);
            jTabPaneContent.setSelectedIndex(jTabPaneContent.getTabCount()-1);
            jTabPaneContent.setTabComponentAt(jTabPaneContent.getSelectedIndex(), new JTabbedPaneTitle(jTabPaneContent,"网页过滤",items));
        }
        if(val.equals("绝对位置关键词")){
            AddressKeys0 items = new AddressKeys0();        
            jTabPaneContent.addTab("绝对位置关键词", items.panel1);
            jTabPaneContent.setSelectedIndex(jTabPaneContent.getTabCount()-1);
            jTabPaneContent.setTabComponentAt(jTabPaneContent.getSelectedIndex(), new JTabbedPaneTitle(jTabPaneContent,"位置关键词",items));  
        }                     
        if(val.equals("行政区划")){
            DistrictManage items = new DistrictManage();        
            jTabPaneContent.addTab("行政区划", items.panel1);
            jTabPaneContent.setSelectedIndex(jTabPaneContent.getTabCount()-1);
            jTabPaneContent.setTabComponentAt(jTabPaneContent.getSelectedIndex(), new JTabbedPaneTitle(jTabPaneContent,"行政区划",items));                         
        }
        if(val.equals("区号邮编")){
            PhonePostCodeManage items = new PhonePostCodeManage();        
            jTabPaneContent.addTab("区号邮编", items.panel1);
            jTabPaneContent.setSelectedIndex(jTabPaneContent.getTabCount()-1);
            jTabPaneContent.setTabComponentAt(jTabPaneContent.getSelectedIndex(), new JTabbedPaneTitle(jTabPaneContent,"区号邮编",items));                         
        }        
    }
    
    public static void main(String[] args){
        MainLocalService items = new MainLocalService();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame ("互联网位置服务信息");
        frame.setPreferredSize(screenSize);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(items.panelMain);            

        frame.pack();
        frame.show();  
    }    
}
