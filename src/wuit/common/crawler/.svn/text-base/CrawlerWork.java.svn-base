package wuit.common.crawler;

import java.util.ArrayList;
import java.util.List;

import wuit.common.crawler.composite.DSExtractor;
import wuit.common.doc.FileIO;
import wuit.common.doc.xml.ParseWorkConfigXml;

public class CrawlerWork extends Thread {
    private int workState = 0;
    private List<DSExtractor> lstParams = new ArrayList<DSExtractor>();		//提取信息的项目结合

    private javax.swing.JLabel _statusLabel = null;
    
    public void setDispLabel(javax.swing.JLabel label){
        this._statusLabel = label;
    }
    
    public void run(){
        for (int j=0;j<lstParams.size();j++){
            CrawlerJob job = new CrawlerJob(lstParams.get(j));
            job.setDispLabel(_statusLabel);
            listJobs.add(job);
            job.start();
        }
        try{
            Thread.sleep(1000);
            while(true){
                Thread.sleep(1000);
                if(workState == 0)
                    break;
            }
            
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
    
    class DownUrlInfo{
        public int count = 0;
    }
 
    private List<CrawlerJob> listJobs = new ArrayList<CrawlerJob>();

    public CrawlerWork(){
    }

    public void addParams(DSExtractor param){
        lstParams.add(param);
    }
    
    public void getParams(String path){
        String appPath = System.getProperty("user.dir");
        String jobPath = appPath + "\\jobs\\";
        List<String> listFiles = new ArrayList<String>();
        FileIO.getPathFiles(jobPath, listFiles);
        if (listFiles.size()==0){
            System.out.println(" the work config file is not found !!!");
            return;
        }
        
        ParseWorkConfigXml config ;
        for (int i=0;i<listFiles.size();i++){
            String pathFile = listFiles.get(i);
            pathFile = "D:\\product\\java\\jobs\\WorkConfig.xml";
            
            config = new ParseWorkConfigXml();
            config.parseWorks(pathFile);
            lstParams = config.getWorksParams();
            for (int j=0;j<lstParams.size()-1;j++){
                CrawlerJob job = new CrawlerJob(lstParams.get(j));
                
                job.start();
                listJobs.add(job);
            }
        }
    }
    
    public void Init(){
        String appPath = System.getProperty("user.dir");
        System.out.println(appPath);
        String jobPath = appPath + "\\jobs\\";
        
        ParseWorkConfigXml config ;
        List<String> listFiles = new ArrayList<String>();
        FileIO.getPathFiles(appPath, listFiles);
        if (listFiles.size()==0){
            System.out.println(" the work config file is not found !!!");
            return;
        }
        
        for (int i=0;i<listFiles.size();i++){
            String pathFile = listFiles.get(i);
            pathFile = "D:\\product\\java\\jobs\\WorkConfig.xml";
            
            config = new ParseWorkConfigXml();
            config.parseWorks(pathFile);
            lstParams = config.getWorksParams();
            for (int j=0;j<lstParams.size()-1;j++){
                CrawlerJob job = new CrawlerJob(lstParams.get(j));
                job.start();
                listJobs.add(job);
            }
        }
        System.out.println(lstParams.size());
    }

    public synchronized int getWorkState(){
        return workState;
    }

    //0:end;1:running;2:interruput
    public synchronized void setWorkState(int state){
        this.workState = state;
        if(state == 0){
            for (int i=0;i<listJobs.size();i++){
                listJobs.get(i).setStatus(state);
            }
        }
    }    
}
