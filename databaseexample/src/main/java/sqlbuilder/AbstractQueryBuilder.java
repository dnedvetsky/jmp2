package sqlbuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is an abstract query builder class
 */
public abstract class AbstractQueryBuilder implements QueryBuilder {
    ArrayList<String> FROM = new ArrayList<>();
    ArrayList<String> SELECT = new ArrayList<>();
    ArrayList<String> JOIN = new ArrayList<>();
    ArrayList<String> GROUPBY = new ArrayList<>();
    ArrayList<String> ORDERBY = new ArrayList<>();
    String LIMITER;
    int limitValue;
    Map<String, String> SHORTLINKS = new HashMap<>();
    SqlQuery sqlQuery;


    abstract public String build();

    abstract public QueryBuilder from(String fromField);

    abstract public QueryBuilder groupBy(String table, String column);

    abstract public QueryBuilder orderBy(String table, String column);

    abstract public QueryBuilder limit(int i);

    abstract public QueryBuilder join(SQLQueryBuilder.JOINS joins, String table, String column, String table2, String column2);

}
