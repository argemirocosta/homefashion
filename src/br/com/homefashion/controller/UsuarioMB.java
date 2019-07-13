package br.com.homefashion.controller;

import br.com.homefashion.dao.UsuarioDAO;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.util.JSFUtil;
import br.com.homefashion.util.RedirecionarUtil;
import br.com.homefashion.util.SessaoUtil;

import static br.com.homefashion.shared.Dialogs.*;
import static br.com.homefashion.shared.Mensagens.*;
import static br.com.homefashion.shared.Paginas.*;
import static br.com.homefashion.shared.Sessao.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ViewScoped
@ManagedBean
public class UsuarioMB {

    private Usuario usuario;
    private Usuario usuarioLogado;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public UsuarioMB() {
        usuario = new Usuario();
        usuarioLogado = null;
    }

    public String login() {
        usuarioLogado = usuarioDAO.login(usuario);

        if (usuarioLogado != null) {
            return RedirecionarUtil.redirectPagina(PRINCIPAL);
        } else {
            JSFUtil.adicionarMensagemAdvertencia(LOGIN_SENHA_INVALIDO, AVISO);
            return "";
        }
    }

    public String logout() {
        SessaoUtil.retirarDaSessao(USUARIO_SESSAO);
        return RedirecionarUtil.redirectPagina(INDEX);
    }

    public void inserirUsuario() {
        boolean cadastrou = usuarioDAO.inserirUsuario(usuario);

        if (cadastrou) {
            JSFUtil.adicionarMensagemSucesso(USUARIO_CADASTRADO_SUCESSO, SUCESSO);
            JSFUtil.fecharDialog(DIALOG_CADASTRO_USUARIO);
            limparUsuario();

        } else {
            JSFUtil.adicionarMensagemErro(USUARIO_CADASTRADO_ERRO, ERRO);
        }
    }

    private void limparUsuario() {
        usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}