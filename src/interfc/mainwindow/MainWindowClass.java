package interfc.mainwindow;

import fileparcer.FileParcer;
import interfc.mainwindow.view.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindowClass extends Application {
	//Loader ��� ������� � �����������
	FXMLLoader mainWindowLoader;
	//������ �� ���������� ����������
	MainWindowController mainWindowCtrl;
	//������ �� ������-������ 
	FileParcer mainFileParcer;
	//Stage ��� ����������� ����
	Stage mainWindowStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		//TODO �������� ���������� ��������� ����������
		
		//�������� FXML-���� �����
		mainWindowLoader = new FXMLLoader();
		mainWindowLoader.setLocation(getClass().getResource("view/MainWindowView.fxml"));
			
		//��������� ���������� ���� � ������ Parent
		Parent mainView = mainWindowLoader.load();
		
		//�������� ������ �� ���������� ����������
		mainWindowCtrl = mainWindowLoader.getController();
		
		//�������� ��� �������� �����
		mainFileParcer = new FileParcer("e:/Saves"); //TODO �������� ������ �� ������ �� ���� ���������, � �� ��������� ��� ������
		mainFileParcer.scanConfFileFolder(); //��������� �����
		mainWindowCtrl.fillCmbConfFileSelect(mainFileParcer.showFilesInConfDirectory()); //��������� ������� ��� ���������� ����� � ������
		mainWindowCtrl.setMainWindowRef(this);
		
		//������ ����� �� ������ ����� � mainView
		Scene mainScene = new Scene(mainView);
		
		//��������� ����� � ���������� � �� ������
		mainWindowStage = new Stage();
		mainWindowStage.setScene(mainScene);
		mainWindowStage.setTitle("��������� ������������");
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
