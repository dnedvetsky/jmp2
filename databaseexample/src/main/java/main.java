import factory.SQLQueryBuilderFactory;
import sqlbuilder.BuilderHelper;

public class main {
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
    }

}
