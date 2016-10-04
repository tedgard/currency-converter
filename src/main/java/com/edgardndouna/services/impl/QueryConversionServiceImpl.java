package com.edgardndouna.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edgardndouna.domain.QueryConversion;
import com.edgardndouna.domain.User;
import com.edgardndouna.services.QueryConversionService;

@Service
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
			queries.add(new QueryConversion(1, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion(2, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion(3, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion(4, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion(5, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion(6, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion(7, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion(8, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion(9, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryConversion(10,"USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
	}
	
}
