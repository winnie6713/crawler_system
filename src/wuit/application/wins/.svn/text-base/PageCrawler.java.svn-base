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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import wuit.common.crawler.composite.DSComposite;
import wuit.crawler.DSCrawlerParam;
import wuit.crawler.DSCrawlerUrl;
import wuit.crawler.database.CrawlerStatInfo;
import wuit.crawler.database.LocalInfoDB;
import wuit.crawler.main.MainServer;
import wuit.crawler.searcher.Crawler;

/**
 *
 * @author lxl
 */
public class PageCrawler {
    private MainServer server ;
    private String startTime="";
    private String endTime="";
    
    private DispState dispStat = new DispState();
    private DSCrawlerParam paramCrawler = new DSCrawlerParam();
    private int extractors = 4;
    
    private int dispRs = -1;
    private int dispParse = -1;
    private int dispFeature = -1;
    private int dispConflict = -1;

    
    JTable jTableCrawler1;
    DefaultTableModel modeCrawler1;
    
    JLabel jLbState;
    
    JTextField jTFCrawlerDeepth;
    JTextField jTFCrawlerTasks;            
    JTextField jTFKeyUrl;
    JTextField jTextExtractTasks;    
    
    JTextField jTxtUrlSum;
    JTextField jTxtUrls;
                
    JTextField jTxtPageSum;
    JTextField jTxtPages;
    JTextField jTxtPagesYes;
    JTextField jTxtPagesNo;
                
    JTextField jTxtItemSum;
    JTextField jTextdirect; 
    JTextField jTxtNull; 
    JTextField jTextAmb;
    JTextField jTextItemInv;                

    JTextField jTextLocalFull;
    JTextField jTextLocalPart;
    JTextField jTextLocalNull;
    JTextField jTextLocalConflict;
    JTextField jTextLocalSum;     
    
    JProgressBar jProgressBar1;
    JProgressBar jProgressBar2;
    JProgressBar jProgressBar3;
    JProgressBar jProgressBar4;
    
    JPanel panel1 = new JPanel ();
    
    public PageCrawler(int mode){
        initComponents();
        InitCrawlerConfig();
        paramCrawler.mode = mode;
        setCrawlerParams();
    }
    
    private void initComponents(){
        JButton jBTNStart = new JButton("开始");
        jBTNStart.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                getCrawlerParams();
                MainCrawler crawler = new MainCrawler(paramCrawler,extractors);        
                new Thread(crawler).start();
                server.running = 1;
                dispStat.state = 1;                    
            }
        });            
        JButton jBTNStop = new JButton("停止");
        jBTNStop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                dispStat.stopDisp();
                server.stopCrawling();                  
            }
        }); 
        
        JButton jBTNDispInfo = new JButton("详细信息");
        jBTNDispInfo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                //server.refreshExtractRuler();
                dispStat.dispTable(dispFeature,dispParse,dispRs,dispConflict);
                System.out.println(dispFeature +":" + dispParse + ":" + dispRs);
            }
        });          
        
        JButton jBTNRefreshLib = new JButton("更新规则");
        jBTNRefreshLib.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                server.refreshExtractRuler();                  
            }
        });         
        
        JPanel jpanelOperator = new JPanel();
        jpanelOperator.setLayout(new BoxLayout(jpanelOperator,BoxLayout.X_AXIS));        
        
        jpanelOperator.add(jBTNStart);
        jpanelOperator.add(jBTNStop);
        jpanelOperator.add(jBTNDispInfo);
        jpanelOperator.add(jBTNRefreshLib);
        panel1.add(jpanelOperator);
        
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        
        JPanel panelText = new JPanel();
        panelText.setLayout(new GridLayout(1,2));
        
        JPanel panelText1 = new JPanel();
        panelText1.setLayout(new GridLayout(5,1));
        JPanel panelText2 = new JPanel();
        panelText2.setLayout(new GridLayout(5,1));
        
        panelText.add(panelText1);
        panelText.add(panelText2);
        
        Box panelConfig = new Box(BoxLayout.X_AXIS);
        JLabel labelConfig1 = new JLabel("搜索深度：");
        panelConfig.add(labelConfig1);        
        jTFCrawlerDeepth = new JTextField();
        panelConfig.add(jTFCrawlerDeepth);
        
        JLabel labelConfig2 = new JLabel("搜索线程：");
        panelConfig.add(labelConfig2);
        jTFCrawlerTasks = new JTextField(); 
        panelConfig.add(jTFCrawlerTasks);

        JLabel labelConfig4 = new JLabel("解析线程：");
        panelConfig.add(labelConfig4);        
        jTextExtractTasks = new JTextField();
        panelConfig.add(jTextExtractTasks);
        
        panelText1.add(panelConfig);

        Box panelTxtUrl = new Box(BoxLayout.X_AXIS);
        JLabel labelUrl1 = new JLabel("链接总数：");
        panelTxtUrl.add(labelUrl1);        
        jTxtUrlSum = new JTextField();
        panelTxtUrl.add(jTxtUrlSum);
        
        JLabel labelUrl2 = new JLabel("链接完成：");
        panelTxtUrl.add(labelUrl2);        
        jTxtUrls = new JTextField();
        panelTxtUrl.add(jTxtUrls);        
        
        panelText1.add(panelTxtUrl);
        
        Box panelTxtPage = new Box(BoxLayout.X_AXIS);
        JLabel labelPage1 = new JLabel("网页总数：");
        panelTxtPage.add(labelPage1);        
        jTxtPageSum = new JTextField();
        panelTxtPage.add(jTxtPageSum);
        
        JLabel labelPage2 = new JLabel("完成：");
        panelTxtPage.add(labelPage2);        
        jTxtPages = new JTextField();
        panelTxtPage.add(jTxtPages);        
        
        JLabel labelPage3 = new JLabel("匹配：");
        panelTxtPage.add(labelPage3);        
        jTxtPagesYes = new JTextField();
        panelTxtPage.add(jTxtPagesYes);        
        

        JLabel labelPage4 = new JLabel("不匹配");
        panelTxtPage.add(labelPage4);        
        jTxtPagesNo = new JTextField();
        panelTxtPage.add(jTxtPagesNo); 
        
        
        JLabel labelPage5 = new JLabel("匹配率");
//        panelTxtPage.add(labelPage5);        
        JTextField jTxtPagesPatternBL = new JTextField();
//        panelTxtPage.add(jTxtPagesPatternBL);         
        
        panelText1.add(panelTxtPage);        
        
        Box panelTxtItems = new Box(BoxLayout.X_AXIS);
        JLabel labelitem1 = new JLabel("解析总数：");
        panelTxtItems.add(labelitem1);        
        jTxtItemSum = new JTextField();
        panelTxtItems.add(jTxtItemSum);
        
        JLabel labelitem2 = new JLabel("直接：");
        panelTxtItems.add(labelitem2);        
        jTextdirect = new JTextField();
        panelTxtItems.add(jTextdirect);        

        JLabel labelitem3 = new JLabel("间接：");
//        panelTxtItems.add(labelitem3);        
        jTxtNull = new JTextField();
//        panelTxtItems.add(jTxtNull); 
        
        JLabel labelitem4 = new JLabel("歧义：");
        panelTxtItems.add(labelitem4);        
        jTextAmb = new JTextField();
        panelTxtItems.add(jTextAmb);         

        JLabel labelitem5 = new JLabel("无效：");
        panelTxtItems.add(labelitem5);        
        jTextItemInv = new JTextField();
        panelTxtItems.add(jTextItemInv);        
        
        panelText1.add(panelTxtItems);
        
        
        Box panelTxtRs = new Box(BoxLayout.X_AXIS);
        JLabel labelrs1 = new JLabel("解析结果：");
        panelTxtRs.add(labelrs1);        
        jTextLocalSum = new JTextField();
        panelTxtRs.add(jTextLocalSum);
        
        JLabel labelrs2 = new JLabel("基本：");
//        panelTxtRs.add(labelrs2);        
        jTextLocalFull = new JTextField();
//        panelTxtRs.add(jTextLocalFull); 

        JLabel labelrs3 = new JLabel("有效：");
        panelTxtRs.add(labelrs3);        
        jTextLocalPart = new JTextField();
        panelTxtRs.add(jTextLocalPart); 
        
        JLabel labelrs4 = new JLabel("无效：");
        panelTxtRs.add(labelrs4);        
        jTextLocalNull = new JTextField();
        panelTxtRs.add(jTextLocalNull);         

        JLabel labelrs5 = new JLabel("冲突：");
        panelTxtRs.add(labelrs5);        
        jTextLocalConflict = new JTextField();
        panelTxtRs.add(jTextLocalConflict);        
        
        panelText1.add(panelTxtRs);        
        
        Box panelUrl = new Box(BoxLayout.X_AXIS);
        JLabel labelConfig3 = new JLabel(" 网站链接：");
        panelUrl.add(labelConfig3);
        jTFKeyUrl = new JTextField();
        panelUrl.add(jTFKeyUrl);
        panelText2.add(panelUrl);
        
        Box panelProgress1 = new Box(BoxLayout.X_AXIS);
        JLabel labelProgress1 = new JLabel(" 搜索链接：");
        panelProgress1.add(labelProgress1);
        jProgressBar1 = new JProgressBar();
        panelProgress1.add(jProgressBar1);
        panelText2.add(panelProgress1);

        Box panelProgress2 = new Box(BoxLayout.X_AXIS);
        JLabel labelProgress2 = new JLabel(" 网页处理：");
        panelProgress2.add(labelProgress2);
        jProgressBar2 = new JProgressBar();
        panelProgress2.add(jProgressBar2);
        panelText2.add(panelProgress2);
        
        Box panelProgress3 = new Box(BoxLayout.X_AXIS);
        JLabel labelProgress3 = new JLabel(" 位置解析：");
        panelProgress3.add(labelProgress3);
        jProgressBar3 = new JProgressBar();
        panelProgress3.add(jProgressBar3);
        panelText2.add(panelProgress3);
        
        Box panelProgress4 = new Box(BoxLayout.X_AXIS);
        JLabel labelProgress4 = new JLabel(" 解析结果：");
        panelProgress4.add(labelProgress4);
        jProgressBar4 = new JProgressBar();
        panelProgress4.add(jProgressBar4);
        panelText2.add(panelProgress4);        
        
        panelText.setBorder(BorderFactory.createTitledBorder(" 互联网位置服务信息——运行状态 "));  
        panel1.add(panelText);
        
        JPanel jpanelDispTable = new JPanel();
        jpanelDispTable.setLayout(new BoxLayout(jpanelDispTable,BoxLayout.X_AXIS));
        jpanelDispTable.setBorder(BorderFactory.createTitledBorder("位置服务信息——详细查看选项"));  

        JPanel panelFeature = new JPanel(new GridLayout(1,3));
        panelFeature.setBorder(BorderFactory.createTitledBorder("网页特征匹配"));         
        ButtonGroup jbgFeature = new ButtonGroup();
        
        JRadioButton jbFeature0 = new JRadioButton("完成总数");
        jbFeature0.setSelected(true);
        jbFeature0.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispFeature = -1;
                }
            }
        });          
        JRadioButton jbFeature1 = new JRadioButton("匹配");
        jbFeature1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispFeature = 1;
                }
            }
        });          
        JRadioButton jbFeature2 = new JRadioButton("不匹配");
        jbFeature2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispFeature = 0;
                }
            }
        });          
        
        jbgFeature.add(jbFeature0);
        jbgFeature.add(jbFeature1);
        jbgFeature.add(jbFeature2);

        panelFeature.add(jbFeature0);
        panelFeature.add(jbFeature1);
        panelFeature.add(jbFeature2);        
        jpanelDispTable.add(panelFeature);

        
        JPanel panelParse = new JPanel(new GridLayout(1,4));
        panelParse.setBorder(BorderFactory.createTitledBorder("位置服务信息——解析方式"));         
        ButtonGroup jbgParse = new ButtonGroup();
        
        JRadioButton jbParse00 = new JRadioButton("全体");
        jbParse00.setSelected(true);
        jbParse00.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispParse = - 1;
                }
            }
        });
        
        JRadioButton jbParse0 = new JRadioButton("直接");
        jbParse0.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispParse = 1;
                }
            }
        });
        
        JRadioButton jbParse1 = new JRadioButton("间接");
        jbParse1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispParse = 2;
                }
            }
        });        
        
        JRadioButton jbParse2 = new JRadioButton("歧义");
        jbParse2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispParse = 3;
                }
            }
        });        
        
        JRadioButton jbParse3 = new JRadioButton("无效");
        jbParse3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispParse = 4;
                }
            }
        });    
        
        jbgParse.add(jbParse00);
        jbgParse.add(jbParse0);
//        jbgParse.add(jbParse1);
        jbgParse.add(jbParse2);
//        jbgParse.add(jbParse3);
        
        panelParse.add(jbParse00);
        panelParse.add(jbParse0);
//        panelParse.add(jbParse1);
        panelParse.add(jbParse2);
//        panelParse.add(jbParse3);
        jpanelDispTable.add(panelParse);        


        JRadioButton jbRS00 = new JRadioButton("全体");
        jbRS00.setSelected(true);
        jbRS00.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispRs = -1;
                }
            }
        }); 
        JRadioButton jbRS0 = new JRadioButton("基本");
        jbRS0.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispRs = 1;
                }
            }
        }); 
        JRadioButton jbRS1 = new JRadioButton("有效");
        jbRS1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispRs = 2;
                }
            }
        }); 
        JRadioButton jbRS2 = new JRadioButton("无效");
        jbRS2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispRs = 3;
                }
            }
        }); 
        
        JPanel panelRS = new JPanel(new GridLayout(1,4));
        panelRS.setBorder(BorderFactory.createTitledBorder("位置服务信息——分类"));         
        ButtonGroup jbgRS = new ButtonGroup();        
        jbgRS.add(jbRS00);
        jbgRS.add(jbRS0);
        jbgRS.add(jbRS1);
        jbgRS.add(jbRS2);
        
        panelRS.add(jbRS00);
        panelRS.add(jbRS0);
        panelRS.add(jbRS1);
        panelRS.add(jbRS2);          

        jpanelDispTable.add(panelRS);         
        
        JRadioButton jbRS3 = new JRadioButton("冲突");
        jbRS3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispConflict = 1;
                }
            }
        });        
        
        JRadioButton jbRS4 = new JRadioButton("非冲突");
        jbRS4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispConflict = 0;
                }
            }
        });

        JRadioButton jbRS5 = new JRadioButton("全体");
        jbRS5.setSelected(true);
        jbRS5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange()==evt.SELECTED){
                    dispConflict = -1;
                }
            }
        });        
        
        
        JPanel panelRS1 = new JPanel(new GridLayout(1,4));
        panelRS1.setBorder(BorderFactory.createTitledBorder("位置服务信息——冲突"));         
        ButtonGroup jbgRS1 = new ButtonGroup();        
        jbgRS1.add(jbRS5);
        jbgRS1.add(jbRS3);
        jbgRS1.add(jbRS4);
        
        panelRS1.add(jbRS5);
        panelRS1.add(jbRS3);
        panelRS1.add(jbRS4);

        jpanelDispTable.add(panelRS1);
        
        panel1.add(jpanelDispTable);
        
        jTableCrawler1 = new JTable();
        //jTableCrawler1.setPreferredScrollableViewportSize(new Dimension(800, 100));//设置表格的大小
        jTableCrawler1.setRowHeight (30);//设置每行的高度为20
//            friends.setRowHeight (0, 20);//设置第1行的高度为15
        jTableCrawler1.setRowMargin (5);//设置相邻两行单元格的距离
        jTableCrawler1.setRowSelectionAllowed (true);//设置可否被选择.默认为false
        jTableCrawler1.setSelectionBackground (Color.white);//设置所选择行的背景色
        jTableCrawler1.setSelectionForeground (Color.red);//设置所选择行的前景色
        jTableCrawler1.setGridColor (Color.black);//设置网格线的颜色
        jTableCrawler1.selectAll ();//选择所有行
//            table.setRowSelectionInterval (0,2);//设置初始的选择行,这里是1到3行都处于选择状态
        jTableCrawler1.clearSelection ();//取消选择
        jTableCrawler1.setDragEnabled (false);//不懂这个
        jTableCrawler1.setShowGrid (false);//是否显示网格线
        jTableCrawler1.setShowHorizontalLines (true);//是否显示水平的网格线
        jTableCrawler1.setShowVerticalLines (true);//是否显示垂直的网格线
//            table.setValueAt ("tt", 0, 0);//设置某个单元格的值,这个值是一个对象
        jTableCrawler1.doLayout ();
        jTableCrawler1.setBackground (Color.lightGray); 
        
        JScrollPane jScrollPaneTable = new JScrollPane(jTableCrawler1); 
        panel1.add(jScrollPaneTable);
        
        InitTable();
    }
    
    private void InitCrawlerConfig(){
        paramCrawler.deepth = 2;
        paramCrawler.keyUrl = "http://www.aibang.com/shanghai/";
        paramCrawler.mapFilterUrl = null;
        paramCrawler.mode = 0;
        paramCrawler.tasks = 3;
        extractors = paramCrawler.tasks * 3;

    }
    
    private void getCrawlerParams(){
        paramCrawler.deepth = Integer.parseInt(jTFCrawlerDeepth.getText());
        paramCrawler.tasks = Integer.parseInt(jTFCrawlerTasks.getText());            
        paramCrawler.keyUrl = jTFKeyUrl.getText();
        extractors = Integer.parseInt(jTextExtractTasks.getText());
        if(paramCrawler.mode == 0){
            paramCrawler.dns = Crawler.getDNSByURL(paramCrawler.keyUrl);
        }
    }
        
    private void setCrawlerParams(){
        jTFCrawlerDeepth.setText(paramCrawler.deepth +"");
        jTFCrawlerTasks.setText(paramCrawler.tasks+"");            
        jTFKeyUrl.setText(paramCrawler.keyUrl);
        jTextExtractTasks.setText(extractors+"");
    }     
    
    
    public void InitTable(){
        modeCrawler1 = null;
        String[] header = { "序号","标签","名称","区号","邮编","地址","省","地市","县区","镇","村","路","号","大楼","楼层","户号","参照","状态"}; //定义表头
        modeCrawler1 = new DefaultTableModel(header,0);
        modeCrawler1.setColumnCount(18);
        jTableCrawler1.setModel(modeCrawler1);
        jTableCrawler1.getTableHeader().setFont(new Font("Dialog", 0, 14)); 
    }
    
    public void stopCrawler(){
        dispStat.stopDisp();
    }
    
    class MainCrawler implements Runnable{        
        public MainCrawler(DSCrawlerParam paramCrawler,int extractors){
            if(paramCrawler.dns.equals("") && paramCrawler.mode == 0){
                return; 
            }
            MainServer.dns = paramCrawler.dns;
            server = new MainServer();
            server.Initialize(extractors, paramCrawler);
            server.start();
        }
        
        public void run(){
            if (server == null)
                server =  new MainServer();
            
            Timestamp stamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            startTime = sdf.format(stamp);
            
            new Thread(dispStat).start();
        }
    }
    
    class DispState implements Runnable{
        int state = 1; 
        
        public synchronized void stopDisp(){
            state = 0;
        }
        
        public void run(){
            try {
                while(state == 1){
                    display();
                    Thread.sleep(5000);
                }
                server.running = 0;
                server.stopCrawling();
            } catch (InterruptedException ex) {
                Logger.getLogger(CrawlersMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void display(){
            int sum = 0;
            int UrlSum = 0;
            synchronized(this){
                int size = MainServer.DBCrawler.listCrawlerUrls.size();
                for(int i=0;i<size;i++){
                    int count = MainServer.DBCrawler.listCrawlerUrls.get(i).size();
                    sum = sum + count;
                } 
                jTableCrawler1.setModel(modeCrawler1);
            }
            synchronized(this){
                CrawlerStatInfo info = MainServer.DBCrawler.statInfo;
                
                int UrlCount = MainServer.DBCrawler.mapDownload.size();
                UrlSum = sum + UrlCount;
                jTxtUrlSum.setText(UrlSum +"");
                jTxtUrls.setText(UrlCount+"");                
                setProgressBar(jProgressBar1,UrlSum ,1, UrlCount);
                
                
                int pageSum = MainServer.DBCrawler.listHtmlPages.size();
                int pageCount = MainServer.state.pageExtractNum;
                jTxtPageSum.setText(pageSum + pageCount +"");
                jTxtPages.setText(pageCount+""); 
                jTxtPagesYes.setText(info.featureYes+"");
                jTxtPagesNo.setText(info.featureNo+"");
                int pageSum0 = pageSum + pageCount;
                setProgressBar(jProgressBar2, pageSum0 ,1, pageCount);                
                
                
                int ItemSum = MainServer.DBCrawler.listDSComposites.size();              
                
                int sumParse = info.getSumParse();                
                jTxtItemSum.setText(sumParse+"");
                jTextdirect.setText(info.parseDirect+""); 
                jTxtNull.setText(info.parseIndirect+""); 
                jTextAmb.setText(info.parseAmb+"");
                jTextItemInv.setText(info.parseInv + ""); 
                int parseSum = info.getSumParse();                
                setProgressBar(jProgressBar3,ItemSum ,1,parseSum);                

                jTextLocalConflict.setText(info.rsConflict+"");
                jTextLocalPart.setText(info.rsPart + info.rsFull + "");
                jTextLocalFull.setText(info.rsFull + "");  
                jTextLocalNull.setText(info.rsInvalidate + "");  
                jTextLocalSum.setText(info.getSumRs() +"");                
                
                int rsSum  = info.getSumRs();
                setProgressBar(jProgressBar4, rsSum, 1, info.rsFull + info.rsPart);
            }
        }
        
        private void setProgressBar(JProgressBar progressBar,int max,int min,int value){
                progressBar.setMaximum(max); 
                progressBar.setMinimum(min);
                progressBar.setValue(value);
                progressBar.setStringPainted(true);
                progressBar.setPreferredSize(new Dimension(300, 20));
                progressBar.setBorderPainted(true);
                progressBar.setBackground(Color.pink); 
                progressBar.setVisible(true);
        }
        
        public void dispTable(int modeFeature,int modeParse,int modeRs,int conflict){
            DisplayTale query = new DisplayTale(modeFeature,modeParse,modeRs,conflict);
            new Thread(query).start();         
        }

    }
    
    class DisplayTale implements Runnable{
//        private int index;
        private int modeFeature,modeParse,modeRs,conflict;
        public DisplayTale(int modeFeature,int modeParse,int modeRs,int conflict){
            //this.index = index;
            this.modeFeature =  modeFeature;
            this.modeParse = modeParse;
            this.modeRs = modeRs;
            this.conflict = conflict;
        }
        
        public void run(){
            try {
                InitTable();
                Thread.sleep(10); 
                
                Timestamp stamp = new Timestamp(System.currentTimeMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                endTime = sdf.format(stamp);   
                
                dispInfo(modeCrawler1,modeFeature,modeParse,modeRs,conflict);
            } catch (InterruptedException ex) {
                System.out.println("DisplayTale DisplayTale " + ex.getMessage());
                Logger.getLogger(CrawlersMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        private void dispInfo(DefaultTableModel mode,int modeFeature,int modeParse,int modeRs,int conflict){
            String[] vals ;
            List<DSComposite> list = new ArrayList<DSComposite>();
            LocalInfoDB localDB = new LocalInfoDB();
            localDB.queryStatByDate(list, modeFeature, modeParse, modeRs, conflict,startTime, endTime);
            
            for(int i=0;i<list.size();i++){               
                vals = new String[18];
                int no = i+1;
                vals[0] = no + "";
                vals[1] = list.get(i).label;
                vals[2] = list.get(i).name;                
                vals[3] = list.get(i).phone_code;
                vals[4] = list.get(i).postcode;
                vals[5] = list.get(i).local.address;
                vals[6] = list.get(i).local.province;
                vals[7] = list.get(i).local.city;
                vals[8] = list.get(i).local.county;
                vals[9] = list.get(i).local.township;
                vals[10] = list.get(i).local.village;
                vals[11] = list.get(i).local.Road;
                vals[12] = list.get(i).local.RoadNo;
                
                vals[13] = list.get(i).local.building;
                vals[14] = list.get(i).local.floor;
                vals[15] = list.get(i).local.room;
                vals[16] = list.get(i).local.reference;
                vals[17] = list.get(i).collection.url+"";
                mode.addRow(vals);
            }
        }
    }
   
    
    public static void main(String[] args){
        DSCrawlerUrl info = new DSCrawlerUrl();
        info.url = "http://www.aibang.com/shanghai/";
        if(info.url.equals(""))
            return;            
        String dns = Crawler.getDNSByURL(info.url);
        System.out.println(info.dns + ":" + dns);            
        PageCrawler items = new PageCrawler(0);            
        JFrame frame = new JFrame ("JTableDemo");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setContentPane (items.panel1); 
        frame.pack();
        frame.show();  
    }
}
    

