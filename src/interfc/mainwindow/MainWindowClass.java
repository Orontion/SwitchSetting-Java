package interfc.mainwindow;

import fileparcer.FileParcer;
import interfc.mainwindow.view.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindowClass extends Application {
	FXMLLoader mainWindowLoader;
	MainWindowController mainWindowCtrl;
	FileParcer mainFileParcer;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		//TODO Запилить нормальную обработку исключений
		mainWindowLoader = new FXMLLoader();
		mainWindowLoader.setLocation(getClass().getResource("view/MainWindowView.fxml"));
			
		
		Parent mainView = mainWindowLoader.load();
		
		
		mainWindowCtrl = mainWindowLoader.getController();
		mainFileParcer = new FileParcer("e:/Saves");
		mainFileParcer.scanConfFileFolder();
		mainWindowCtrl.startForm(mainFileParcer.showFilesInConfDirectory());
		
		Scene mainScene = new Scene(mainView);
		
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("Настройка коммутаторов");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
