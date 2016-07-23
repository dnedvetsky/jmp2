package sqlbuilder;

import factory.SQLQueryBuilderFactory;

/**
 * Builder should be able to build following statement:
 * SELECT SUM(O.TotalPrice), C.FirstName, C.LastName, CI.Name
 * FROM Order O
 * JOIN Customer C ON O.CustomerId = C.Id
 * LEFT JOIN City CI ON C.CityId = CI.Name
 * GROUP BY C.FirstName, C.LastName, CI.Name
 * ORDER BY C.LastName ASC
 * LIMIT 5
 */
public class OracleQueryBuilder extends SQLQueryBuilder {
    private SqlQuery sqlQuery = new SqlQuery();


    /**
     * Constructor. Uses top to set type of limiter depending on Database type
     */
    public OracleQueryBuilder() {
        super(SQLQueryBuilderFactory.DB.ORACLE.getValue());
    }

}
