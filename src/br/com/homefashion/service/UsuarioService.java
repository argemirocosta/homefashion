package br.com.homefashion.service;

import br.com.homefashion.dao.UsuarioDAO;
import br.com.homefashion.dto.ParametrosVerificarSenhaUsuarioDTO;
import br.com.homefashion.model.Usuario;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        usuarioDAO = new UsuarioDAO();
    }

    public Usuario login(Usuario usuario) {
        return usuarioDAO.login(usuario);
    }

    public Boolean inserirUsuario(Usuario usuario) {
        return usuarioDAO.inserirUsuario(usuario);
    }

    public Boolean verificarSenhaUsuario(ParametrosVerificarSenhaUsuarioDTO parametrosVerificarSenhaUsuarioDTO) {
        return usuarioDAO.verificarSenhaUsuario(parametrosVerificarSenhaUsuarioDTO);
    }

    public Boolean alterarUsuario(Usuario usuario) {
        return usuarioDAO.alterarUsuario(usuario);
    }
}