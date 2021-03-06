package com.edgardndouna.services;

import java.util.List;

import com.edgardndouna.domain.QueryConversion;

public interface QueryConversionService {

	public List<QueryConversion> loadLastTenQueriesPerformedByUser(int id);
	
	public void saveQuery(QueryConversion query);
}
