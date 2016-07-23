package sqlbuilder;

/**
 * Basically a query being built by SQL Query Builder
 */
public class SqlQuery {
    private String FROM;
    private String SELECT;
    private String JOIN;
    private String GROUPBY;
    private String ORDERBY;
    private String LIMITER;
    private int limitValue;

    public String getFROM() {
        return FROM;
    }

    public void setFROM(String FROM) {
        this.FROM = FROM;
    }

    public String getSELECT() {
        return SELECT;
    }

    public void setSELECT(String SELECT) {
        this.SELECT = SELECT;
    }

    public String getJOIN() {
        return JOIN;
    }

    public void setJOIN(String JOIN) {
        this.JOIN = JOIN;
    }

    public String getGROUPBY() {
        return GROUPBY;
    }

    public void setGROUPBY(String GROUPBY) {
        this.GROUPBY = GROUPBY;
    }

    public String getORDERBY() {
        return ORDERBY;
    }

    public void setORDERBY(String ORDERBY) {
        this.ORDERBY = ORDERBY;
    }

    public String getLIMITER() {
        return LIMITER;
    }

    public void setLIMITER(String LIMITER) {
        this.LIMITER = LIMITER;
    }

    public int getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(int limitValue) {
        this.limitValue = limitValue;
    }
}
