package it.unisa.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unisa.DriverManagerConnectionPool;
import it.unisa.control.AwardsController;
import it.unisa.control.Controller;
import it.unisa.control.RegisteredAccountController;

public class PremioModelDM implements ProductModel<Premio>{

	@Override
	public Premio doRetrieveByKey(String name) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs;
	
		Premio premio= new Premio();
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql="SELECT * FROM premio WHERE name= ?";
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setString(1, name);
			
			rs= preparedStatement.executeQuery();
			
			while (rs.next()) {
				premio.setName(rs.getString("name"));
				premio.setDescription(rs.getString("description"));
				premio.setPunti(rs.getString("punti"));
				premio.setQuantity(rs.getString("quantity"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection!=null)
					DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return premio;
	}

	@Override
	public ArrayList<Premio> doRetrieveAll(String order) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs=null;
		
		ArrayList<Premio> listPremi=new ArrayList<Premio>();
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "SELECT * FROM premio";
			if (order!=null && !order.equals(""))
				sql+=" ORDER BY " + order;
			preparedStatement= connection.prepareStatement(sql);
			
			rs= preparedStatement.executeQuery();
			
			while (rs.next()) {
				Premio premio= new Premio();

				premio.setName(rs.getString("name"));
				premio.setDescription(rs.getString("description"));
				premio.setPunti(rs.getString("punti"));
				premio.setQuantity(rs.getString("quantity"));
				
				listPremi.add(premio);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection!=null)
					DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return listPremi;
	}

	@Override
	public void doSave(Premio premio) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "INSERT INTO premio(name, description, punti, quantity) values (?, ?, ?, ?);";
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setString(1, premio.getName());
			preparedStatement.setString(2, premio.getDescription());
			preparedStatement.setString(3, premio.getPunti());
			preparedStatement.setString(4, premio.getQuantity());
			
			preparedStatement.executeUpdate();

			connection.commit();
			
			AwardsController.message="Il premio <b>" + premio.getName() + "</b> è stato inserito in modo corretto";
		} catch (SQLException e) {
			e.printStackTrace();
			AwardsController.errorMessage="<b>ERRORE</b>, Il premio non è stato inserito!";
			
		} finally {
			try {
				if (preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection!=null)
					DriverManagerConnectionPool.releaseConnection(connection);
			}
		}	
	}

	@Override
	public void doUpdate(Premio premio) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String sql = "UPDATE premio SET name = ?, description = ?, punti = ?, quantity = ? WHERE name = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(sql);	
			
			preparedStatement.setString(1, premio.getName());
			preparedStatement.setString(2, premio.getDescription());
			preparedStatement.setString(3, premio.getPunti());
			preparedStatement.setString(4, premio.getQuantity());
			
			if (AwardsController.idNameAward != null) {
				preparedStatement.setString(5, AwardsController.idNameAward);
			} else if (RegisteredAccountController.idNameAward != null) {
				preparedStatement.setString(5, RegisteredAccountController.idNameAward);
			}
			
			
			preparedStatement.executeUpdate();
			
			connection.commit();
			
			Controller.message="Il premio <b>" + premio.getName() + "</b> è stato aggiornato in modo corretto";
		} catch (SQLException e) {
			e.printStackTrace();
			Controller.errorMessage="<b>ERRORE</b>, Il premio non è stato aggiornato!";
			
		} finally {
			try {
				if (preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection!=null)
					DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
	}

	@Override
	public void doDelete(Premio premio) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "DELETE FROM premio WHERE name= ?";
			preparedStatement= connection.prepareStatement(sql);
			
			preparedStatement.setString(1, premio.getName());
			
			preparedStatement.executeUpdate();

			connection.commit();
			
			if (!premio.getName().equals("-1"))
				AwardsController.message="Il premio <b>" + premio.getName() + "</b> è stato cancellato";
			else AwardsController.message="Il premio è stato cancellato";
		} catch (SQLException e) {
			e.printStackTrace();
			AwardsController.errorMessage="<b>ERRORE</b>, Il premio non è stato cancellato!";
			
		} finally {
			try {
				if (preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection!=null)
					DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
	}
	
	public synchronized static byte[] load(String name) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs=null;
		
		byte[] photo=null;
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "SELECT photo FROM premio WHERE name= ?";
			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			
			rs= preparedStatement.executeQuery();
			
			if (rs.next())
				photo= rs.getBytes("photo");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection!=null)
					DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return photo;
	}
	
	public synchronized static void uploadPhoto(String name, String photo) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		try {
			connection= DriverManagerConnectionPool.getConnection();
			String sql= "UPDATE premio SET photo= ? WHERE name= ?";
			preparedStatement= connection.prepareStatement(sql);
			
			File filePhoto= new File(photo);
			FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(filePhoto);
				preparedStatement.setBinaryStream(1, inputStream, inputStream.available());
				preparedStatement.setString(2, name);
				
				preparedStatement.executeUpdate();
				
				connection.commit();

				Controller.message="Il premio <b>" + name + "</b> è stato aggiornato in modo corretto";
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Controller.errorMessage="<b>ERRORE</b>, Il premio non è stato aggiornato!";
		} finally {
			try {
				if (preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection!=null)
					DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		
	}
	

}//fine classe
