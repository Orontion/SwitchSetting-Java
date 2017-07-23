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

public class SettingsWindowClass extends Application {
	//Loader ��� ������� � �����������
	FXMLLoader stgWinLoader;
	//������ �� ���������� ����������
	SettingsWindowController stgWinCtrl;
	//Stage ��� ����������� ���� ��������
	Stage stgWinStage;
	//������ �� ���� ���������
	ConfigurationCore programCoreRef;
	
	//TODO ��������� �������� ���������� � ����� ����������, �� ��������� ������ �� ������� ����
	//������ �� ������� ����
	MainWindowClass mainWindowRef;
	
	public SettingsWindowClass(ConfigurationCore programCoreRef, MainWindowClass mainWindowRef) {
		this.programCoreRef = programCoreRef;
		this.mainWindowRef = mainWindowRef;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stgWinLoader = new FXMLLoader(getClass().getResource("view/SettingsWindowView.fxml"));
		
		Parent stgView = stgWinLoader.load();
		
		stgWinCtrl = stgWinLoader.getController();
		stgWinCtrl.setSettingsWindowRef(this);
		
		stgWinCtrl.fillSerialPortList(programCoreRef.getSerialPortList());
		stgWinCtrl.setConfDirPath(programCoreRef.getConFileDir());
		
		Scene stgScene = new Scene(stgView);
		
		stgWinStage = new Stage();
		stgWinStage.setScene(stgScene);
		stgWinStage.setTitle("��������� ����������");
		stgWinStage.initModality(Modality.WINDOW_MODAL);
		stgWinStage.initOwner(primaryStage);
		stgWinStage.show();
	}

	//����� ������� ������ ����� � ����������������� �������
	public void chooseConfFileDir(){
		DirectoryChooser dirChooser = new DirectoryChooser();
		File newConfDir;
		
		newConfDir = dirChooser.showDialog(stgWinStage);
		
		if (newConfDir != null){
			programCoreRef.setConFileDir(newConfDir.getAbsolutePath());
			stgWinCtrl.setConfDirPath(newConfDir.getAbsolutePath());
			mainWindowRef.refreshFileListCmb();
		}
	}

	public void chooseSerialPort(String serialPortName){
		programCoreRef.chooseSerialPort(serialPortName);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
