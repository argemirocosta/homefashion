package br.com.homefashion.controller;

import br.com.homefashion.dao.ClienteDAO;
import br.com.homefashion.dao.UsuarioDAO;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.util.SessionUtil;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

@SessionScoped
@ManagedBean
public class UsuarioSessionController {

	private Usuario usuario;
	private Usuario usuarioLogado;
	private String sessaoExpirada;

	public UsuarioSessionController() {
		usuario = new Usuario();
		usuarioLogado = null;
		sessaoExpirada = "N";
	}

	public String login() {

		UsuarioDAO udao = new UsuarioDAO();
		usuarioLogado = udao.login(usuario);

		if (usuarioLogado != null) {

			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("usuario_session", usuarioLogado);

			return "principal.faces?faces-redirect=true";
		} else {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Aviso", "Login ou senha inválidos!");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);

			return "";
		}
	}

	public static void timeOut() throws IOException {
		if (SessionUtil.getSession() != null) {
			SessionUtil.getSession().invalidate();
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("sessaoExpirada", "S");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("/index.faces");
		}
	}

	public String logout() {
		SessionUtil.getSession().invalidate();
		return "/index.faces?faces-redirect=true";
	}

	public void insereUsuario() {

		UsuarioDAO uDao = new UsuarioDAO();
		boolean cadastrou = uDao.insereUsuario(usuario);

		if (cadastrou) {

			FacesMessage msg = new FacesMessage(
					"Usuario cadastrado com sucesso!");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);

			RequestContext.getCurrentInstance().execute(
					"PF('dlgCadastro').hide();");

			usuario.setLogin("");
			usuario.setNome("");
			usuario.setSenha("");

		} else {
			FacesMessage msg = new FacesMessage("erro ao cadastrar");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getSessaoExpirada() {
		sessaoExpirada = (String) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("sessaoExpirada");
		return sessaoExpirada;
	}
}