package interfc.termwindow.view;

import interfc.termwindow.TermWindowClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TermWindowController {
	@FXML
	private Button butClose;
	
	@FXML
	private void closeForm(){
		termWindowRef.closeWindow();
	}
	
	//—сылка на класс-окно
	public TermWindowClass termWindowRef;
	
	//‘ункци€ получени€ ссылки на класс-окно
	public void setTermWindowRef(TermWindowClass termWindowRef){
		this.termWindowRef = termWindowRef;
	}
	
}