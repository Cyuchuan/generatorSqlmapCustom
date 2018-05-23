package cn.cyc.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import cn.cyc.search.pojo.SearchResult;

public interface SearchDao {

	SearchResult search(SolrQuery query) throws Exception;
}
