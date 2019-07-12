package br.com.homefashion.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class SessionUtil {

	public static HttpSession getSession() {
		ExternalContext extCon = FacesContext.getCurrentInstance().getExternalContext();
		return (HttpSession) extCon.getSession(true);
	}

	public static void adicionarNaSessao(Object objeto, String nomeObjetoSessao){
		FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().put(nomeObjetoSessao, objeto);
	}

	public static Object resgatarDaSessao(String nomeObjetoSessao){

		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(nomeObjetoSessao);
	}
}