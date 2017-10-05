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
	
	//�����������, ����������� ���������� ������ �� ������� �������� ���� � ���� ��������� ====================================================================
	public SettingsWindowClass(ConfigurationCore programCoreRef, MainWindowClass mainWindowRef) {
		this.programCoreRef = programCoreRef;
		this.mainWindowRef = mainWindowRef;
	}
	
	//����� ���� �������� ========================================================================================================================================
	@Override
	public void start(Stage primaryStage) throws Exception {
		//�������� view-����� �����
		stgWinLoader = new FXMLLoader(getClass().getResource("view/SettingsWindowView.fxml"));
		
		//��������� ���������� ���� � ������ Parent (������������ ������ ��� ���� ��������� �����)
		Parent stgView = stgWinLoader.load();
		
		//��������� ������ �� ������-���������� ����, � �������� ����� ������� ������ �� ������� ������-����
		stgWinCtrl = stgWinLoader.getController();
		stgWinCtrl.setSettingsWindowRef(this);
		
		//TODO ��������� try-catch ����������
		//������� ��������� combobox ���������� � ������� COM-�������
		try {
			stgWinCtrl.fillSerialPortList(programCoreRef.getSerialPortList());
		} catch (NoSerialPortsInSystemException e){
			//���� ��������� ����� ����������� - ��� � ����� � combobox-�
			stgWinCtrl.fillSerialPortList();
		} catch (Exception e) {
			//���� ���-�� ����� �� ���
			throw new SwitchSettingException("Unknown exception during SerialPortList getting!", e);
		}
		
		//���������� �� ����� ��� ���� � ����� � ����-�������, ������� � ������ ������ ������� � ����
		stgWinCtrl.setConfDirPath(programCoreRef.getConFileDir());
		
		
		//������ ����� �� ������ ����� � stgView
		Scene stgScene = new Scene(stgView);

		//��������� ����� � ���������� � �� ������
		stgWinStage = new Stage();
		stgWinStage.setScene(stgScene);
		stgWinStage.setTitle("��������� ����������");
		stgWinStage.initModality(Modality.WINDOW_MODAL);
		stgWinStage.initOwner(primaryStage);
		stgWinStage.show();
	}

	
	//����� ������� ������ ����� � ����������������� ������� =======================================================================
	public void chooseConfFileDir(){
		DirectoryChooser dirChooser = new DirectoryChooser(); 	//������ ����������� ����
		File newConfDir; 										//�.�. DirectoryChooser.showDialog() ���������� ��� File - ���������� ��������� ������
		
		newConfDir = dirChooser.showDialog(stgWinStage); //�������� ����� ����� �� ����������� ����
		
		//��������, ��� ���������� ���� �� ���� ������ �������. ���� ���� �������, � ������ newConfDir ����� null - ��������� � ��������� �� ��������.
		if (newConfDir != null){
			programCoreRef.setConFileDir(newConfDir.getAbsolutePath());
			stgWinCtrl.setConfDirPath(newConfDir.getAbsolutePath());
			mainWindowRef.refreshFileListCmb();
		}
	}

	//����� COM-����� ��� ������ ==================================================================================================
	public void chooseSerialPort(String serialPortName){
		programCoreRef.chooseSerialPort(serialPortName);
	}
	
	//����� �������� ����� ==================================================================================================
	public void chooseSerialPortSpeed(int chosedSpeed){
		programCoreRef.chooseSerialPortSpeed(chosedSpeed);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
