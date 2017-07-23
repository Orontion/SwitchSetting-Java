package interfc.settingswindow;

import interfc.mainwindow.view.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsWindowClass extends Application {
	//Loader для доступа к контроллеру
	FXMLLoader stgWinLoader;
	//Ссылка на контроллер интерфейса
	MainWindowController stgWinCtrl;
	//Stage для отображения окна настроек
	Stage stgWinStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stgWinLoader = new FXMLLoader(getClass().getResource("view/SettingsWindowView.fxml"));
		
		Parent stgView = stgWinLoader.load();
		
		Scene stgScene = new Scene(stgView);
		
		stgWinStage = new Stage();
		stgWinStage.setScene(stgScene);
		stgWinStage.setTitle("Настройки приложения");
		stgWinStage.initModality(Modality.WINDOW_MODAL);
		stgWinStage.initOwner(primaryStage);
		stgWinStage.show();
		
//		primaryStage.setScene(stgScene);
//		primaryStage.setTitle("Настройки приложения");
//		primaryStage.initModality(Modality.WINDOW_MODAL);
//		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
