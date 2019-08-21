package com.vectorcoder.androidwoocommerce.models.product_filters_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FilterTerm {

    @SerializedName("term_id")
    @Expose
    private int termId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("term_group")
    @Expose
    private int termGroup;
    @SerializedName("term_taxonomy_id")
    @Expose
    private int termTaxonomyId;
    @SerializedName("taxonomy")
    @Expose
    private String taxonomy;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("parent")
    @Expose
    private int parent;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("filter")
    @Expose
    private String filter;
    @SerializedName("meta_value")
    @Expose
    private String metaValue;

    
    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getTermGroup() {
        return termGroup;
    }

    public void setTermGroup(int termGroup) {
        this.termGroup = termGroup;
    }

    public int getTermTaxonomyId() {
        return termTaxonomyId;
    }

    public void setTermTaxonomyId(int termTaxonomyId) {
        this.termTaxonomyId = termTaxonomyId;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }

}
