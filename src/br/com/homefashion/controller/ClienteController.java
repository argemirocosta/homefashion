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
    private List<Cliente> listaCliente;
    private String campoBusca;
    private ClienteDAO cDao = new ClienteDAO();

    public ClienteController() {
        cliente = new Cliente();
        campoBusca = "";
    }

    public void limparBusca() {
        listaCliente = null;
        listarClientes();
    }

    private void limparCampos() {
        listaCliente = null;
        cliente = new Cliente();
    }

    public void buscarClientePorNome() {
        listaCliente = cDao.buscaClientePorNome(campoBusca);
    }

    public void deletarCliente() {
        Boolean deletou = cDao.deletarCliente(cliente);

        if (deletou) {
            limparCampos();
            JSFUtil.fecharDialog(DIALOG_DELETAR_CLIENTE);
            JSFUtil.adicionarMensagemSucesso("Cliente deletado com sucesso!", "Sucesso");
        } else {
            JSFUtil.adicionarMensagemErro("Erro ao deletar o cliente!", "Erro");
        }
    }

    public void alterarCliente() {
        Boolean alterou = cDao.alterarCliente(cliente);

        if (alterou) {
            limparCampos();
            JSFUtil.fecharDialog(DIALOG_ALTERAR_CLIENTE);
            JSFUtil.adicionarMensagemSucesso("Cliente alterado com sucesso!", "Sucesso");
        } else {
            JSFUtil.adicionarMensagemErro("Erro ao alterar o cliente!", "Erro");
        }
    }

    public void inserirCliente() {
        Boolean cadastrou = cDao.inserirCliente(cliente);

        if (cadastrou) {
            limparCampos();
            JSFUtil.fecharDialog(DIALOG_CADASTRAR_CLIENTE);
            JSFUtil.adicionarMensagemSucesso("Cliente cadastrado com sucesso!", "Sucesso");
        } else {
            JSFUtil.adicionarMensagemErro("Erro ao cadastrar o cliente!", "Erro");
        }
    }

    public void listarClientes() {
        listaCliente = cDao.listarClientes();
    }

    //GETTERS E SETTERS


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public String getCampoBusca() {
        return campoBusca;
    }

    public void setCampoBusca(String campoBusca) {
        this.campoBusca = campoBusca;
    }

}