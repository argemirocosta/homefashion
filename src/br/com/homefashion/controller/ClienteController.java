package br.com.homefashion.controller;

import br.com.homefashion.dao.ClienteDAO;
import br.com.homefashion.model.Cliente;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.homefashion.util.JSFUtil;

import static br.com.homefashion.shared.Dialogs.*;

@ViewScoped
@ManagedBean
public class ClienteController {

    private Cliente cliente;
    private List<Cliente> listaClientes;
    private String campoBusca;
    private ClienteDAO clienteDAO = new ClienteDAO();

    public ClienteController() {
        cliente = new Cliente();
    }

    public void limparBusca() {
        listaClientes = null;
        listarClientes();
    }

    private void limparCampos() {
        listaClientes = null;
        cliente = new Cliente();
    }

    public void buscarClientePorNome() {
        listaClientes = clienteDAO.buscarClientePorNome(campoBusca);
    }

    public void deletarCliente() {
        Boolean deletou = clienteDAO.deletarCliente(cliente);

        if (deletou) {
            limparCampos();
            JSFUtil.fecharDialog(DIALOG_DELETAR_CLIENTE);
            JSFUtil.adicionarMensagemSucesso("Cliente deletado com sucesso!", "Sucesso");
        } else {
            JSFUtil.adicionarMensagemErro("Erro ao deletar o cliente!", "Erro");
        }
    }

    public void alterarCliente() {
        Boolean alterou = clienteDAO.alterarCliente(cliente);

        if (alterou) {
            limparCampos();
            JSFUtil.fecharDialog(DIALOG_ALTERAR_CLIENTE);
            JSFUtil.adicionarMensagemSucesso("Cliente alterado com sucesso!", "Sucesso");
        } else {
            JSFUtil.adicionarMensagemErro("Erro ao alterar o cliente!", "Erro");
        }
    }

    public void inserirCliente() {
        Boolean cadastrou = clienteDAO.inserirCliente(cliente);

        if (cadastrou) {
            limparCampos();
            JSFUtil.fecharDialog(DIALOG_CADASTRAR_CLIENTE);
            JSFUtil.adicionarMensagemSucesso("Cliente cadastrado com sucesso!", "Sucesso");
        } else {
            JSFUtil.adicionarMensagemErro("Erro ao cadastrar o cliente!", "Erro");
        }
    }

    public void listarClientes() {
        listaClientes = clienteDAO.listarClientes();
    }

    //GETTERS E SETTERS


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public String getCampoBusca() {
        return campoBusca;
    }

    public void setCampoBusca(String campoBusca) {
        this.campoBusca = campoBusca;
    }

}