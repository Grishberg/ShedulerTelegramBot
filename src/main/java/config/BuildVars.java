package config;

/**
 * Created by grishberg on 06.09.16.
 */
public class BuildVars {
    public static final Boolean DEBUG = true;
    public static final String PATH_TO_LOGS = "./";
    public static final String LINK_DB = "jdbc:mysql://[IP_OF_YOU_MYSQL_SERVER]:3306/[DATABASE]?useUnicode=true&characterEncoding=UTF-8";
    public static final String CONTROLLER_DB = "com.mysql.jdbc.Driver";
    public static final String USER_DB = "[YOUR_DB_USERNAME]";
    public static final String PASSWORD_DB = "[YOUR_SECRET_DB_PASSWORD]";
}
