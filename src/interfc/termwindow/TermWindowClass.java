package interfc.termwindow;

import confcore.ConfigurationCore;
import interfc.termwindow.view.TermWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TermWindowClass extends Application {
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
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		termWinLoader = new FXMLLoader(getClass().getResource("view/TermWindowView.fxml"));
		
		Parent termView = termWinLoader.load();
		
		//��������� ������ �� ������-���������� ����, � �������� ����� ������� ������ �� ������� ������-����
		termWinCtrl = termWinLoader.getController();
		termWinCtrl.setTermWindowRef(this);
		
		Scene termScene = new Scene(termView);
		
		termWinStage = new Stage();
		termWinStage.setScene(termScene);
		termWinStage.setTitle("��������");
		termWinStage.initOwner(primaryStage);
		termWinStage.show();
	}

	public void closeWindow(){
		termWinStage.close();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
