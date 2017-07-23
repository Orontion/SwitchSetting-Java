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
	//Loader ��� ������� � �����������
	FXMLLoader mainWindowLoader;
	//������ �� ���������� ����������
	MainWindowController mainWindowCtrl;
	//������-����, �������� � ������� �������� ���� 
	ConfigurationCore programCore = new ConfigurationCore();
	//Stage ��� ����������� ����
	Stage mainWindowStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		//TODO �������� ���������� ��������� ����������
		
		//�������� ������ �� ���� � ����
		programCore.setMainWindow(this);
		
		//�������� FXML-���� �����
		mainWindowLoader = new FXMLLoader();
		mainWindowLoader.setLocation(getClass().getResource("view/MainWindowView.fxml"));
			
		//��������� ���������� ���� � ������ Parent
		Parent mainView = mainWindowLoader.load();
		
		//�������� ������ �� ���������� ����������
		mainWindowCtrl = mainWindowLoader.getController();
		
		//�������� ��� �������� �����
		mainWindowCtrl.fillCmbConfFileSelect(programCore.getConfFilesList()); //�������� ������ ������
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
	
	
	//����� ����������� ���� � ����������� ===================================================
	public void showSettingsWindow(){
		//����� ������ ���� ��������
		SettingsWindowClass stgWindow = new SettingsWindowClass(programCore);
		
		//���������� ��������� ����
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
