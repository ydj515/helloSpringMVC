package kr.ac.hansung.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.model.Offer;

@Repository
public class OfferDAO { // DAO는 jdbc template사용

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource); // 의존성 주입 생성될때
	}

	public int getRowCount() {
		String SQL = "select count(*) from offers";
		return jdbcTemplate.queryForObject(SQL, Integer.class);

	}

	// query and return a single object
	public Offer getOffer(String name) { // name을 줘서 일치하는 record를 찾아 return 해주는 method
		String sqlStatement = "select * from offers where name =?"; // 여기서 '?'는 placeholder임
		return jdbcTemplate.queryForObject(sqlStatement, new Object[] { name }, // 여기에 name은 getOffer에 name
				new RowMapper<Offer>() {
					@Override
					public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {

						Offer offer = new Offer();
						offer.setId(rs.getInt("id")); // rs에 record가 int값이라 getInt사용
						offer.setName(rs.getString("name"));
						offer.setEmail(rs.getString("email"));
						offer.setText(rs.getString("text"));

						return offer; // rs에서 db값을 읽어서 offer object에 넣음

					}
				});
	}

	// query and return a multiple objects
	public List<Offer> getOffers() { // name을 줘서 일치하는 record를 찾아 return 해주는 method
		String sqlStatement = "select * from offers"; // 여기서 '?'는 placeholder임
		return jdbcTemplate.query(sqlStatement, // 여기에 name은 getOffer에 name
				new RowMapper<Offer>() {
					@Override
					public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {

						Offer offer = new Offer();
						offer.setId(rs.getInt("id")); // rs에 record가 int값이라 getInt사용
						offer.setName(rs.getString("name"));
						offer.setEmail(rs.getString("email"));
						offer.setText(rs.getString("text"));

						return offer; // rs에서 db값을 읽어서 offer object에 넣음

					}
				});
	}

	public boolean insert(Offer offer) {

		String name = offer.getName();
		String email = offer.getEmail();
		String text = offer.getText();

		String sqlStatement = "insert into offers (name, email, text) values (?,?,?)";
		return (jdbcTemplate.update(sqlStatement, new Object[] { name, email, text }) == 1);
		// update를 하면 int값이 넘어가는데
	}

	public boolean update(Offer offer) {

		String name = offer.getName();
		String email = offer.getEmail();
		String text = offer.getText();
		int id = offer.getId();

		String sqlStatement = "update offers set name =?, email=?, text=? where id=?";
		return (jdbcTemplate.update(sqlStatement, new Object[] { name, email, text, id }) == 1);
		// update를 하면 int값이 넘어가는데
	}

	public boolean delete(int id) {
		String sqlStatement = "delete from offers where id=?";
		return (jdbcTemplate.update(sqlStatement, new Object[] { id }) == 1);
	}

}