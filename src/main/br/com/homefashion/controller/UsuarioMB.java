package br.com.homefashion.controller;

import br.com.homefashion.dto.ParametrosVerificarSenhaUsuarioDTO;
import br.com.homefashion.exception.ProjetoException;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.service.UsuarioService;
import br.com.homefashion.util.JSFUtil;
import br.com.homefashion.util.RedirecionarUtil;
import br.com.homefashion.util.SessaoUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import static br.com.homefashion.shared.Dialogs.DIALOG_ALTERAR_USUARIO;
import static br.com.homefashion.shared.Dialogs.DIALOG_CADASTRO_USUARIO;
import static br.com.homefashion.shared.Mensagens.*;
import static br.com.homefashion.shared.Paginas.INDEX;
import static br.com.homefashion.shared.Paginas.PRINCIPAL;
import static br.com.homefashion.shared.Sessao.USUARIO_SESSAO;

@ViewScoped
@ManagedBean
public class UsuarioMB {

    private Usuario usuario;
    private Usuario usuarioLogado;
    private UsuarioService usuarioService = new UsuarioService();
    private String senhaAtual;

    public UsuarioMB() {
        usuario = new Usuario();
        usuarioLogado = null;
    }

    public String login() {
        usuarioLogado = usuarioService.login(usuario);

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
        try {
            usuarioService.inserirUsuario(usuario);
            JSFUtil.adicionarMensagemSucesso(USUARIO_CADASTRADO_SUCESSO, SUCESSO);
            JSFUtil.fecharDialog(DIALOG_CADASTRO_USUARIO);
            limparUsuario();
        } catch (ProjetoException e) {
            JSFUtil.adicionarMensagemErro(USUARIO_CADASTRADO_ERRO, ERRO);

        }
    }

    public void carregarDadosUsuario(){
        usuario = (Usuario) SessaoUtil.resgatarDaSessao(USUARIO_SESSAO);
    }

    public void verificarSePodeAlterarUsuario(){

        ParametrosVerificarSenhaUsuarioDTO parametrosVerificarSenhaUsuarioDTO = new ParametrosVerificarSenhaUsuarioDTO(usuario.getId(), senhaAtual);

        if(usuarioService.verificarSenhaUsuario(parametrosVerificarSenhaUsuarioDTO)){
            alterarUsuario();
        }
        else{
            JSFUtil.adicionarMensagemErro(SENHA_INVALIDA, ERRO);
        }

    }

    private void alterarUsuario() {
        try {
            usuarioService.alterarUsuario(usuario);
            JSFUtil.adicionarMensagemSucesso(USUARIO_ALTERADO_SUCESSO, SUCESSO);
            JSFUtil.fecharDialog(DIALOG_ALTERAR_USUARIO);
            limparUsuario();
        } catch (ProjetoException e) {
            JSFUtil.adicionarMensagemErro(USUARIO_ALTERADO_ERRO, ERRO);
        }
    }

    private void limparUsuario() {
        usuario = new Usuario();
    }

    //GETTERS E SETTERS

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }
}