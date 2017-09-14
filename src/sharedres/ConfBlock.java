package sharedres;
import java.util.ArrayList;
import java.util.List;

//Класс-контейнер для сохранения прочитанных из файла блоков конфигурирования свитча
public class ConfBlock {
	//Стандартное значение, по которому будет происходить повторение блока конфигурирования
	public static final String DEFAULT_ITERATED_BY = "NONE";
	
	//Переменная, хранящая группу настроек, по количеству которых будет произведено повторение блока конфигурирования
	private String iteratedBy;
	
	//Список, содержащий строку конфигурирования, разбитую на элементы для быстрой сборки в переменную типа String с заменой переменных на значения настроек
	private List<List<String>> stgLines = new ArrayList<>();
	
	//Переменная, хранящая количество строк конфигурирования в объекте
	private int countOfLines = 0;
	
	
	public ConfBlock(String iteratedBy){
		this.iteratedBy = iteratedBy;
	}
	
	public ConfBlock() {
		this(DEFAULT_ITERATED_BY);
	}
	
	public int countOfLines(){
		return countOfLines;
	}
	
	public String iteratedBy(){
		return iteratedBy;
	}
	
	public void addNewLine(){
		stgLines.add(new ArrayList<String>());
		countOfLines++;
	}
	
	public void addNewOperator(String operatorToAdd){
		stgLines.get(countOfLines - 1).add(operatorToAdd);
	}
	
	public List<String> getLine (int lineIndex){
		return stgLines.get(lineIndex);
	}
}
