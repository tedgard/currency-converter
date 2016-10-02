package services;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.edgardndouna.services.QueryHistoryService;
import com.edgardndouna.services.QueryHistoryServiceImpl;

import domain.QueryHistory;

public class QueryHistoryServiceTest {

	@Test
	public void shouldReturnTheLastTenQueriesOrEmptyList(){
		QueryHistoryService queryHistoryService = new QueryHistoryServiceImpl();
		
		List<QueryHistory> results = queryHistoryService.loadLastTenQueryHistory(1);
		
		assertThat(results.size(), Matchers.equalTo(10));
	}
	
}
