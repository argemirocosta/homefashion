package br.com.homefashion.controller;

import br.com.homefashion.dao.UsuarioDAO;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.util.JSFUtil;
import br.com.homefashion.util.SessionUtil;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@SessionScoped
@ManagedBean
public class UsuarioSessionController {

	private Usuario usuario;
	private Usuario usuarioLogado;
	private String sessaoExpirada;
	private UsuarioDAO uDao = new UsuarioDAO();

	public UsuarioSessionController() {
		usuario = new Usuario();
		usuarioLogado = null;
		sessaoExpirada = "N";
	}

	public String login() {
		usuarioLogado = uDao.login(usuario);

		if (usuarioLogado != null) {

			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario_session", usuarioLogado);

			return "principal.faces?faces-redirect=true";
		} else {
			JSFUtil.adicionarMensagemAdvertencia("Login e/ou senha inválidos!", "Aviso");
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
		boolean cadastrou = uDao.insereUsuario(usuario);

		if (cadastrou) {
			JSFUtil.adicionarMensagemSucesso("Usuário cadastrado com sucesso!", "Sucesso");
			JSFUtil.fecharDialog("dlgCadastro");

			usuario.setLogin("");
			usuario.setNome("");
			usuario.setSenha("");

		} else {
			JSFUtil.adicionarMensagemErro("Erro ao cadastrar!", "Erro");
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
		sessaoExpirada = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessaoExpirada");
		return sessaoExpirada;
	}
}