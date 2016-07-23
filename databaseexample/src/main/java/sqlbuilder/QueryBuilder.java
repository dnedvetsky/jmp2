package sqlbuilder;

/**
 * A builder interface
 */
public interface QueryBuilder {
    public String build();

    public QueryBuilder from(String fromField);

    public QueryBuilder groupBy(String table, String column);

    public QueryBuilder orderBy(String table, String column);

    public QueryBuilder limit(int i);

    public QueryBuilder join(SQLQueryBuilder.JOINS joins, String table, String column, String table2, String column2);

}
