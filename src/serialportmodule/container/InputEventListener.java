package serialportmodule.container;

//Интерфейс для объектов, которые будут Listener-ами событий от InputContainer-а
public interface InputEventListener {
	public void dataArrived(InputEvent inputE);
}
