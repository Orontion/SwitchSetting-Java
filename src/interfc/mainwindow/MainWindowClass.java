package interfc.mainwindow;

import confcore.ConfigurationCore;
import interfc.mainwindow.view.MainWindowController;
import interfc.settingswindow.SettingsWindowClass;
import interfc.termwindow.TermWindowClass;
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
		
		//�������� ������ �� ���������� ���������� � ��� ��� ������ �� ���� ������
		mainWindowCtrl = mainWindowLoader.getController();
		mainWindowCtrl.setMainWindowRef(this);
		
		//�������� ��� �������� �����
		refreshFileListCmb();
		
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
	
	//����� ����������� ���� � ����������� =======================================================================
	public void showSettingsWindow(){
		//����� ������ ���� ��������
		SettingsWindowClass stgWindow = new SettingsWindowClass(programCore, this);

		//���������� ��������� ����
		try {
			stgWindow.start(mainWindowStage);
		} catch (Exception e) {
			throw new RuntimeException("Cannot start settings window", e);
		}
	}
	
	//����� ����������� ���� ��������� ===========================================================================
	public void showTermWindow(){
		//����� ������ ���� ���������
		TermWindowClass termWindow = new TermWindowClass(programCore);
		
		//���������� ��������� ����
		try {
			termWindow.start(mainWindowStage);
		} catch (Exception e) {
			throw new RuntimeException("Cannot start terminal window");
		}
	}
	
	//����� ���������� ����������� ComboBox-� �� ������� ���������������� ������ =================================
	public void refreshFileListCmb(){
		mainWindowCtrl.fillCmbConfFileSelect(programCore.getConfFilesList());
	}

	public static void main(String[] args) {
		launch(args);
	}
}
