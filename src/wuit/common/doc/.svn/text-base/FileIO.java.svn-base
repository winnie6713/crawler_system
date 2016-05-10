package wuit.common.doc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;


import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileIO {
	protected static final String NEWLINE = "\r\n";
	protected static final String HASH_ALGORITHM = "md5";
	public static final String SEPARATOR = "===================================";
	
	public FileIO(){
		
	}
	

	/*
	public String getImageDir(){
		return imageDir;
	}
	
	public void setIamgeDir(String imageDir){
		this.imageDir = imageDir;
	}
	*/
	/*
	 * 写入文本文件
	 */
	public static void writeAsTxts(String path,String fileName,String content){
		if(path == null)
			return;
      File _filePath = new File(path);
         if(!_filePath.exists()) 
            _filePath.mkdirs();
		try{
			if(fileName == null || fileName.equals("")){
				return;
			}
			fileName= fileName + (new Date()).getTime();
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path + "\\" + fileName + ".txt")));
			bw.write(content + NEWLINE);
			bw.flush();
			bw.close();
		}catch(IOException e){
         System.out.println("FileIO :" + e.getMessage());
		}
	}

    public static void writeAsTxtList(String path,String fileName,List<String> items){
        if(path == null)
            return;
        File _filePath = new File(path);
        if(!_filePath.exists()) 
            _filePath.mkdirs();
        try{
            if(fileName == null || fileName.equals("")){
                return;
            }
            String _fileName= fileName + (new Date()).getTime();
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path + _fileName + ".txt")));
            for (int i=0;i<items.size();i++){
                bw.write(items.get(i) + NEWLINE);
            }
            bw.flush();
            bw.close();
        }catch(IOException e){
            System.out.println("FileIO :" + e.getMessage());
        }
    } 
   
   public static void writeAsTxtMap(String path,String fileName,Map<String,String> items){
       if(path == null)
           return;
       File _filePath = new File(path);
       if(!_filePath.exists())
           _filePath.mkdirs();
       try{
           if(fileName == null || fileName.equals("")){
               return;
           }
           String _fileName= fileName + (new Date()).getTime();
           BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path + _fileName + ".txt")));
           Set set = items.entrySet();
           Iterator it = set.iterator();
           while(it.hasNext()){
               Entry<String,String> body = (Entry<String,String>)it.next();
               bw.write(body.getValue() + NEWLINE);
           }
           bw.flush();
           bw.close();
       }catch(IOException e){
           System.out.println("FileIO :" + e.getMessage());
       }
   }
   
   
   public static void writeAsTxt(String path,String fileName,String content){
       if(path == null)
           return;
       try{
         File _filePath = new File(path);
         if(!_filePath.exists()) 
            _filePath.mkdirs();
			if(fileName == null || fileName.equals("")){
				return;
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path + fileName )));
			bw.write(content + NEWLINE);
			bw.flush();
			bw.close();
		}catch(IOException e){
			System.out.println("FileIO.writeAsText :" + e.getMessage());
		}
	}
	
   
	/*
	 * 读取文本文件
	 * param path :文件目录
	 * param filename :文件名称
	 * param encode : 编码(utf-8,gbk,gb2312...)
	 */
	public static String read(String path,String fileName,String encode){
		String rs = "";
		BufferedReader br = null;
		if(path == null)
			return rs;
		try{
			if(fileName == null || fileName.equals(""))
				return rs;
			fileName= path + fileName;
			File file = new File(fileName);
			if (!encode.equals(""))
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file),encode));
			else
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String c = br.readLine();
			while(c != null){
				rs= rs + c +"\r\n";
				c = br.readLine();
			}
			br.close();
			return rs;
		}catch(IOException e){
			e.printStackTrace();
		}
		return rs;
	}
	
   /**
    * 
    * @param fileName   文件目录+文件名
    * @param encode     字符编码
    * @return           字符串
    */
   public static String read(String fileName,String encode){
		String rs = "";		
		if(fileName == null)
			return rs;
		try{		
			File file = new File(fileName);
         BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),encode));

			String c = br.readLine();
			while(c != null){
				rs= rs + c +"\r\n";
				c = br.readLine();
			}
			br.close();
			return rs;
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		return rs;
	}
   
   public static void readLines(String path,String fileName,String encode,List<String> listItems){
		BufferedReader br = null;
		if(path == null)
			return ;
		try{
			if(fileName == null || fileName.equals(""))
				return ;
			fileName= path + fileName;
			File file = new File(fileName);
			if (!encode.equals(""))
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file),encode));
			else
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String c = br.readLine();
			listItems.add(br.readLine());
			while(c != null){
				c = br.readLine();
				listItems.add(c);
			}

			br.close();
			return ;
		}catch(IOException e){
			System.out.println("Composite Parse readLine  " + e.getMessage());
		}   
   }
   
   public static void readLines(String pathFile,String encode,List<String> listItems){
		BufferedReader br = null;
		if(pathFile == null)
			return ;
		try{
			if(pathFile.equals(""))
				return ;
			File file = new File(pathFile);
			if (!encode.equals(""))
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file),encode));
			else
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String c = br.readLine();
			listItems.add(c);
			while(c != null){
				c = br.readLine();
				listItems.add(c);
			}

			br.close();
			return ;
		}catch(IOException e){
			System.out.println("Composite Parse readLine  " + e.getMessage());
		}   
   }   
	/*
	 * 读取目录文件
	 * param path：文件目录
	 */
   /*
	public String getFiles(String path){
		File filePath = new File(path);
		String[] files = null;
		if(filePath.isDirectory()){
			files = filePath.list();
		}
		String fileStr = "";
		for(int i=0;i<files.length;i++){
			fileStr = fileStr + files[i] +";";
		}
		return fileStr;
	}
        */ 
	/*
	 * 
	 */
        /*
	public String[] getFileList(String path){
		File filePath = new File(path);
		String[] files = null;
		if(filePath.isDirectory()){
			files = filePath.list();
		}
		return files;
	}
	*/
	/*
	protected boolean copyImage(String image_url,String new_image_file){
		String dirs = image_url.substring(7);
		try{
			File file_in = new File(new File(mirrorDir),dirs);
			if(file_in == null || !file_in.exists()){
				file_in = new File(mirrorDir +"\\noimage.jpg");
			}
			File file_out = new File(new File(imageDir),new_image_file);
			FileInputStream in1 = new FileInputStream(file_in);
			FileOutputStream out1 = new FileOutputStream(file_out);
			byte[] bytes = new byte[1024];
			int c;
			while((c= in1.read(bytes)) != -1){
				out1.write(bytes,0,c);
			}
			in1.close();
			out1.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	*/
	
	/**
	* 创建目录
	* @param destDirName 目标目录名
	* @return 目录创建成功返回true，否则返回false
	*/
	public static boolean createDir(String destDirName) {
	    File dir = new File(destDirName);
	    if(dir.exists()) {
	     System.out.println("创建目录" + destDirName + "失败，目标目录已存在！");
	     return false;
	    }
	    if(!destDirName.endsWith(File.separator))
	     destDirName = destDirName + File.separator;
	    // 创建单个目录
	    if(dir.mkdirs()) {
	     System.out.println("创建目录" + destDirName + "成功！");
	     return true;
	    } else {
	     System.out.println("创建目录" + destDirName + "成功！");
	     return false;
	    }
	}
   
   ////////////////////////
    /**
     * Used to list the files / subdirectories in a given directory.
     * @param dir Directory to start listing from
     */
    private static void doSimpleFileListing(String dirName) {

        System.out.println();
        System.out.println("Simple file listing...");
        System.out.println("----------------------");

        File dir = new File(dirName);
       
        String[] children = dir.list();

        printFiles(children, dirName);

    }


    /**
     * Used to list the files / subdirectories in a given directory and also
     * provide a filter class.
     * @param dir Directory to start listing from
     * @param ff  A string that can be used to filter out files from the
     *            returned list of files. In this example, the String
     *            values is used to only return those values that start
     *            with the given String.
     */
    private static void doFileFilterListing(String dirName, String ff) {

        System.out.println("Filter file listing...");
        System.out.println("----------------------");

        final String fileFilter = ff;

        File           dir     = new File(dirName);
        FilenameFilter filter  = null;

        if (fileFilter != null) {

            // It is also possible to filter the list of returned files.
            // This example uses the passed in String value (if any) to only
            // list those files that start with the given String.
            filter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.startsWith(fileFilter);
                }
            };
        }

        String[] children = dir.list(filter);

        printFiles(children, dirName);

    }


    /**
     * Used to list the files / subdirectories in a given directory and also
     * provide a filter class that only lists the directories.
     * @param dir Directory to start listing from
     */
    private static void doFileFilterDirectoryListing(String dirName) {

        System.out.println("Filter Directory listing...");
        System.out.println("---------------------------");

        File dir = new File(dirName);
        
        

        // The list of files can also be retrieved as File objects. In this
        // case, we are just listing all files in the directory. For the sake
        // of this example, we will not print them out here.
        File[] files = (new File(dirName)).listFiles();

        // This filter only returns directories
        FileFilter dirFilter = new FileFilter() {
            public boolean accept(File dir) {
                return dir.isDirectory();
            }
        };

        files = dir.listFiles(dirFilter);

        for (int i=0; i<files.length; i++) {
            System.out.println("[D] : " + files[i]);
        }
        System.out.println();
    }


    /**
     * Utility method to print the list of files to the terminal
     * @param children A String array of the file names to print out
     * @param dirName  The given directory to start the listing at.
     */
    private static void printFiles(String[] children, String dirName) {

        if (children == null) {
            System.out.println("Error with " + dirName);
            System.out.println("Either directory does not exist or is not a directory");
        } else {
            for (int i=0; i<children.length; i++) {
                // Get filename of file or directory
                String filename = children[i];
                if ((new File(dirName + File.separatorChar + filename)).isDirectory()) {
                    System.out.print("[D] : ");
                } else {
                    System.out.print("[F] : ");
                }
                System.out.println(dirName + File.separatorChar + filename);
            }
        }
        System.out.println();

    }
    
    public static void getFilesAndSubDirFiles(String path,List<String> lstFiles,List<String> dirs){
       File dir = new File(path);        
       File[] files = (new File(path)).listFiles();
       FileFilter fileFilter = new FileFilter() {
            public boolean accept(File dir) {
                return dir.isFile();
            }
       };       
       files = dir.listFiles(fileFilter);
       for(int i=0;i<files.length;i++){
          lstFiles.add(files[i].toString());
       }
       // This filter only returns directories
       FileFilter dirFilter = new FileFilter() {
            public boolean accept(File dir) {
                return dir.isDirectory();
            }
        };

        files = dir.listFiles(dirFilter);
        for(int i=0;i<files.length;i++){
           getFilesAndSubDirFiles(files[i].getAbsolutePath(),lstFiles,dirs);
        }
    }
    
    public static void getPathFiles(String path,List<String> lstFiles){
       File dir = new File(path);        
       File[] files = (new File(path)).listFiles();
       FileFilter fileFilter = new FileFilter() {
            public boolean accept(File dir) {
                return dir.isFile();
            }
       };       
       files = dir.listFiles(fileFilter);
       if(files == null)
           return;
       for(int i=0;i<files.length;i++){
          lstFiles.add(files[i].toString());
       }
    }    
    

    public static void main(String[] args){
        // Call the simple file listing method
       String filePath = "D:\\Projects\\search engine\\CrawlerSystem\\database\\Carwler_data\\temp\\content\\上海市\\";
       
       List<String> files = new ArrayList<String>();
       List<String> dirs = new ArrayList<String>();
       getFilesAndSubDirFiles(filePath,files,dirs);
       
       for (int i=0;i<files.size();i++){
          System.out.println(files.get(i));
       }
       
        doSimpleFileListing(filePath);

        // Now do the file listing but pass in a String to filter the file list
        if (args.length == 0) {
            doFileFilterListing(filePath, null);
        } else {
            doFileFilterListing(filePath, args[0]);
        }

        // Now do another example that only prints out the directories
        doFileFilterDirectoryListing(filePath);

    }
    ///////////////////////////////////
   public static String match(String valstring,String filter){
		String val ="";
		try{		
			Matcher m = Pattern.compile(filter,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(valstring);
			while (m.find()) {
			   val =m.group();
			   break;
		   }
		}catch(Exception e){
			System.out.println("File IO match " + e.getMessage());
		}
		return val;
	}    
}
