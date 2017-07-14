package fileparcer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import sharedres.ConfBlock;



public class FileParcer {
	//Константа, в которой сохранен путь к файлам конфигурирования по умолчанию - путь к файлам приложения
	public static final String DEFAULT_FILE_PATH = System.getProperty("user.dir");
	
	
	
	private String confFilePath;
	private List<String> confFilesList = new ArrayList<>();
	private List<ConfBlock> confBlockList = new ArrayList<>();
	private String chosenFile;
	
	private File confFileDir;
	private File confFile;
	
	
	public FileParcer(String confFilePath){
		this.setConfFileDir(confFilePath);
	}
	
	public FileParcer() {
		this.setConfFileDir();
	}
	
	public String showCurrentFilePath(){
		return confFilePath;
	}
	
	public void setConfFileDir(){
		this.setConfFileDir(DEFAULT_FILE_PATH);
	}
	
	public void setConfFileDir(String newConfFilePath){
		confFilePath = newConfFilePath;
		confFileDir = new File(confFilePath);
		if (!confFileDir.exists()){
			throw new RuntimeException("Directory does not exists!");
		}
		if (confFileDir.isFile()){
			throw new RuntimeException("Path to directory is file!");
		}
	}
	
	public void scanConfFileFolder(){		
		confFilesList.clear();
		for (File tmpFile : confFileDir.listFiles()){
			if (tmpFile.isFile()){
				confFilesList.add(tmpFile.getName());
			}
		}
	}
	
	public List<String> showFilesInConfDirectory(){
		return confFilesList;
	}
	
	public void chooseConfFile(String chosenFileName){
		//TODO Вставить проверку наличия введённого имени файла в списке confFilesList
		this.chosenFile = chosenFileName;
	}
	
	public void parseConfFile()throws IOException{
		InputStream confFileIS = null;
		InputStreamReader confFileReader = null;
		BufferedReader buffFileReader = null;
		//TODO Вставить проверку отсутствия выбранного файла
		
		
		try {
			String tempStr;
			String splittedStr[];
			confFile = new File(confFilePath + "/" + chosenFile);
			confFileIS = new FileInputStream(confFile);
			confFileReader = new InputStreamReader(confFileIS,Charset.forName("UTF-8"));
			buffFileReader = new BufferedReader(confFileReader);
			int confBlockListIndex = 0;
			
			Pattern confOper = Pattern.compile("\\{|\\}");
			
			confBlockList.clear();
			while ((tempStr = buffFileReader.readLine()) != null){
				if (tempStr.equals("BLOCK")){
					tempStr = buffFileReader.readLine();
					if (!tempStr.equals("/n")){
						confBlockList.add(new ConfBlock(tempStr));
						confBlockListIndex = confBlockList.size() - 1;
						continue;
					}
					else{
						confBlockList.add(new ConfBlock());
						confBlockListIndex = confBlockList.size() - 1;
						continue;
					}
				}
				
				splittedStr = confOper.split(tempStr);
				confBlockList.get(confBlockListIndex).addNewLine();
				for (String strIter : splittedStr){
					confBlockList.get(confBlockListIndex).addNewOperator(strIter);
				}
			}
			
		} catch (Exception e) {
			//Ловим исключение, пишем сообщение, где оно произошло, и кидаем его дальше вверх
			throw new IOException("Error during parcing file " + chosenFile, e);
		} finally {
			//Если был косяк во время парсинга, закрываем InputStream, если он был открыт. 
			if (confFileIS != null){
				try{
					confFileIS.close();
				} catch (Exception e) {
					//Если во время закрытия InputStream произошла ошибка - просто скажем, что она была
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<ConfBlock> getParsedConf(){
		if (confBlockList == null){
			throw new RuntimeException("Configuration file was not parsed!");
		}
		return confBlockList;
	}
	
	public static void main(String[] args) {
		System.out.println(DEFAULT_FILE_PATH);
		
		FileParcer tfp;
		
		try {
			tfp = new FileParcer("e:/Saves");
			tfp.scanConfFileFolder();
			tfp.chooseConfFile("D-Link DES-3526.txt");
			tfp.parseConfFile();
			System.out.println(tfp.showCurrentFilePath());
			System.out.println(tfp.showFilesInConfDirectory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
