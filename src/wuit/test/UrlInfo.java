package wuit.test;

public class UrlInfo {
	private String title = "";
	private String url = "";
	private String dns = "";
	private String IP = "";
	private String path = "";
	private String file = "";
	private int port = 80;
	private String protocol = "http";
	private String extend = "";
	private int deepth = 0;
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String val){
		this.title = val;
	}

	public String getUrl(){
		return this.url;
	}
	
	public void setUrl(String val){
		this.url = val;
	}

	public String getDNS(){

		return this.dns;
	}
	
	public void setDNS(String val){
		this.dns = val;
	}

	public String getIP(){
		return this.IP;
	}
	
	public void setIP(String val){
		this.IP = val;
	}
	
	public String getPath(){
		return this.path;
	}
	
	public void setPath(String val){
		this.path = val;
	}
	
	public String getFile(){
		return this.file;
	}
	
	public void setFile(String val){
		this.file = val;
	}
	
	public int getPort(){
		return this.port;
	}
	
	public void setPort(int val){
		this.port = val;
	}
	
	public String getProtocol(){
		return this.protocol;
	}
	
	public void setProtocol(String val){
		this.protocol = val;
	}
	
	public String getExtend(){
		return this.extend;
	}
	
	public void setExtend(String val){
		this.extend = val;
	}
	
	public int getDeepth(){
		return this.deepth;
	}
	
	public void setDeepth(int val){
		this.deepth = val;
	}
	

}
