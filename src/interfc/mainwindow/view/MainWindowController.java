package interfc.mainwindow.view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainWindowController {
	@FXML
	private ComboBox<String> cmbConfFileSelect;
	
	@FXML
	private TextField txtVlanName;
	
	@FXML
	private TextField txtVlanTgd;
	
	@FXML
	private TextField txtVlanUtgd;
	
	@FXML
	private CheckBox chkbxIsControl;
	
	@FXML
	private TableView<VlanContainer> tableVlanStg;
	
	@FXML
	private TableColumn<VlanContainer, String> colVlanName;
	
	@FXML
	private TableColumn<VlanContainer, String> colVlanTgd;
	
	@FXML
	private TableColumn<VlanContainer, String> colVlanUtgd;
	
	@FXML
	private TableColumn<VlanContainer, Boolean> colVlanIsControl;
	
	private ObservableList<VlanContainer> vlanList;
	
	private ObservableList<String> confFileObsList;
	
	@FXML
	private void initialize(){
		vlanList = FXCollections.observableArrayList();
		
		colVlanName.setCellValueFactory(new PropertyValueFactory<VlanContainer,String>("vlanName"));
		colVlanTgd.setCellValueFactory(new PropertyValueFactory<VlanContainer,String>("vlanTagged"));
		colVlanUtgd.setCellValueFactory(new PropertyValueFactory<VlanContainer,String>("vlanUntagged"));
		colVlanIsControl.setCellValueFactory(new PropertyValueFactory<VlanContainer,Boolean>("vlanIsControl"));
		tableVlanStg.setItems(vlanList);
		
	}
	
	public void startForm(List<String> confFileList){
		confFileObsList = FXCollections.observableArrayList();
		
		this.fillCmbConfFileSelect(confFileList);
		
		cmbConfFileSelect.setItems(confFileObsList);
	}
	
	public void fillCmbConfFileSelect(List<String> confFileList){
		confFileObsList.clear();
		confFileObsList.addAll(confFileList);
	}
	
	@FXML
	private void addVlanToTable(){
		//TODO Запилить обработчик ввода (пустые поля, больше одного управляющего VLAN и т.п.)
//		if (txtVlanName.textProperty().isEmpty().get()){
//			txtVlanName.setText("Works!");
//		}
		vlanList.add(new VlanContainer(txtVlanName.getText(), 
										txtVlanName.getText(), 
										txtVlanName.getText(), 
										chkbxIsControl.isSelected()));
	}
	
//	private void addDataToVlanCont(String newVlanName, String newVlanTgd, String newVlanUtgd, Boolean newVlanIsCtrl){
//		
//	}
}
