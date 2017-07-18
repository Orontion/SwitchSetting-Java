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
	//Константа, в которой сохранен путь к папке с файлами конфигурирования по умолчанию - путь к папке приложения
	public static final String DEFAULT_CONF_DIR_PATH = System.getProperty("user.dir");
	
	//Строка с адресом папки файлов конфигурирования
	private String confDirPath;
	//Список конф-файлов в папке в виде их имён
	private List<String> confFilesList = new ArrayList<>();
	//Строка с именем выбранного конф-файла
	private String chosenFile;
	//Массив прочитанных конф-блоков
	private List<ConfBlock> confBlockList = new ArrayList<>();

	//Объекты для взаимодействия с файлами и папками
	private File confFileDir;
	private File confFile;
	
	public FileParcer(String confFilePath){
		this.setConfDirPath(confFilePath);
	}
	
	public FileParcer() {
		this.setConfDirPath();
	}
	
	
	public String showCurrentConfDirPath(){
		return confDirPath;
	}
	
	public void setConfDirPath(){
		this.setConfDirPath(DEFAULT_CONF_DIR_PATH);
	}
	
	public void setConfDirPath(String newConfDirPath){
		confDirPath = newConfDirPath;
		confFileDir = new File(confDirPath);
		if (!confFileDir.exists()){
			throw new RuntimeException("Directory does not exists!");
		}
		if (confFileDir.isFile()){
			throw new RuntimeException("Path to directory is file!");
		}
	}
	
	public void scanConfDir(){		
		confFilesList.clear();
		for (File tmpFile : confFileDir.listFiles()){
			if (tmpFile.isFile()){
				confFilesList.add(tmpFile.getName());
			}
		}
	}
	
	public List<String> showFilesInConfDir(){
		if (confFilesList.isEmpty()){
			throw new RuntimeException("There is no files in chosen directory!");
		}
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
			confFile = new File(confDirPath + "/" + chosenFile);
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
	
	public List<ConfBlock> getParsedConfBlocks(){
		if (confBlockList == null || confBlockList.isEmpty()){
			throw new RuntimeException("Configuration file was not parsed!");
		}
		return confBlockList;
	}
	
	public static void main(String[] args) {
		System.out.println(DEFAULT_CONF_DIR_PATH);
		
		FileParcer tfp;
		
		try {
			tfp = new FileParcer("e:/Saves");
			tfp.scanConfDir();
			tfp.chooseConfFile("D-Link DES-3526.txt");
			tfp.parseConfFile();
			System.out.println(tfp.showCurrentConfDirPath());
			System.out.println(tfp.showFilesInConfDir());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
