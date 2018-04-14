package controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.ProdutoDAO;
import model.Admin;
import model.Produto;
import util.Upload;

@ManagedBean(name="controladorAdmin")
public class ControladorAdmin implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Admin adm = new Admin();
	private Part arquivo;
	public int s;
	private String caminhoImg;
	private Produto produto = new Produto();
	private int id = 1;
	private static final int MAX_SIZE = 2 * 1024 * 1024;
	 
	public Admin pegaAdminNaSessao(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) 
				context.getExternalContext().getRequest();
		
		HttpSession sessao = request.getSession();
		Admin admin = (Admin) sessao.getAttribute("admlogado");
		
		return admin;
	}
	
public String editaPerfil(){
		
		this.adm = pegaAdminNaSessao();
		System.out.println(this.getAdm().getLogin());
		ProdutoDAO pdao = new ProdutoDAO();
		produto = pdao.getProdutoId(id);
		System.out.println("Produto caminho: "+ produto.getCaminhoImg());
		this.setCaminhoImg(produto.getCaminhoImg());
		if(this.adm != null) {
			return "AdmLogado.xhtml";
		}else {
		return "index.xhtml";
		}
	}


public String extractFileName(Part part) {
    String contentDisp = part.getHeader("content-disposition");
    String[] items = contentDisp.split(";");
    for (String s : items) {
        if (s.trim().startsWith("filename")) {
            return s.substring(s.indexOf("=") + 2, s.length()-1);
        }
    }
    return "";
}
	
	public String uploadArquivo(){
		 
		 //verifica se o arquivo está nulo
		 if(arquivo == null || arquivo.getSize() <=0 || arquivo.getContentType().isEmpty()){
			 System.out.println("VAZIO");
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um arquivo válido.", ""));	
			 return editaPerfil();
		
			}
			
			//verifica o tamanho do arquivo
			if (arquivo.getSize() > MAX_SIZE) {
				System.out.println("MT GRANDE");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O arquivo deve ter no máximo 2mb.", ""));	
				return editaPerfil();
				
			}
			//verifica o tipo do arquivo
			 String fileName = extractFileName(arquivo);
			if (!fileName.endsWith(".png") && !fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg")) {
				System.out.println("INVALIDO");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tipo de arquivo inválido", ""));	
				return editaPerfil();
			}
		 
		 try {
		
		 Upload upload = Upload.getInstance();
		 upload.write(arquivo);
		 System.out.println("ENVIADO");
		 //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Arquivo enviado.", ""));	
		 return editaPerfil();
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 return editaPerfil();
		 }

	public Admin getAdm() {
		return adm;
	}

	public void setAdm(Admin adm) {
		this.adm = adm;
	}

	public Part getArquivo() {
		return arquivo;
	}

	public void setArquivo(Part arquivo) {
		this.arquivo = arquivo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static int getMaxSize() {
		return MAX_SIZE;
	}

	public String getCaminhoImg() {
		return caminhoImg;
	}

	public void setCaminhoImg(String caminhoImg) {
		this.caminhoImg = caminhoImg;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	
}
