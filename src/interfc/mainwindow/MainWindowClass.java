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
		mainWindowCtrl.startForm(mainFileParcer.showFilesInConfDirectory()); //��������� ������� ��� ���������� ����� � ������
		
		//������ ����� �� ������ ����� � mainView
		Scene mainScene = new Scene(mainView);
		
		//��������� ����� � ���������� � �� 
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("��������� ������������");
		//TODO �������� �� ��������� �������� ������������ ������� �� ������������ � ��������� ��������� Pine � ����� ����� FXML
		primaryStage.setMinHeight(mainScene.getHeight());
		primaryStage.setMinWidth(mainScene.getWidth());
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
