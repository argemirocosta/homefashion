package br.com.homefashion.shared;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Builder {

    Object construir();

    Object mapear(ResultSet rs) throws SQLException;
}
