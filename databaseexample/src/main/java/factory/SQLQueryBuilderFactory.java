package factory;

import sqlbuilder.*;

/**
 * SQL Query builder factory - basic factory used to get required builder
 */
public class SQLQueryBuilderFactory {
    public enum DB {
        MYSQL("LIMIT"), MSSQL("TOP"), ORACLE("RowNum");
        private final String limit;

        DB(String limit) {
            this.limit = limit;
        }

        public String getValue() {
            return limit;
        }
    }

    /**
     * Creates instance of MsSQLQueryBuilder with specified LIMITER;
     * It is possible to return exact class representation of MySQL Oracle or MsSQL just by implementing inheritance;
     * @param factoryType
     * @return
     */
    public static SQLQueryBuilder createSqlQueryBuilder(DB factoryType) {
        SQLQueryBuilder factoredBuilder = null;
        switch (factoryType) {
            case MSSQL:
                factoredBuilder = new MsSQLQueryBuilder();
                break;
            case MYSQL:
                factoredBuilder = new MySQLQueryBuilder();
                break;
            case ORACLE:
                factoredBuilder = new OracleQueryBuilder();
                break;
        }
        return factoredBuilder;
    }
}
