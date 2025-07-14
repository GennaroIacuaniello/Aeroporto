package implementazioniPostgresDAO;

import dao.EsempioDAO;
import database.ConnessioneDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class EsempioImplementazionePostgresDAO implements EsempioDAO {

	private Connection connection;

	public void EsempioImplementazionePostgresDAO() {
		try {
			connection = ConnessioneDatabase.getInstance().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Override
	public void esempioQuery() {

	}

}
