package controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.LoginDAO;
import model.Admin;

@ManagedBean(name="loginController")
public class LoginController {
	private Admin adm;
	private String login;
	private String senha;

	public String logar() {
		
		String paginaDestino;
		LoginDAO log = new LoginDAO();
		Admin admLogando = log.loginAdm(login, senha);
		
		// Obtem a Sess„o
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession sessao = request.getSession();
		
		// Caso sim
		if (admLogando != null) {
			// Adicionar a pessoa na Session
			sessao.setAttribute("admlogado", admLogando);

			// encaminhar para a p·gina de sucesso
			paginaDestino = "security/AdmLogado.xhtml";
		} else {
			sessao.invalidate();

			// Sen„o encaminhar para a p·gina de erro
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nome de usu√°rio ou senha inv√°lidos.", ""));
			paginaDestino = "AdmLogin.xhtml";
		}

		return paginaDestino;
	}
	
	public Admin getAdm() {

		if (this.adm == null) {
			adm = new Admin();
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			HttpSession sessao = request.getSession();
			adm = (Admin) sessao.getAttribute("admlogado");
		}else{
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			HttpSession sessao = request.getSession();
			adm = (Admin) sessao.getAttribute("admlogado");
		}
		return adm;
	}

	public void setAdm(Admin adm) {
		this.adm = adm;
	}

	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}
}
