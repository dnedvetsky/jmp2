package sqlbuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * This is an abstract query builder class
 */
public abstract class SQLQueryBuilder implements QueryBuilder {
    private ArrayList<String> FROM = new ArrayList<>();
    private ArrayList<String> SELECT = new ArrayList<>();
    private ArrayList<String> JOIN = new ArrayList<>();
    private ArrayList<String> GROUPBY = new ArrayList<>();
    private ArrayList<String> ORDERBY = new ArrayList<>();
    private String LIMITER;
    private int limitValue;
    private Map<String, String> SHORTLINKS = new HashMap<>();
    private SqlQuery sqlQuery = new SqlQuery();

    /**
     * Used to set JOINER type
     */
    public enum JOINS {
        INNER_JOIN {
            @Override
            public String toString() {
                return "JOIN";
            }
        },
        LEFT_JOIN {
            @Override
            public String toString() {
                return "LEFT JOIN";
            }
        }

    }

    /**
     * Used to get aggregation type
     */
    public enum AGGREGATE {
        SUM {
            @Override
            public String toString() {
                return "SUM";
            }
        }
    }

    /**
     * Constructor. Uses top to set type of limiter depending on Database type
     *
     * @param top - limiter type
     */
     SQLQueryBuilder(String top) {
        LIMITER = top;
    }

    /**
     * Specified selection
     *
     * @param table  table
     * @param column column
     */
    public SQLQueryBuilder output(String table, String column) {
        SELECT.add(buildTableColumn(table, column));
        return this;
    }

    /**
     * Specified select. Method uses {@link MsSQLQueryBuilder.AGGREGATE} to get aggregation type
     *
     * @param table     table
     * @param column    column
     * @param aggregate aggregation type
     */
    public SQLQueryBuilder output(String table, String column, MsSQLQueryBuilder.AGGREGATE aggregate) {
        SELECT.add(String.format("%s(%s)", aggregate, buildTableColumn(table, column)));
        return this;
    }

    /**
     * Specify from which table the actions are performed
     *
     * @param fromTable - from table value
     */
    public SQLQueryBuilder from(String fromTable) {
        addTableValueToShortLink(fromTable);
        FROM.add(String.format("%s %s", fromTable, getTableValueFromShortLink(fromTable)));
        return this;
    }

    /**
     * Used to group results by table.colum pattern
     *
     * @param table  table (will be changed to shortcut)
     * @param column column
     */
    public SQLQueryBuilder groupBy(String table, String column) {
        GROUPBY.add(buildTableColumn(table, column));
        return this;
    }

    /**
     * Used to sort results in required order
     *
     * @param table  - table name
     * @param column - column name
     */
    public SQLQueryBuilder orderBy(String table, String column) {
        ORDERBY.add(buildTableColumn(table, column));
        return this;
    }

    /**
     * Sets limiter to a table;
     *
     * @param i - number of rows displayed
     */
    public SQLQueryBuilder limit(int i) {
        this.limitValue = i;
        return this;
    }

    /**
     * A Join method - use it to build query in order to join some tables
     *
     * @param joins   joining type. {@link MsSQLQueryBuilder.JOINS} for more reference
     * @param table   table no 1
     * @param column  column in table no 1
     * @param table2  table no 2
     * @param column2 column in table no 2
     */
    public SQLQueryBuilder join(MsSQLQueryBuilder.JOINS joins, String table, String column, String table2, String column2) {
        addTableValueToShortLink(table2);
        String mainJoinPart = String.format("%s %s %s", joins, table2, getTableValueFromShortLink(table2));
        String joinEqualsPart = String.format("%s.%s = %s.%s", getTableValueFromShortLink(table), column,
                getTableValueFromShortLink(table2), column2);
        JOIN.add(String.format("%s ON %s", mainJoinPart, joinEqualsPart));
        return this;
    }

    /**
     * Parses HashMap and acquires shortling from the according to a key value
     *
     * @param table - table to get short link to it
     * @return string
     */
    private String getTableValueFromShortLink(String table) {
        String shortlink;
        if (SHORTLINKS.containsKey(table))
            shortlink = SHORTLINKS.get(table);
        else throw new NullPointerException("There is no such table '" + table + "' to get short link for");
        return shortlink;
    }

    /**
     * Method is used to generate shortlinks to tables that is being added to a query
     * Builds it according to a first unique values passed;
     *
     * @param table table
     */
    private void addTableValueToShortLink(String table) {
        String fieldShortLink;
        int i = 0;
        if (SHORTLINKS.containsKey(table))
            return;
        do {
            fieldShortLink = table.substring(0, ++i);
            if (i >= table.length()) {
                fieldShortLink += i;
            }
        } while (SHORTLINKS.containsValue(fieldShortLink));
        SHORTLINKS.put(table, fieldShortLink);
    }

    /**
     * This is used to build Table.Column values; Uses pre-generated shortlink to get required value;
     *
     * @param table  - table name;
     * @param column - column name;
     * @return string
     */
    private String buildTableColumn(String table, String column) {
        return String.format("%s.%s", getTableValueFromShortLink(table), column);
    }

    /**
     * Technically a universal method that acquires delimeter and values; Could be used without other methods but
     * eventually feels safer to have one.
     *
     * @param arrayList - array with values;
     * @param delimeter - delimeter;
     * @return string
     */
    private String joinByDelimeter(ArrayList<String> arrayList, String delimeter) {
        StringJoiner tableColumnsJoin = new StringJoiner(delimeter);
        for (String listElement : arrayList) {
            tableColumnsJoin.add(listElement);
        }
        return tableColumnsJoin.toString();
    }

    /**
     * Used to join columns or tables - with , delimeter
     *
     * @param arrayList - columns to join
     * @return string
     */
    private String joinColumns(ArrayList<String> arrayList) {
        return arrayList.isEmpty() ? "" : joinByDelimeter(arrayList, ", ");
    }

    /**
     * Returns null if value is empty; Used to actually ignore all missing fields from SqlQuery
     *
     * @param formatter - SQL formatter
     * @param joins     - values to join under a formatter
     * @return String with ignoring empty data
     */
    private String ignoreEmpty(String formatter, ArrayList<String> joins) {
        String joinedData = joinColumns(joins);
        return joinedData.isEmpty() ? null : String.format(formatter, joinedData);
    }

    /**
     * Jins join toghether in case new values are being added
     *
     * @param join array of joins to join
     * @return joined joins
     */
    private String joinJoins(ArrayList<String> join) {
        return joinByDelimeter(join, "\r\n");
    }

    /**
     * Method used to build SQL query - it fills SqlQuery object with data and then parses it, returning SQL statement
     *
     * @return String
     */
    public String build() {
        sqlQuery.setSELECT(ignoreEmpty("SELECT %s", SELECT));
        sqlQuery.setFROM(ignoreEmpty("FROM %s", FROM));
        sqlQuery.setJOIN(joinJoins(JOIN));
        sqlQuery.setGROUPBY(ignoreEmpty("GROUP BY %s", GROUPBY));
        sqlQuery.setORDERBY(ignoreEmpty("ORDER BY %s ASC", ORDERBY));
        sqlQuery.setLIMITER(String.format("%s %d", LIMITER, limitValue));

        return generateCorrectBuildedSQLQuery(sqlQuery);
    }

    /**
     * Used to generate accurate string - without empty values and etc.
     *
     * @param sqlQuery - object of SQL query class from which values is acquired
     * @return string query
     */
    private String generateCorrectBuildedSQLQuery(SqlQuery sqlQuery) {
        StringJoiner joiner = new StringJoiner("\n\r");
        ArrayList<String> sqlQueryValues = new ArrayList<>();
        sqlQueryValues.add(sqlQuery.getSELECT());
        sqlQueryValues.add(sqlQuery.getFROM());
        sqlQueryValues.add(sqlQuery.getJOIN());
        sqlQueryValues.add(sqlQuery.getGROUPBY());
        sqlQueryValues.add(sqlQuery.getORDERBY());
        sqlQueryValues.add(sqlQuery.getLIMITER());
        for (String sqlValue : sqlQueryValues) {
            if (sqlValue != null && !sqlValue.isEmpty()) joiner.add(sqlValue);
        }
        return joiner.toString();
    }
}
