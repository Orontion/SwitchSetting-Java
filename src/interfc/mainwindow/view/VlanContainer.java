package interfc.mainwindow.view;

//Объект-контейнер, содержащий VLAN и их настройки
public class VlanContainer {
	private String vlanName; 		//Имя VLAN-а
	private String vlanTgd; 		//Тэгированные порты
	private String vlanUtgd;      	//Нетэгированные порты
	private Boolean vlanIsCtrl;		//Является ли порт управляющим
	
	//Конструктор для создания нового VLAN
	public VlanContainer(String newVlanName, String newVlanTgd, String newVlanUtgd, Boolean newVlanIsCtrl){
		this.vlanName = newVlanName;
		this.vlanTgd = newVlanTgd;
		this.vlanUtgd = newVlanUtgd;
		this.vlanIsCtrl = newVlanIsCtrl;
	}
	
	public String getVlanName() {
		return vlanName;
	}
	public void setVlanName(String vlanName) {
		this.vlanName = vlanName;
	}
	public String getVlanTagged() {
		return vlanTgd;
	}
	public void setVlanTagged(String vlanTagged) {
		this.vlanTgd = vlanTagged;
	}
	public String getVlanUntagged() {
		return vlanUtgd;
	}
	public void setVlanUntagged(String vlanUntagged) {
		this.vlanUtgd = vlanUntagged;
	}
	public Boolean getVlanIsControl() {
		return vlanIsCtrl;
	}
	public void setVlanIsControl(Boolean vlanIsControl) {
		this.vlanIsCtrl = vlanIsControl;
	}
}
