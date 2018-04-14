package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Admin;
import util.ConexaoBanco;

public class LoginDAO {
	private Connection connection;
	
	
	public Admin loginAdm(String login, String senha) {
		Admin admin = null;
		String sql = "SELECT * FROM teste WHERE login = ?";
				try {
					this.connection = new ConexaoBanco().getConnection();
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, login);
					ResultSet rs = stmt.executeQuery();
					while(rs.next()) {
						admin = new Admin();
						admin.setLogin(rs.getString("login"));
						admin.setSenha(rs.getString("senha"));
						admin.setId(rs.getInt("idteste"));
					}
					stmt.close();
					
					if(admin != null) {
						if(admin.getSenha().equals(senha)) {
							return admin;
						}else {
							return null;
						}
					}
				}catch(SQLException e) {
					System.out.println("Erro ao verificar Login no BD!");
					e.printStackTrace();
				} finally {
					ConexaoBanco.fecharConexao(this.connection);
				}
				return admin;
	}
}
