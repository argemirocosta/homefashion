package br.com.homefashion.controller;

import br.com.homefashion.dao.UsuarioDAO;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.util.JSFUtil;
import br.com.homefashion.util.RedirecionarUtil;
import br.com.homefashion.util.SessionUtil;

import static br.com.homefashion.shared.Dialogs.*;
import static br.com.homefashion.shared.Mensagens.*;
import static br.com.homefashion.shared.Paginas.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ViewScoped
@ManagedBean
public class UsuarioController {

	private Usuario usuario;
	private Usuario usuarioLogado;
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	public UsuarioController() {
		usuario = new Usuario();
		usuarioLogado = null;
	}

	public String login() {
		usuarioLogado = usuarioDAO.login(usuario);

		if (usuarioLogado != null) {
			SessionUtil.adicionarNaSessao(usuarioLogado, "usuario_session");
			return RedirecionarUtil.redirectPagina(PRINCIPAL);
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
			RedirecionarUtil.redirectPagina(INDEX);
		}
	}

	public String logout() {
		SessionUtil.getSession().invalidate();
		return RedirecionarUtil.redirectPagina(INDEX);
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

}