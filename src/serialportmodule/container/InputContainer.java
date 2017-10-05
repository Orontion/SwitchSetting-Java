package serialportmodule.container;

import java.util.LinkedList;
import java.util.List;

//Контейнер, который содержит всю информацию, отправляемую в последовательный порт
public class InputContainer {
	
	//TODO Проверить ограничение на размер введённой информации в переменную String
	private String allInputData; //Вся введённая информация. 
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
	
	//Метод вызова события при получении новых данных =================================================================================================================
	private void fireNewDataInputEvent(String arrivedData){
		InputEvent inpE = new InputEvent(this, arrivedData);
		for (InputEventListener tempListener : listenerList){
			tempListener.dataArrived(inpE);
		}
	}
	
	
	//Получение данных =================================================================================================================================
	public void addData(String newData){
		lastTakedInputData = newData;
		allInputData += newData;
		
		//Вызов событий
		fireNewDataInputEvent(newData);
	}
	
	//Getter всех полученных данных =================================================================================================================================
	public String getAllInputData(){
		return allInputData;
	}

	//Getter последних полученных данных =================================================================================================================================
	public String getLastTakedInputData() {
		return lastTakedInputData;
	}
}
