package interfc.mainwindow;

import confcore.ConfigurationCore;
import interfc.mainwindow.view.MainWindowController;
import interfc.settingswindow.SettingsWindowClass;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindowClass extends Application {
	//Loader для доступа к контроллеру
	FXMLLoader mainWindowLoader;
	//Ссылка на контроллер интерфейса
	MainWindowController mainWindowCtrl;
	//Объект-ядро, создаётся в объекте главного окна 
	ConfigurationCore programCore = new ConfigurationCore();
	//Stage для отображения окна
	Stage mainWindowStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		//TODO Запилить нормальную обработку исключений
		
		//Передача ссылки на окно в ядро
		programCore.setMainWindow(this);
		
		//Получаем FXML-файл формы
		mainWindowLoader = new FXMLLoader();
		mainWindowLoader.setLocation(getClass().getResource("view/MainWindowView.fxml"));
			
		//Загружаем полученный файл в объект Parent
		Parent mainView = mainWindowLoader.load();
		
		//Получаем ссылку на контроллер интерфейса
		mainWindowCtrl = mainWindowLoader.getController();
		
		//Действия при загрузке формы
		mainWindowCtrl.fillCmbConfFileSelect(programCore.getConfFilesList()); //Получаем список файлов
		mainWindowCtrl.setMainWindowRef(this);
		
		//Создаём сцену на основе формы в mainView
		Scene mainScene = new Scene(mainView);
		
		//Запускаем сцену и показываем её на экране
		mainWindowStage = new Stage();
		mainWindowStage.setScene(mainScene);
		mainWindowStage.setTitle("Настройка коммутаторов");
		mainWindowStage.setMinHeight(mainScene.getHeight());
		mainWindowStage.setMinWidth(mainScene.getWidth());
		mainWindowStage.show();
		
	}

	public Stage getMainWindowStage(){
		return mainWindowStage;
	}
	
	
	//Метод отображения окна с настройками ===================================================
	public void showSettingsWindow(){
		//Новый объект окна настроек
		SettingsWindowClass stgWindow = new SettingsWindowClass(programCore);
		
		//Показываем созданное окно
		try {
			stgWindow.start(mainWindowStage);
		} catch (Exception e) {
			throw new RuntimeException("Cannot start settings window");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
