package br.com.homefashion.util;

import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

public class DataUtilTeste {

    @Test
    public void converterDateUtilParaDateSql(){
        Date dataSql = DataUtil.converterDateUtilParaDateSql(new java.util.Date());

        Date dataEsperada = new java.sql.Date(new java.util.Date().getTime());

        assertEquals(dataEsperada.toString(), dataSql.toString());
    }

    @Test
    public void retornarDataAtual(){
        assertEquals(DataUtil.retornarDataAtual().toString(), new java.util.Date().toString() );
    }
}
