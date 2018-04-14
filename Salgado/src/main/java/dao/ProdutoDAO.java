package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Produto;
import util.ConexaoBanco;

public class ProdutoDAO {
	
	private Connection connection;
	
	public boolean atualizarFoto(Produto produto) {

		boolean atualizadoSucesso = false;
		String sql = "UPDATE produto SET caminhoImg = ? WHERE idProduto = ?";
			
		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setString(1, produto.getCaminhoImg());
			stmt.setInt(2, produto.getIdProduto());
		
			
			// executa
			int ok = stmt.executeUpdate();

			if (ok == 1) {
				System.out.println("Imagem atualizado com sucesso no BD!");
				atualizadoSucesso = true;
			} else {
				System.out.println("Erro ao atualizar IMG no BD! \n"+ stmt);
			}

			stmt.close();

			return atualizadoSucesso;
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar IMG no BD! "+e);
			throw new RuntimeException(e);
		} finally {
			ConexaoBanco.fecharConexao(this.connection);
		}
	}
	
	public Produto getProdutoId(int id) {
		String sql = "SELECT * FROM produto WHERE idProduto = ?";
		Produto produto = null;
		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				produto = new Produto();
				produto.setIdProduto(rs.getInt("idproduto"));
				produto.setNome(rs.getString("nome"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setCaminhoImg(rs.getString("caminhoImg"));
			}
			System.out.println("produto: "+ produto.getNome());
			return produto;
		}catch(SQLException e){
			System.out.println("Erro ao buscar Produto no BD!");
			e.printStackTrace();
			return null;
		}finally {
			ConexaoBanco.fecharConexao(this.connection);
		}
	}
}
