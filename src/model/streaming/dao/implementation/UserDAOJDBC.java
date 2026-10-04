package model.streaming.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.streaming.dao.MyConnection;
import model.streaming.dao.UserDAO;
import model.streaming.user.CommonUser;
import model.streaming.user.User;

public class UserDAOJDBC implements UserDAO {

	@Override
	public void save(User user, String password) {
		Connection connection = MyConnection.getCon();
		PreparedStatement statementData = null;
		PreparedStatement statementUser = null;
		PreparedStatement statementDNI = null;
		ResultSet rs = null;
		int personalDataId = -1;

		String sqlPersonalData = "INSERT INTO PERSONALDATA (NAME, SURNAME, DNI) VALUES (?, ?, ?)";
		String sqlDNI = "SELECT ID FROM PERSONALDATA WHERE DNI = ?";
		String sqlUser = "INSERT INTO USER (USERNAME, EMAIL, PASSWORD, PERSONALDATA_ID) VALUES (?, ?, ?, ?)";

		try {
			connection.setAutoCommit(false);

			statementData = connection.prepareStatement(sqlPersonalData);
			statementData.setString(1, user.getName());
			statementData.setString(2, user.getSurname());
			statementData.setInt(3, user.getDNI());

			int added = statementData.executeUpdate();

			if (added == 0) {
				throw new SQLException("No se pudo guardar en PERSONALDATA, ninguna fila afectada.");
			}

			statementDNI = connection.prepareStatement(sqlDNI);
			statementDNI.setInt(1, user.getDNI());
			rs = statementDNI.executeQuery();

			if (rs.next()) {
				personalDataId = rs.getInt("ID");
			} else {
				throw new SQLException(
						"No se pudo encontrar el ID de PERSONALDATA recién insertado para DNI: " + user.getDNI());
			}

			statementUser = connection.prepareStatement(sqlUser);
			statementUser.setString(1, user.getUsername());
			statementUser.setString(2, user.getEmail());
			statementUser.setString(3, password);
			statementUser.setInt(4, personalDataId);

			statementUser.executeUpdate();

			connection.commit();
			System.out.println("Usuario guardado con éxito");

			statementData.close();
			statementUser.close();
			statementDNI.close();
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("Error al guardar el usuario: " + e.getMessage());
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("Error al restaurar auto-commit: " + e.getMessage());
			}
		}
	}

	
	
	@Override
	public boolean dniExists(int dni) {
		String sql = "SELECT * FROM PERSONALDATA WHERE DNI = ?";
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = MyConnection.getCon();

		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, dni);
			rs = statement.executeQuery();
			if (rs.next()) {
				return true;
			}
			statement.close();
		} catch (SQLException e) {
			System.out.println("Error al verificar DNI: " + e.getMessage());
		}
		return false;
	}

	public void savePersonalData() {

	}

	@Override
	public User findById(int id) {

		return null;
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		Connection connection = MyConnection.getCon();
		PreparedStatement statement = null;
		ResultSet rs = null;
		User user = null;

		String sql = "SELECT USER.ID, USER.USERNAME, USER.EMAIL, PERSONALDATA.NAME, PERSONALDATA.SURNAME, PERSONALDATA.DNI "
				   + "FROM USER "
				   + "INNER JOIN PERSONALDATA ON PERSONALDATA.ID = USER.PERSONALDATA_ID "
				   + "WHERE USER.USERNAME = ? AND USER.PASSWORD = ?";

		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);

			rs = statement.executeQuery();

			if (rs.next()) {
	            user = new CommonUser();
	            user.setUsername(rs.getString("USERNAME"));
	            user.setID(rs.getInt("ID"));	        
	            }
			
			statement.close();
		} catch (SQLException e) {
			System.out.println("Error al buscar usuario por nombre y contraseña: " + e.getMessage());
			return null;
		}
		
		return user;
	}

	@Override
	public List<User> findAll() {
		Connection connection = MyConnection.getCon();
		PreparedStatement statementData = null;

		ResultSet rs = null;
		List<User> usersList = new LinkedList<>();
		String sqlUser = "SELECT USER.ID, USER.USERNAME, USER.EMAIL, PERSONALDATA.ID AS PERSONALDATAID, PERSONALDATA.NAME, PERSONALDATA.SURNAME, PERSONALDATA.DNI FROM USER INNER JOIN PERSONALDATA ON PERSONALDATA.id=USER.PERSONALDATA_ID";

		try {
			statementData = connection.prepareStatement(sqlUser);
			rs = statementData.executeQuery();

			while (rs.next()) {
				User user = new CommonUser();
				user.setID(rs.getInt("ID"));	        
				user.setName(rs.getString("NAME"));
				user.setSurname(rs.getString("SURNAME"));
				user.setEmail(rs.getString("EMAIL"));
				user.setDNI(rs.getInt("DNI"));
				user.setUsername(rs.getString("USERNAME"));
				usersList.add(user);
			}
			statementData.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return  usersList;
	}

	@Override
	public List<User> findAllByDNI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> findAllByName() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean userExists(String username)  {
		Connection conn = MyConnection.getCon();
		String SQL_USER_ID = "SELECT ID FROM USER WHERE USERNAME = ?";
        try (PreparedStatement ps = conn.prepareStatement(SQL_USER_ID)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
            ps.close();
        }catch (SQLException e) {
        	e.getMessage();
        }
        return false;
    }
	
}
