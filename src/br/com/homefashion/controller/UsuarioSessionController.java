package br.com.homefashion.controller;

import br.com.homefashion.dao.UsuarioDAO;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.shared.Dialogs;
import br.com.homefashion.util.JSFUtil;
import br.com.homefashion.util.RedirecionarUtil;
import br.com.homefashion.util.SessionUtil;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@SessionScoped
@ManagedBean
public class UsuarioSessionController extends Dialogs {

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
			SessionUtil.adicionarNaSessao(usuarioLogado, "usuario_session");
			return RedirecionarUtil.redirectPagina("/pages/principal.faces");
		} else {
			JSFUtil.adicionarMensagemAdvertencia("Login e/ou senha inválidos!", "Aviso");
			return "";
		}
	}

	public static void timeOut() throws IOException {
		if (SessionUtil.getSession() != null) {
			SessionUtil.getSession().invalidate();
			SessionUtil.adicionarNaSessao("S", "sessaoExpirada");
		} else {
			RedirecionarUtil.redirectPagina("/index.faces");
		}
	}

	public String logout() {
		SessionUtil.getSession().invalidate();
		return RedirecionarUtil.redirectPagina("/index.faces");
	}

	public void insereUsuario() {
		boolean cadastrou = uDao.insereUsuario(usuario);

		if (cadastrou) {
			JSFUtil.adicionarMensagemSucesso("Usuário cadastrado com sucesso!", "Sucesso");
			JSFUtil.fecharDialog(DIALOG_CADASTRO_USUARIO);

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
		sessaoExpirada = (String) SessionUtil.resgatarDaSessao("sessaoExpirada");
		return sessaoExpirada;
	}
}