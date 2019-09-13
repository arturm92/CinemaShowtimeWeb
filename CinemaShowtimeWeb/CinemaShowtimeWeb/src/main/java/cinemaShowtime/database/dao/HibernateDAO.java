package cinemaShowtime.database.dao;

import java.util.HashMap;
import java.util.List;

public interface HibernateDAO <T>  {

	public Integer insert(T entity);

	public void update(T entity);

	public void delete(T entity);

	public List<T> findList(HashMap<String, Object> queryParamMap);

	public T find(HashMap<String, Object> queryParamMap);
}
