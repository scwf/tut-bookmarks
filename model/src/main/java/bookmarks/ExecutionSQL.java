package bookmarks;

import javax.persistence.Entity;

/**
 * Created by w00228970 on 2017/4/6.
 */
public class ExecutionSQL {
    private String sql;

    public ExecutionSQL() {
    }

    public ExecutionSQL(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
