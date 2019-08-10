package br.com.homefashion.util;

import br.com.homefashion.factory.ConnectionFactory;
import net.sf.jasperreports.engine.JasperRunManager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ManagedBean
@ViewScoped
public class RelatorioUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public RelatorioUtil() {

    }

    public void reciboReport(Integer idPagamento) throws IOException {
        String caminho = "/WEB-INF/relatorios/";
        String relatorio = caminho + "recibo.jasper";
        Map<String, Object> map = new HashMap<>();

        map.put("id_pagamento", idPagamento);
        map.put("REPORT_LOCALE", new Locale("pt", "BR"));
        this.executeReport(relatorio, map, "Recibo venda "+idPagamento+".pdf");
    }

    private void executeReport(String relatorio, Map<String, Object> map, String filename)
            throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

        InputStream reportStream = context.getExternalContext().getResourceAsStream(relatorio);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);

        ServletOutputStream servletOutputStream = response.getOutputStream();

        try {
            Connection connection = ConnectionFactory.getConnection();

            JasperRunManager.runReportToPdfStream(reportStream, response.getOutputStream(), map, connection);

            if (connection != null) {
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.getFacesContext().responseComplete();
        servletOutputStream.flush();
        servletOutputStream.close();
    }

    private FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
