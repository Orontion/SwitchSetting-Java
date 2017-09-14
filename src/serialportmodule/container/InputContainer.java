package serialportmodule.container;

import java.util.LinkedList;
import java.util.List;

//���������, ������� �������� ��� ����������, ������������ � ���������������� ����
public class InputContainer {
	
	private String allInputData; //��� �������� ����������. TODO ����������� �� ������ �������� ����������?
	private String lastTakedInputData; //��������� ���������� ���� ����������
	private List<InputEventListener> listenerList = new LinkedList<>(); //������ Listener-��
	
	//���������� ������ Listener-� =================================================================================================================
	public void addListener(InputEventListener inputListener){
		listenerList.add(inputListener);
	}
	
	//�������� Listner-a =============================================================================================================================
	public void removeListener(InputEventListener inputListener){
		listenerList.remove(inputListener);
	}
	
	//����� ������� ��� ��������� ����� ������ =================================================================================================================
	private void fireNewDataInputEvent(String arrivedData){
		InputEvent inpE = new InputEvent(this, arrivedData);
		for (InputEventListener tempListener : listenerList){
			tempListener.dataArrived(inpE);
		}
	}
	
	
	//��������� ������ =================================================================================================================
	public void addData(String newData){
		lastTakedInputData = newData;
		allInputData += newData;
		
		fireNewDataInputEvent(newData);
	}
	
	public String getAllInputData(){
		return allInputData;
	}

	public String getLastTakedInputData() {
		return lastTakedInputData;
	}
}
