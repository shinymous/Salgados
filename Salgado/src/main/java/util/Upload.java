package util;

import java.io.File;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import dao.ProdutoDAO;
import model.Produto;

public class Upload {
    private static final Upload INSTANCE = new Upload();

    private Upload() {}

    public void write(Part part) throws IOException {
        String fileName = extractFileName(part);
        File nome = new File(fileName);
        System.out.println(part.toString());
        String filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("")+"img"+File.separator+"fotos";
        System.out.println(fileName);
        File fileSaveDir = new File(filePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        System.out.println("NOMEEEEE: "+ nome.getName().trim().replaceAll(" ", "_"));
        //part.write(filePath + File.separator + fileName.trim().replaceAll(" ", "_"));
        part.write(filePath + File.separator + nome.getName().trim().replaceAll(" ", "_"));
        Produto produto= new Produto();
        produto.setCaminhoImg(nome.getName().trim().replaceAll(" ", "_"));
        produto.setIdProduto(1);
        ProdutoDAO pDAO = new ProdutoDAO();
        pDAO.atualizarFoto(produto);
        
        System.out.println(filePath+File.separator+fileName.trim().replaceAll(" ", "_"));
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

    public static Upload getInstance() {
        return INSTANCE;
    }

}