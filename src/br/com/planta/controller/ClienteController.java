package br.com.planta.controller;

import br.com.planta.dao.ClienteDAO;
import br.com.planta.dao.UsuarioDAO;
import br.com.planta.model.ClienteBean;
import br.com.planta.model.VendaBean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

@ViewScoped
@ManagedBean
public class ClienteController {

	private ClienteBean p;
	private List<ClienteBean> listaCliente;
	private String campoBusca;
	private VendaBean venda;

	public ClienteController() {
		p = new ClienteBean();
		venda = new VendaBean();
		campoBusca = "";
	}

	public void limparBusca() {
		listaCliente = null;
		campoBusca = "";
	}

	public void limparCampos() {
		listaCliente = null;
		p = new ClienteBean();
	}

	public void busca() {
		buscaNome();
	}

	private void buscaNome() {
		ClienteDAO pd = new ClienteDAO();
		listaCliente = pd.buscaNome(campoBusca);
	}

	public void deleteCliente() {

		ClienteDAO pd = new ClienteDAO();
		boolean deletou = pd.deleteCliente(p);

		if (deletou) {
			limparCampos();
			RequestContext.getCurrentInstance().execute(
					"PF('dlgDeleteCliente').hide();");
			FacesMessage msg = new FacesMessage("Cliente deletado");
			FacesContext ct = FacesContext.getCurrentInstance();

			ct.addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage("erro ao deletar");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
		}
	}

	public void editeCliente() {

		ClienteDAO pd = new ClienteDAO();
		boolean alterou = pd.editeCliente(p);

		if (alterou) {
			limparCampos();

			RequestContext.getCurrentInstance().execute(
					"PF('dlgAltPlanta').hide();");

			FacesMessage msg = new FacesMessage("Cliente Alterado");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage("erro ao alterar");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
		}
	}

	public void insereCliente() {

		ClienteDAO pd = new ClienteDAO();
		boolean cadastrou = pd.insereCliente(p);

		if (cadastrou) {

			limparCampos();

			RequestContext.getCurrentInstance().execute(
					"PF('dlgCadPlanta').hide();");

			FacesMessage msg = new FacesMessage("Cliente cadastrado");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage("erro ao cadastrar");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
		}
	}

	public ClienteBean getP() {
		return p;
	}

	public void setP(ClienteBean p) {
		this.p = p;
	}

	public List<ClienteBean> getListaCliente() {
		ClienteDAO pd = new ClienteDAO();
		if (listaCliente == null) {
			listaCliente = pd.listarClientes();
		}
		return listaCliente;
	}

	public void setListaCliente(List<ClienteBean> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public String getCampoBusca() {
		return campoBusca;
	}

	public void setCampoBusca(String campoBusca) {
		this.campoBusca = campoBusca;
	}

	public VendaBean getVenda() {
		return venda;
	}

	public void setVenda(VendaBean venda) {
		this.venda = venda;
	}

}