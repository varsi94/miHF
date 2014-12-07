package dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import accentprediction.TrigramWord;

public class MySQLDBConnection {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public MySQLDBConnection() throws SQLException, ClassNotFoundException {
	connect = DriverManager
		.getConnection("jdbc:mysql://localhost/?user=miHF&password=miHF");
    }

    public void writeTrigram(TrigramWord trigram) {
	try {
	    preparedStatement = connect
		    .prepareStatement("INSERT INTO mihf.bigram (word1, word2, frequency) VALUES (?, ?, 1)");
	    preparedStatement.setString(1, trigram.getWord(0));
	    preparedStatement.setString(2, trigram.getWord(1));
	    preparedStatement.execute();
	} catch (SQLException e) {
	    try {
		preparedStatement = connect
			.prepareStatement("UPDATE mihf.bigram SET frequency = frequency + 1 WHERE word1 = ? AND word2 = ?");
		preparedStatement.setString(1, trigram.getWord(0));
		preparedStatement.setString(2, trigram.getWord(1));
		preparedStatement.execute();
	    } catch (SQLException e1) {
		e1.printStackTrace();
	    }
	    //e.printStackTrace();
	}

    }

    public int getFrequency(TrigramWord trigram) {
	try {
	    preparedStatement = connect
		    .prepareStatement("SELECT * FROM mihf.bigram WHERE word1 = ? AND word2 = ?");
	    preparedStatement.setString(1, trigram.getWord(0));
	    preparedStatement.setString(2, trigram.getWord(1));
	    ResultSet result = preparedStatement.executeQuery();
	    result.first();
	    return result.getInt(3);
	} catch (SQLException e) {
	    //e.printStackTrace();
	}
	return -1;
    }

    public void close() throws SQLException {
	if (resultSet != null) {
	    resultSet.close();
	}
	if (statement != null) {
	    statement.close();
	}
	if (connect != null) {
	    connect.close();
	}
    }
}
