package example;

import factory.SQLQueryBuilderFactory;
import sqlbuilder.SQLQueryBuilder;

public class Main {
    public static void main(String[] args) {
        System.out.println(new BuilderHelper(SQLQueryBuilderFactory.DB.MSSQL).buildRequiredSQL());
        System.out.println();
        System.out.println();
        System.out.println(new BuilderHelper(SQLQueryBuilderFactory.DB.ORACLE).buildRequiredSQL());
        System.out.println();
        System.out.println();
        System.out.println(new BuilderHelper(SQLQueryBuilderFactory.DB.MYSQL).buildRequiredSQL());
        System.out.println();
        System.out.println();
        System.out.println(new BuilderHelper(SQLQueryBuilderFactory.DB.MYSQL).buildRequiredSQL());
        System.out.println();
        System.out.println();
        SQLQueryBuilder builder = SQLQueryBuilderFactory.createSqlQueryBuilder(SQLQueryBuilderFactory.DB.MSSQL);
        System.out.println(builder.from("Order")
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
                .limit(5).build());
    }

}
