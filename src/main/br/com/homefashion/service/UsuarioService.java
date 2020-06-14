package br.com.homefashion.service;

import br.com.homefashion.dao.UsuarioDAO;
import br.com.homefashion.dto.ParametrosVerificarSenhaUsuarioDTO;
import br.com.homefashion.exception.ProjetoException;
import br.com.homefashion.model.Usuario;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        usuarioDAO = new UsuarioDAO();
    }

    public Usuario login(Usuario usuario) {
        return usuarioDAO.login(usuario);
    }

    public void inserirUsuario(Usuario usuario) throws ProjetoException {
        usuarioDAO.inserirUsuario(usuario);
    }

    public Boolean verificarSenhaUsuario(ParametrosVerificarSenhaUsuarioDTO parametrosVerificarSenhaUsuarioDTO) {
        return usuarioDAO.verificarSenhaUsuario(parametrosVerificarSenhaUsuarioDTO);
    }

    public void alterarUsuario(Usuario usuario) throws ProjetoException {
        usuarioDAO.alterarUsuario(usuario);
    }
}