package br.com.homefashion.controller;

import br.com.homefashion.dao.UsuarioDAO;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.util.JSFUtil;
import br.com.homefashion.util.RedirecionarUtil;
import br.com.homefashion.util.SessionUtil;

import static br.com.homefashion.shared.Dialogs.*;
import static br.com.homefashion.shared.Mensagens.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ViewScoped
@ManagedBean
public class UsuarioController {

	private Usuario usuario;
	private Usuario usuarioLogado;
	private String sessaoExpirada;
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	public UsuarioController() {
		usuario = new Usuario();
		usuarioLogado = null;
		sessaoExpirada = "N";
	}

	public String login() {
		usuarioLogado = usuarioDAO.login(usuario);

		if (usuarioLogado != null) {
			SessionUtil.adicionarNaSessao(usuarioLogado, "usuario_session");
			return RedirecionarUtil.redirectPagina("/pages/principal.faces");
		} else {
			JSFUtil.adicionarMensagemAdvertencia(LOGIN_SENHA_INVALIDO, AVISO);
			return "";
		}
	}

	public static void timeOut() {
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

	public void inserirUsuario() {
		boolean cadastrou = usuarioDAO.inserirUsuario(usuario);

		if (cadastrou) {
			JSFUtil.adicionarMensagemSucesso(USUARIO_CADASTRADO_SUCESSO, SUCESSO);
			JSFUtil.fecharDialog(DIALOG_CADASTRO_USUARIO);

			usuario.setLogin("");
			usuario.setNome("");
			usuario.setSenha("");

		} else {
			JSFUtil.adicionarMensagemErro(USUARIO_CADASTRADO_ERRO, ERRO);
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