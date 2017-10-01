package interfc.settingswindow;

import java.io.File;

import confcore.ConfigurationCore;
import interfc.mainwindow.MainWindowClass;
import interfc.settingswindow.view.SettingsWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import swsetexceptions.NoSerialPortsInSystemException;
import swsetexceptions.SwitchSettingException;

public class SettingsWindowClass extends Application {
	//Loader для доступа к контроллеру
	FXMLLoader stgWinLoader;
	//Ссылка на контроллер интерфейса
	SettingsWindowController stgWinCtrl;
	//Stage для отображения окна настроек
	Stage stgWinStage;
	//Ссылка на ядро программы
	ConfigurationCore programCoreRef;
	
	//TODO Придумать передачу информации о смене директории, не требующую ссылки на главное окно
	//Ссылка на главное окно
	MainWindowClass mainWindowRef;
	
	//Конструктор, вынуждающий передавать ссылки на объекты главного окна и ядра программы ====================================================================
	public SettingsWindowClass(ConfigurationCore programCoreRef, MainWindowClass mainWindowRef) {
		this.programCoreRef = programCoreRef;
		this.mainWindowRef = mainWindowRef;
	}
	
	//Старт окна настроек ========================================================================================================================================
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Загрузка view-файла формы
		stgWinLoader = new FXMLLoader(getClass().getResource("view/SettingsWindowView.fxml"));
		
		//Загружаем полученный файл в объект Parent (родительский объект для всей остальной формы)
		Parent stgView = stgWinLoader.load();
		
		//Получение ссылки на объект-контроллер окна, и передача этому объекту ссылки на текущий объект-окно
		stgWinCtrl = stgWinLoader.getController();
		stgWinCtrl.setSettingsWindowRef(this);
		
		//TODO Придумать try-catch покрасивее
		//Пробуем заполнить combobox имеющимися в системе COM-портами
		try {
			stgWinCtrl.fillSerialPortList(programCoreRef.getSerialPortList());
		} catch (NoSerialPortsInSystemException e){
			//Если доступные порты отсутствуют - так и пишем в combobox-е
			stgWinCtrl.fillSerialPortList();
		} catch (Exception e) {
			//Если что-то пошло не так
			throw new SwitchSettingException("Unknown exception during SerialPortList getting!", e);
		}
		
		//Показываем на форме тот путь к папке с конф-файлами, который в данный момент сохранён в ядре
		stgWinCtrl.setConfDirPath(programCoreRef.getConFileDir());
		
		
		//Создаём сцену на основе формы в stgView
		Scene stgScene = new Scene(stgView);

		//Запускаем сцену и показываем её на экране
		stgWinStage = new Stage();
		stgWinStage.setScene(stgScene);
		stgWinStage.setTitle("Настройки приложения");
		stgWinStage.initModality(Modality.WINDOW_MODAL);
		stgWinStage.initOwner(primaryStage);
		stgWinStage.show();
	}

	
	//Вызов диалога выбора папки с конфигурационными файлами =======================================================================
	public void chooseConfFileDir(){
		DirectoryChooser dirChooser = new DirectoryChooser(); 	//Объект диалогового окна
		File newConfDir; 										//Т.к. DirectoryChooser.showDialog() возвращает тип File - используем временный объект
		
		newConfDir = dirChooser.showDialog(stgWinStage); //Получаем новую папку из диалогового окна
		
		//Проверка, что диалоговое окно не было просто закрыто. Если окно закрыли, и ссылка newConfDir равна null - изменений в настройки не вносится.
		if (newConfDir != null){
			programCoreRef.setConFileDir(newConfDir.getAbsolutePath());
			stgWinCtrl.setConfDirPath(newConfDir.getAbsolutePath());
			mainWindowRef.refreshFileListCmb();
		}
	}

	//Выбор COM-порта для работы ==================================================================================================
	public void chooseSerialPort(String serialPortName){
		programCoreRef.chooseSerialPort(serialPortName);
	}
	
	//Выбор скорости порта ==================================================================================================
	public void chooseSerialPortSpeed(int chosedSpeed){
		programCoreRef.chooseSerialPortSpeed(chosedSpeed);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
