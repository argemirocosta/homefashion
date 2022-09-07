package br.com.homefashion.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;


public final class JSFUtil {

	public static void adicionarMensagemSucesso(String mensagem, String informativo) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, informativo);
		FacesContext contexto = FacesContext.getCurrentInstance();
		contexto.addMessage(null, msg);
	}

	public static void adicionarMensagemErro(String mensagem, String informativo) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, informativo);
		FacesContext contexto = FacesContext.getCurrentInstance();
		contexto.addMessage(null, msg);
	}

	public static void adicionarMensagemAdvertencia(String mensagem, String informativo) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, informativo);
		FacesContext contexto = FacesContext.getCurrentInstance();
		contexto.addMessage(null, msg);
	}

	public static void abrirDialog(String dialog) {
		PrimeFaces.current().executeScript("PF('" + dialog + "').show();");
	}

	public static void fecharDialog(String dialog) {
		PrimeFaces.current().executeScript("PF('" + dialog + "').hide();");
	}

}

