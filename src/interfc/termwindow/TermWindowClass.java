package interfc.termwindow;

import confcore.ConfigurationCore;
import interfc.termwindow.view.TermWindowController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import serialportmodule.container.InputEvent;
import serialportmodule.container.InputEventListener;

public class TermWindowClass extends Application implements InputEventListener {
	//Loader ��� ������� � �����������
	FXMLLoader termWinLoader;
	//������ �� ���������� ����������
	TermWindowController termWinCtrl;
	//Stage ��� ����������� ���� ���������
	Stage termWinStage;
	//������ �� ���� ���������
	ConfigurationCore programCoreRef;
	
	public TermWindowClass(ConfigurationCore programCoreRef) {
		this.programCoreRef = programCoreRef;
		programCoreRef.addSerialPortInputListener(this);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		termWinLoader = new FXMLLoader(getClass().getResource("view/TermWindowView.fxml"));
		
		Parent termView = termWinLoader.load();
		
		//��������� ������ �� ������-���������� ����, � �������� ����� ������� ������ �� ������� ������-����
		termWinCtrl = termWinLoader.getController();
		termWinCtrl.setTermWindowRef(this);
		
		termWinCtrl.addDataToTerminal(programCoreRef.getAllSerialPortData());
		
		Scene termScene = new Scene(termView);
		
		termWinStage = new Stage();
		
		//��������� �������� ����
		termWinStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				programCoreRef.removeSerialPortInputListener(TermWindowClass.this); //������ ������ �� ������������ ������ �� ������ Listener-��
			}
		});
		termWinStage.setScene(termScene);
		termWinStage.setTitle("��������");
		termWinStage.initOwner(primaryStage);
		termWinStage.show();
	}
	
	public void closeWindow(){
		//�������� ������ ������. ����� .close() ������ �������� ����, � �� ���������.
		termWinStage.close();
		programCoreRef.removeSerialPortInputListener(this); //�������, �.�. ��� ������ .close() event �� ������������
	}
	
	@Override
	public void dataArrived(InputEvent inputE) {
		termWinCtrl.addDataToTerminal(inputE.getInputContents());
	}
	
	public static void main(String[] args) {
		launch(args);
	}


}
