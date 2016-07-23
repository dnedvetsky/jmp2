package sqlbuilder;

import factory.SQLQueryBuilderFactory;

/**
 * Used to help with building queries; DB type can be specified - helpful in output;
 */
public class BuilderHelper {
    private SQLQueryBuilder msSqlQueryBuilder;

    public BuilderHelper(SQLQueryBuilderFactory.DB db) {
        this.msSqlQueryBuilder = SQLQueryBuilderFactory.createSqlQueryBuilder(db);
    }

    public String buildRequiredSQL() {
        return msSqlQueryBuilder.from("Order")
                .join(MsSQLQueryBuilder.JOINS.INNER_JOIN, "Order", "CustomerId", "Customer", "Id")
                .join(MsSQLQueryBuilder.JOINS.LEFT_JOIN, "Customer", "CityId", "City", "Id")
                .output("Order", "TotalPrice", MsSQLQueryBuilder.AGGREGATE.SUM)
                .output("Customer", "FirstName")
                .output("Customer", "LastName")
                .output("City", "Name")
                .groupBy("Customer", "FirstName")
                .groupBy("Customer", "LastName")
                .groupBy("City", "Name")
                .orderBy("Customer", "LastName")
                .limit(5).build();
    }

}
