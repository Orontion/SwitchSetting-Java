package serialportmodule.container;

import java.util.LinkedList;
import java.util.List;

//Контейнер, который содержит всю информацию, отправляемую в последовательный порт
public class InputContainer {
	
	private String allInputData; //Вся введённая информация. TODO Ограничение на размер введённой информации?
	private String lastTakedInputData; //Последний полученный блок информации
	private List<InputEventListener> listenerList = new LinkedList<>(); //Список Listener-ов
	
	//Добавление нового Listener-а =================================================================================================================
	public void addListener(InputEventListener inputListener){
		listenerList.add(inputListener);
	}
	
	//Удаление Listner-a =============================================================================================================================
	public void removeListener(InputEventListener inputListener){
		listenerList.remove(inputListener);
	}
	
	//Вызов события при получении новых данных =================================================================================================================
	private void fireNewDataInputEvent(String arrivedData){
		InputEvent inpE = new InputEvent(this, arrivedData);
		for (InputEventListener tempListener : listenerList){
			tempListener.dataArrived(inpE);
		}
	}
	
	
	//Получение данных =================================================================================================================
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
