package example;

import factory.SQLQueryBuilderFactory;
import sqlbuilder.SQLQueryBuilder;

/**
 * Used to help with building queries; DB type can be specified - helpful in output;
 */
class BuilderHelper {
    private SQLQueryBuilder msSqlQueryBuilder;

    BuilderHelper(SQLQueryBuilderFactory.DB db) {
        this.msSqlQueryBuilder = SQLQueryBuilderFactory.createSqlQueryBuilder(db);
    }

    String buildRequiredSQL() {
        return msSqlQueryBuilder.from("Order")
                .join(SQLQueryBuilder.JOINS.INNER_JOIN, "Order", "CustomerId", "Customer", "Id")
                .join(SQLQueryBuilder.JOINS.LEFT_JOIN, "Customer", "CityId", "City", "Id")
                .output("Order", "TotalPrice", SQLQueryBuilder.AGGREGATE.SUM)
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
