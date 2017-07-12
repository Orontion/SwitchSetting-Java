import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;



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
	
	public void chooseConfFile(String fileName)throws IOException{
		//TODO Вставить проверку наличия введённого имени файла в списке confFilesList
		InputStream confFileIS = null;
		InputStreamReader confFileReader = null;
		BufferedReader buffFileReader = null;
		
		try {
			String tempStr;
			confFile = new File(confFilePath + "/" + fileName);
			confFileIS = new FileInputStream(confFile);
			confFileReader = new InputStreamReader(confFileIS,Charset.forName("UTF-8"));
			buffFileReader = new BufferedReader(confFileReader);
			
			while ((tempStr = buffFileReader.readLine()) != null){
				System.out.println(tempStr);
			}
			
		} catch (Exception e) {
			//Ловим исключение, пишем сообщение, где оно произошло, и кидаем его дальше вверх
			throw new IOException("Error during parcing file" + fileName, e);
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
	
	public static void main(String[] args) {
		System.out.println(DEFAULT_FILE_PATH);
		
		FileParcer tfp;
		
		try {
			tfp = new FileParcer("e:/Saves");
			tfp.scanConfFileFolder();
			tfp.chooseConfFile("test.txt");
			System.out.println(tfp.showCurrentFilePath());
			System.out.println(tfp.showFilesInConfDirectory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
