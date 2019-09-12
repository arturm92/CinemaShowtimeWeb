package cinemaShowtime.utils;

public class HibernateSetting {

	public static final String DIALECT = "org.hibernate.dialect.PostgreSQL10Dialect";
	public static final String DRIVER_CLASS = "org.postgresql.Driver";
	public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	public static final String USERNAME = "postgres";
	public static final String PASSWORD = "root";
	public static final String POOL_SIZE = "10";
	public static final String SHOW_SQL = "true";
	public static final String SESSION_CONTEXT = "thread";
	
}
