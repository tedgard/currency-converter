package services;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.edgardndouna.services.QueryConversionService;
import com.edgardndouna.services.QueryConversionServiceImpl;

import domain.QueryConversion;
import domain.User;

public class QueryConverisonServiceTest {

	private QueryConversionService queryConversionService;
	
	@Before
	public void setup(){
		queryConversionService = new QueryConversionServiceImpl();
	}
	
	@Test
	public void shouldReturnTheLastTenQueriesPerformedByUserOrEmptyList(){
		
		List<QueryConversion> results = queryConversionService.loadLastTenQueriesPerformedByUser(1);
		
		assertThat(results.size(), Matchers.anyOf(Matchers.lessThanOrEqualTo(10), Matchers.equalTo(0)));
	}
	
	@Test
	public void shouldSaveNewQueryPerfomedByUser(){
		
		User user = new User(1, "Jane Roe", "jane.roe@example.com", "test1234", "1988-09-01", "1267 Jackson St", "80000", "Amiens", "France");
		QueryConversion query = new QueryConversion(1, "USD", "EUR", 90.0, "2016-03-03", 80.1132, user);
		
		queryConversionService.saveQuery(query);
		List<QueryConversion> results = queryConversionService.loadLastTenQueriesPerformedByUser(1);
		
		assertThat(results.size(), Matchers.lessThanOrEqualTo(10));
		assertThat(results.get(results.size()-1), Matchers.hasProperty("user", Matchers.equalTo(user)));
	}
	
	
}
