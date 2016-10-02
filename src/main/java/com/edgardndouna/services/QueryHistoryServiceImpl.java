package com.edgardndouna.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import domain.QueryHistory;
import domain.User;

@Service
public class QueryHistoryServiceImpl implements QueryHistoryService{

	private List<QueryHistory> queries;
	
	public QueryHistoryServiceImpl() {
		mockQueries();
	}
	
	
	@Override
	public List<QueryHistory> loadLastTenQueryHistory(int id) {		
		return queries;
	}

	private void mockQueries(){
		queries = new ArrayList<>();
			queries.add(new QueryHistory(1, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryHistory(2, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryHistory(3, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryHistory(4, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryHistory(5, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryHistory(6, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryHistory(7, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryHistory(8, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryHistory(9, "USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
			queries.add(new QueryHistory(10,"USD", "EUR", 5.0, "2016-10-02", 10.0, new User()));
	}
	
}
