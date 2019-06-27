package model.json.complex;

import java.util.List;

public interface JsonList<T> {
	
	public List<T> getList();
	
	public void setList(List<T> list);
	
	public void showAllElements();
	
}
