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
import sharedres.SwitchParameters;

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
	//Объект с прочитанными параметрами свитча
	private SwitchParameters readedSwParams = new SwitchParameters();

	//Объекты для взаимодействия с файлами и папками
	private File confFileDir;
	private File confFile;
	
	//Конструктор с установкой пути к папке =============================================================================================================================
	public FileParcer(String confFilePath){
		this.setConfDirPath(confFilePath);
	}
	
	//Конструктор по умолчанию =============================================================================================================================
	public FileParcer() {
		this.setConfDirPath();
	}
	
	//Getter для текущего пути к папке =============================================================================================================================
	public String getCurrentConfDirPath(){
		return confDirPath;
	}
	
	//Setter пути к папке по умолчанию =============================================================================================================================
	public void setConfDirPath(){
		this.setConfDirPath(DEFAULT_CONF_DIR_PATH);
	}
	
	//Setter пути к папке с параметром =============================================================================================================================
	//TODO Переделать алгоритм так, чтобы при попытке установить неверный ConfDirPath восстанавливалось старое значение переменной
	public void setConfDirPath(String newConfDirPath){
		//Сохраняем новый путь
		confDirPath = newConfDirPath;
		
		//Создаём новый File с путём до папки
		confFileDir = new File(confDirPath);
		
		//Проверка существования такого файла
		if (!confFileDir.exists()){
			throw new RuntimeException("Directory does not exists!");
		}
		
		//Проверка того, что папка - это папка
		if (confFileDir.isFile()){
			throw new RuntimeException("Path to directory is file!");
		}
	}
	
	//Сканирование папки с конф-файлами =============================================================================================================================
	public void scanConfDir(){		
		//Очистка старого списка конф-файлов
		confFilesList.clear();
		
		//Добавляем в список все файлы из папки
		//TODO Сделать фильтр по расширению
		for (File tmpFile : confFileDir.listFiles()){
			if (tmpFile.isFile()){
				confFilesList.add(tmpFile.getName());
			}
		}
	}
	
	//Получение списка имён конф-файлов =============================================================================================================================
	public List<String> showFilesInConfDir(){
		//Исключение если в папке нет файлов
		if (confFilesList.isEmpty()){
			throw new RuntimeException("There is no files in chosen directory!");
		}
		return confFilesList;
	}
	
	//Выбор конкретного конф-файла для работы =============================================================================================================================
	public void chooseConfFile(String chosenFileName){
		//TODO Вставить проверку наличия введённого имени файла в списке confFilesList
		this.chosenFile = chosenFileName;
	}
	
	//Парсинг конф-файла =============================================================================================================================
	public void parseConfFile()throws IOException{
		//Reader-ы для чтения файла
		InputStream confFileIS = null;
		InputStreamReader confFileReader = null;
		BufferedReader buffFileReader = null;
		//TODO Вставить проверку отсутствия выбранного файла
		
		
		try {	
			//Строка, которую будут читать из файла
			String tempStr;
			//Массив, на который будут делить строку
			String splittedStr[];
			
			//Открываем файл для чтения
			confFile = new File(confDirPath + "/" + chosenFile);
			confFileIS = new FileInputStream(confFile);
			confFileReader = new InputStreamReader(confFileIS,Charset.forName("UTF-8")); //кодировка - UTF-8
			buffFileReader = new BufferedReader(confFileReader);
			
			//Индекс конфблока, который в данный момент читается 
			int confBlockListIndex = 0;
			
			//Regexp для разделения блоков
			Pattern confOper = Pattern.compile("\\{|\\}");
			
			//Очищаем ранее прочитанные конфблоки
			confBlockList.clear();
			//Построчно читаем файл
			while ((tempStr = buffFileReader.readLine()) != null){
				//Первая секция - параметры свитча
				if(tempStr.equals("SWITCHPARAM")){
					tempStr = buffFileReader.readLine();
					while (!tempStr.equals("END")){
						splittedStr = confOper.split(tempStr);
						readedSwParams.readParamLine(splittedStr);
						tempStr = buffFileReader.readLine();
					}
					tempStr = buffFileReader.readLine();
					continue;
				}
				
				//Вторая секция - блоки настроек
				if (tempStr.equals("BLOCK")){
					//После управляющего слова BLOCK читаем следующую строку - заголовок блока
					tempStr = buffFileReader.readLine();
					//Если она не пуста - создаём новый ConfBlock с этим заголовком...
					if (!tempStr.equals("/n")){
						confBlockList.add(new ConfBlock(tempStr)); //... т.е. добавляем в confBlockList новый ConfBlock
						confBlockListIndex = confBlockList.size() - 1; //И запоминаем индекс конфблока, с которым мы работаем
						continue;
					}
					//Если в строке ничего нет - создаём конфблок с пустым заголовком
					else{
						confBlockList.add(new ConfBlock());
						confBlockListIndex = confBlockList.size() - 1;
						continue;
					}
				}
				
				//Если строка не является управляющим словом - делим её на подстроки по скобкам {}
				splittedStr = confOper.split(tempStr);
				//Добавляем в конфблок строку-команду...
				confBlockList.get(confBlockListIndex).addNewLine();
				//... и забиваем в неё операторы. Если переменных типа {VLAN} нет - вся строка является одним оператором
				//Если переменные есть <add {VLAN} tag {VLAN}>, то строка превращается в массив операторов: [add ][VLAN][ tag ][VLAN]
				for (String strIter : splittedStr){
					confBlockList.get(confBlockListIndex).addNewOperator(strIter);
				}
			}
			
		} catch (Exception e) {
			//Cброс всех прочитанных параметров к начальным значениям
			confBlockList.clear();
			readedSwParams.resetAll();
			
			//Ловим исключение, пишем сообщение, где оно произошло, и выкидываем его наружу
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
	
	//Получение готовых конфблоков для работы =============================================================================================================================
	public List<ConfBlock> getParsedConfBlocks(){
		//Если прочитанных конфблоков нет - кидаем исключение
		if (confBlockList == null || confBlockList.isEmpty()){
			throw new RuntimeException("Configuration file was not parsed!");
		}
		return confBlockList;
	}
	
	//Getter для параметров свитча =============================================================================================================================
	public SwitchParameters getSwitchParameters(){
		return readedSwParams;
	}
	
	
	//Функция main для тестов =============================================================================================================================
	public static void main(String[] args) {
		System.out.println(DEFAULT_CONF_DIR_PATH);
		
		FileParcer tfp;
		
		try {
			tfp = new FileParcer("e:/Saves");
			tfp.scanConfDir();
			tfp.chooseConfFile("D-Link DES-3526.txt");
			tfp.parseConfFile();
			System.out.println(tfp.getCurrentConfDirPath());
			System.out.println(tfp.showFilesInConfDir());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
