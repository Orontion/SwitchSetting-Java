package sharedres;
import java.util.ArrayList;
import java.util.List;

//�����-��������� ��� ���������� ����������� �� ����� ������ ���������������� ������
public class ConfBlock {
	//����������� ��������, �� �������� ����� ����������� ���������� ����� ����������������
	public static final String DEFAULT_ITERATED_BY = "NONE";
	
	//����������, �������� ������ ��������, �� ���������� ������� ����� ����������� ���������� ����� ����������������
	private String iteratedBy;
	
	//������, ���������� ������ ����������������, �������� �� �������� ��� ������� ������ � ���������� ���� String � ������� ���������� �� �������� ��������
	private List<List<String>> stgLines = new ArrayList<>();
	
	//����������, �������� ���������� ����� ���������������� � �������
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
