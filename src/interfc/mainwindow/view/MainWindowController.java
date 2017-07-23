package interfc.mainwindow.view;

import java.util.List;

//TODO Перевести диалоговые окна со Swing на JavaFX
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
		//Инициализация объектов для хранения данных
		vlanList = FXCollections.observableArrayList();				//Объект с данными о VLAN
		confFileObsList = FXCollections.observableArrayList();		//Список файлов конфигурации
		
		//Инициализация полей таблицы для отображения данных о VLAN
		colVlanName.setCellValueFactory(new PropertyValueFactory<VlanContainer,String>("vlanName"));
		colVlanTgd.setCellValueFactory(new PropertyValueFactory<VlanContainer,String>("vlanTagged"));
		colVlanUtgd.setCellValueFactory(new PropertyValueFactory<VlanContainer,String>("vlanUntagged"));
		colVlanIsControl.setCellValueFactory(new PropertyValueFactory<VlanContainer,Boolean>("vlanIsControl"));
		tableVlanStg.setItems(vlanList);
		
		cmbConfFileSelect.setItems(confFileObsList);
	}
	
	//Метод добавления данных о VLAN из полей в таблицу. В момент добавления производится проверка корректности данных
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
	
	//Метод активации кнопки удаления, когда происходит выбор строчки в таблице VLAN ====================
	@FXML
	private void butDeleteActivation(){
		//TODO Активация кнопки удаления не только при щелчке мыши
		if (tableVlanStg.getSelectionModel().getSelectedIndex() != -1){
			butDeleteRow.setDisable(false);
		}
	}
	
	//Метод удаления выбранного VLAN вместе с настройками из таблицы ====================================
	@FXML
	private void deleteRow(){
		vlanList.remove(tableVlanStg.getSelectionModel().getSelectedIndex());
		tableVlanStg.getSelectionModel().clearSelection();
		butDeleteRow.setDisable(true);
	}
	
	//Метод очистки всех значений, которые были введены =================================================
	@FXML
	private void clearAll(){
		if (JOptionPane.showConfirmDialog(null,"Подтверждение", "Очистить все настройки?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
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
	
	//Показ окна настроек====================================================================
	@FXML
	private void showSettingsWindow(){
		//Новый объект окна настроек
		SettingsWindowClass stgWindow = new SettingsWindowClass();
		
		//Показываем созданное окно
		try {
			stgWindow.start(mainWindowRef.getMainWindowStage());
		} catch (Exception e) {
			throw new RuntimeException("Cannot start settings window");
		}
	}
	
	//Показ окна терминала====================================================================
	@FXML
	private void showTermWindow(){
		//Новый объект окна терминала
		TermWindowClass termWindow = new TermWindowClass();
		
		//Показываем созданное окно
		try {
			termWindow.start(mainWindowRef.getMainWindowStage());
		} catch (Exception e) {
			throw new RuntimeException("Cannot start terminal window");
		}
	}
	
	//ObservableList объектов типа VlanContaner, хранит настройки по VLANам
	private ObservableList<VlanContainer> vlanList;
	
	//ObservableList со списком имён файлов конфигурации
	private ObservableList<String> confFileObsList; 
	
	//Ссылка на объект-окно
	private MainWindowClass mainWindowRef;
	
	//Получение ссылки на объект главного окна===============================================================
	public void setMainWindowRef(MainWindowClass mainWindowRef){
		this.mainWindowRef = mainWindowRef;
	}
	
	//Функция для проверки корректности вводимого VLAN ======================================================
	private Boolean vlanInputCheck(){
		//Проверка имени VLAN
		if (txtVlanName.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Не введен номер VLAN!");
			return false;
		}
		
		//Проверка корректности ввода в поле имени VLAN
		int TestInt = 0;
		try {
			TestInt = Integer.parseInt(txtVlanName.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "VLAN должен быть числом в диапазоне от 0 до 65535!");
			return false;
		}
		
		if (TestInt < 0 || TestInt > 65535){
			JOptionPane.showMessageDialog(null, "VLAN должен быть числом в диапазоне от 0 до 65535!");
			return false;
		}
		
		//Проверка повторяющихся VLAN
		for (VlanContainer tmpCont : vlanList){
			if (txtVlanName.getText().equals(tmpCont.getVlanName())){
				JOptionPane.showMessageDialog(null, "Нельзя вводить повторяющиеся VLAN!");
				return false;
			}
		}
		
		//Проверка, создаваемый VLAN имеет настроенные порты
		if (txtVlanTgd.getText().isEmpty() && txtVlanUtgd.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Должен быть настроен хотя бы один тэгированный или нетэгированный порт!");
			return false;
		}
	
		//Проверка попытки ввести второй управляющий VLAN
		if (chkbxIsControl.isSelected()){
			for (VlanContainer tmpCont : vlanList){
				if (tmpCont.getVlanIsControl()){
					JOptionPane.showMessageDialog(null, "Можно настроить только один управляющий VLAN!");
					return false;
				}
			}
		}
	
		//TODO Парсер портов
		return true;
	}
	
	//Метод добавления содержимого в ComboBox для выбора файла/свитча для настройки ========================================================
	public void fillCmbConfFileSelect(List<String> confFileList){
		confFileObsList.clear();
		confFileObsList.addAll(confFileList);
	}
}

