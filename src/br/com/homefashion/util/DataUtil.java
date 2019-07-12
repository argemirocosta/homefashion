package br.com.homefashion.util;

import java.util.Date;

public final class DataUtil {

    public static java.sql.Date converterDateUtilParaDateSql(Date dataUtil) {
        java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

        return dataSql;
    }

}
