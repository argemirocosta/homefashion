package br.com.homefashion.util;

import br.com.homefashion.controller.UsuarioSessionController;
import br.com.homefashion.model.Usuario;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class AuthorizationListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		// Adquirindo o FacesContext.
		FacesContext facesContext = event.getFacesContext();

		// Página Atual.
		String currentPage = facesContext.getViewRoot().getViewId();

		// Verifica se está na página de login.
		boolean isLoginPage = (currentPage.lastIndexOf("index") > -1);

		// Recuperando objetos da sessão.
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		Usuario usuario = (Usuario) session.getAttribute("usuario_session");

		// Redireciona para a página de login quando a sessão expira.
		if (!isLoginPage && usuario == null) {
			try {
				UsuarioSessionController.timeOut();
				if (SessionUtil.getSession() != null) {
					SessionUtil.getSession().invalidate();

					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sessaoExpirada", "S");
				}
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.faces");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			if (isLoginPage) {
			} else {
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}