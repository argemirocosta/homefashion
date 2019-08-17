package br.com.homefashion.controller;

import br.com.homefashion.model.Cliente;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.homefashion.service.ClienteService;
import br.com.homefashion.util.CEPUtil;
import br.com.homefashion.util.JSFUtil;

import static br.com.homefashion.shared.Dialogs.*;
import static br.com.homefashion.shared.Mensagens.*;

@ViewScoped
@ManagedBean
public class ClienteMB {

    private Cliente cliente;
    private List<Cliente> listaClientes;
    private String campoBusca;
    private ClienteService clienteService;

    public ClienteMB() {
        cliente = new Cliente();
        clienteService = new ClienteService();
    }

    public void limparBusca() {
        listaClientes = null;
        campoBusca = null;
        listarClientes();
    }

    private void limparCampos() {
        cliente = new Cliente();
    }

    public void buscarClientePorNome() {
        listaClientes = clienteService.buscarClientePorNome(campoBusca);
    }

    public void inserirCliente() {
        Boolean cadastrou = clienteService.inserirCliente(cliente);

        if (cadastrou) {
            limparCampos();
            listarClientes();
            JSFUtil.fecharDialog(DIALOG_CADASTRAR_CLIENTE);
            JSFUtil.adicionarMensagemSucesso(CLIENTE_CADASTRADO_SUCESSO, SUCESSO);
        } else {
            JSFUtil.adicionarMensagemErro(CLIENTE_CADASTRADO_ERRO, ERRO);
        }
    }

    public void alterarCliente() {
        Boolean alterou =  clienteService.alterarCliente(cliente);

        if (alterou) {
            limparCampos();
            listarClientes();
            JSFUtil.fecharDialog(DIALOG_ALTERAR_CLIENTE);
            JSFUtil.adicionarMensagemSucesso(CLIENTE_ALTERADO_SUCESSO, SUCESSO);
        } else {
            JSFUtil.adicionarMensagemErro(CLIENTE_ALTERADO_ERRO, ERRO);
        }
    }

    public void deletarCliente() {
        Boolean deletou = clienteService.deletarCliente(cliente);

        if (deletou) {
            limparCampos();
            listarClientes();
            JSFUtil.fecharDialog(DIALOG_DELETAR_CLIENTE);
            JSFUtil.adicionarMensagemSucesso(CLIENTE_EXCLUIDO_SUCESSO, SUCESSO);
        } else {
            JSFUtil.adicionarMensagemErro(CLIENTE_EXCLUIDO_ERRO, ERRO);
        }
    }

    public void listarClientes() {
        listaClientes = clienteService.listarClientes();
    }

    public void buscarEnderecoClientePorCEP(){
        cliente.setEndereco(CEPUtil.buscarEnderecoPorCEP(cliente.getEndereco().getCep()));
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