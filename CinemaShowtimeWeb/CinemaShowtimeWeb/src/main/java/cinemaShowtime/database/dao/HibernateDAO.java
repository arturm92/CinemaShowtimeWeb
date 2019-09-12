package cinemaShowtime.database.dao;

import java.util.HashMap;
import java.util.List;

public interface HibernateDAO <T>  {

	public Integer insert(T entity);

	public void update(T entity);

	public void delete(T entity);

	public List<T> findAll();

	public T find(HashMap<String, String> queryParamMap);
}
