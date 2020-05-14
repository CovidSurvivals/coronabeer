package com.covidsurvivals.coronabeer;

/*
    Enum class for GraphType
 */
public enum GraphType {
    BAR("Bar Graph"),
    LINE("Line Graph");

    private String graphType;

    GraphType(String graphType){
        this.graphType = graphType;
    }

    public String getGraphType() { return graphType; }

    @Override
    public String toString() {
        return getGraphType();
    }
}
