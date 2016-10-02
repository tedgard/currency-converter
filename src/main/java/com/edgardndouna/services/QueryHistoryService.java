package com.edgardndouna.services;

import java.util.List;

import domain.QueryHistory;

public interface QueryHistoryService {

	public List<QueryHistory> loadLastTenQueryHistory(int id);
	
}
