package com.enjoyf.search.bean;

import org.apache.solr.client.solrj.SolrQuery;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-10-9
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
public class SolrSortBean {
    private String sortField;
    private SolrQuery.ORDER order= SolrQuery.ORDER.asc;

    public SolrSortBean(String sortField, SolrQuery.ORDER order) {
        this.sortField = sortField;
        this.order = order;
    }

    public SolrSortBean(String sortField) {
        this.sortField = sortField;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public SolrQuery.ORDER getOrder() {
        return order;
    }

    public void setOrder(SolrQuery.ORDER order) {
        this.order = order;
    }

    public static SolrSortBean praseParam(String param){
        String[] fields =param.split(":");
        if(fields.length==1){
           return  new SolrSortBean(fields[0]);
        }

        if(fields.length==2){
            if(fields[1].equalsIgnoreCase("desc")){
                return  new SolrSortBean(fields[0], SolrQuery.ORDER.desc);
            }
            return new SolrSortBean(fields[0]) ;
        }

        return null;
    }
}
