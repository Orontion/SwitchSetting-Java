package interfc.mainwindow;

import fileparcer.FileParcer;
import interfc.mainwindow.view.MainWindowController;
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
	//Ссылка на объект-парсер 
	FileParcer mainFileParcer;
	//Stage для отображения окна
	Stage mainWindowStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		//TODO Запилить нормальную обработку исключений
		
		//Получаем FXML-файл формы
		mainWindowLoader = new FXMLLoader();
		mainWindowLoader.setLocation(getClass().getResource("view/MainWindowView.fxml"));
			
		//Загружаем полученный файл в объект Parent
		Parent mainView = mainWindowLoader.load();
		
		//Получаем ссылку на контроллер интерфейса
		mainWindowCtrl = mainWindowLoader.getController();
		
		//Действия при загрузке формы
		mainFileParcer = new FileParcer("e:/Saves"); //TODO Получать ссылку на объект из ядра программы, а не создавать его заново
		mainFileParcer.scanConfFileFolder(); //Сканируем папку
		mainWindowCtrl.fillCmbConfFileSelect(mainFileParcer.showFilesInConfDirectory()); //Запускаем функцию для подготовки формы к показу
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
