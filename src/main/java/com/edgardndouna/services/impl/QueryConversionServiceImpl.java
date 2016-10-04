package com.edgardndouna.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.edgardndouna.domain.QueryConversion;
import com.edgardndouna.domain.User;
import com.edgardndouna.services.QueryConversionService;

@Service
@Profile("dev")
public class QueryConversionServiceImpl implements QueryConversionService{

	private List<QueryConversion> queries;
	
	public QueryConversionServiceImpl() {
		mockQueries();
	}
	
	
	@Override
	public List<QueryConversion> loadLastTenQueriesPerformedByUser(int id) {		
		return queries.subList(queries.size()-10, queries.size());
	}
	
	@Override
	public void saveQuery(QueryConversion query){
		queries.add(query);
		
		//TODO:To remove
		System.out.println("--- New QueryConversion Elements : "+queries);
	}

	private void mockQueries(){
		queries = new ArrayList<>();
			queries.add(new QueryConversion("USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion("USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion("USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion("USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion("USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion("USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion("USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion("USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion("USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion("USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
	}
	
}
