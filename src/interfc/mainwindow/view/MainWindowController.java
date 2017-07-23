package interfc.mainwindow.view;

import java.util.List;

//TODO ��������� ���������� ���� �� Swing �� JavaFX
import javax.swing.JOptionPane;

import interfc.mainwindow.MainWindowClass;
import interfc.settingswindow.SettingsWindowClass;
import interfc.termwindow.TermWindowClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainWindowController {
	@FXML
	private MenuItem mItShowStgWindow;
	
	@FXML
	private MenuItem mItShowTermWindow;
	
	@FXML
	private ComboBox<String> cmbConfFileSelect;
	
	@FXML
	private TextField txtIP;
	
	@FXML
	private TextField txtNetmask;
	
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
	
	@FXML
	private Button butDeleteRow;
	
	@FXML
	private Button butShowTermWindow;
	
	@FXML
	private void initialize(){
		//������������� �������� ��� �������� ������
		vlanList = FXCollections.observableArrayList();				//������ � ������� � VLAN
		confFileObsList = FXCollections.observableArrayList();		//������ ������ ������������
		
		//������������� ����� ������� ��� ����������� ������ � VLAN
		colVlanName.setCellValueFactory(new PropertyValueFactory<VlanContainer,String>("vlanName"));
		colVlanTgd.setCellValueFactory(new PropertyValueFactory<VlanContainer,String>("vlanTagged"));
		colVlanUtgd.setCellValueFactory(new PropertyValueFactory<VlanContainer,String>("vlanUntagged"));
		colVlanIsControl.setCellValueFactory(new PropertyValueFactory<VlanContainer,Boolean>("vlanIsControl"));
		tableVlanStg.setItems(vlanList);
		
		cmbConfFileSelect.setItems(confFileObsList);
	}
	
	//����� ���������� ������ � VLAN �� ����� � �������. � ������ ���������� ������������ �������� ������������ ������
	@FXML
	private void addVlanToTable(){
		if (vlanInputCheck()){
			vlanList.add(new VlanContainer(txtVlanName.getText(), 
											txtVlanTgd.getText(), 
											txtVlanUtgd.getText(), 
											chkbxIsControl.isSelected()));
			txtVlanName.clear();
			txtVlanTgd.clear();
			txtVlanUtgd.clear();
			chkbxIsControl.setSelected(false);
		}
	}
	
	//����� ��������� ������ ��������, ����� ���������� ����� ������� � ������� VLAN ====================
	@FXML
	private void butDeleteActivation(){
		//TODO ��������� ������ �������� �� ������ ��� ������ ����
		if (tableVlanStg.getSelectionModel().getSelectedIndex() != -1){
			butDeleteRow.setDisable(false);
		}
	}
	
	//����� �������� ���������� VLAN ������ � ����������� �� ������� ====================================
	@FXML
	private void deleteRow(){
		vlanList.remove(tableVlanStg.getSelectionModel().getSelectedIndex());
		tableVlanStg.getSelectionModel().clearSelection();
		butDeleteRow.setDisable(true);
	}
	
	//����� ������� ���� ��������, ������� ���� ������� =================================================
	@FXML
	private void clearAll(){
		if (JOptionPane.showConfirmDialog(null,"�������������", "�������� ��� ���������?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			txtIP.clear();
			txtNetmask.clear();
			txtVlanName.clear();
			txtVlanTgd.clear();
			txtVlanUtgd.clear();
			chkbxIsControl.setSelected(false);
			vlanList.clear();
			butDeleteRow.setDisable(true);
		}
	}
	
	//����� ���� ��������====================================================================
	@FXML
	private void showSettingsWindow(){
		//����� ������ ���� ��������
		SettingsWindowClass stgWindow = new SettingsWindowClass();
		
		//���������� ��������� ����
		try {
			stgWindow.start(mainWindowRef.getMainWindowStage());
		} catch (Exception e) {
			throw new RuntimeException("Cannot start settings window");
		}
	}
	
	//����� ���� ���������====================================================================
	@FXML
	private void showTermWindow(){
		//����� ������ ���� ���������
		TermWindowClass termWindow = new TermWindowClass();
		
		//���������� ��������� ����
		try {
			termWindow.start(mainWindowRef.getMainWindowStage());
		} catch (Exception e) {
			throw new RuntimeException("Cannot start terminal window");
		}
	}
	
	//ObservableList �������� ���� VlanContaner, ������ ��������� �� VLAN��
	private ObservableList<VlanContainer> vlanList;
	
	//ObservableList �� ������� ��� ������ ������������
	private ObservableList<String> confFileObsList; 
	
	//������ �� ������-����
	private MainWindowClass mainWindowRef;
	
	//��������� ������ �� ������ �������� ����===============================================================
	public void setMainWindowRef(MainWindowClass mainWindowRef){
		this.mainWindowRef = mainWindowRef;
	}
	
	//������� ��� �������� ������������ ��������� VLAN ======================================================
	private Boolean vlanInputCheck(){
		//�������� ����� VLAN
		if (txtVlanName.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "�� ������ ����� VLAN!");
			return false;
		}
		
		//�������� ������������ ����� � ���� ����� VLAN
		int TestInt = 0;
		try {
			TestInt = Integer.parseInt(txtVlanName.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "VLAN ������ ���� ������ � ��������� �� 0 �� 65535!");
			return false;
		}
		
		if (TestInt < 0 || TestInt > 65535){
			JOptionPane.showMessageDialog(null, "VLAN ������ ���� ������ � ��������� �� 0 �� 65535!");
			return false;
		}
		
		//�������� ������������� VLAN
		for (VlanContainer tmpCont : vlanList){
			if (txtVlanName.getText().equals(tmpCont.getVlanName())){
				JOptionPane.showMessageDialog(null, "������ ������� ������������� VLAN!");
				return false;
			}
		}
		
		//��������, ����������� VLAN ����� ����������� �����
		if (txtVlanTgd.getText().isEmpty() && txtVlanUtgd.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "������ ���� �������� ���� �� ���� ������������ ��� �������������� ����!");
			return false;
		}
	
		//�������� ������� ������ ������ ����������� VLAN
		if (chkbxIsControl.isSelected()){
			for (VlanContainer tmpCont : vlanList){
				if (tmpCont.getVlanIsControl()){
					JOptionPane.showMessageDialog(null, "����� ��������� ������ ���� ����������� VLAN!");
					return false;
				}
			}
		}
	
		//TODO ������ ������
		return true;
	}
	
	//����� ���������� ����������� � ComboBox ��� ������ �����/������ ��� ��������� ========================================================
	public void fillCmbConfFileSelect(List<String> confFileList){
		confFileObsList.clear();
		confFileObsList.addAll(confFileList);
	}
}

