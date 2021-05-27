package com.pik.hotpoll.domain.search;

import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "SearchCriteriaBuilder")
class SearchCriteria {

    private static final List<String> OPERATIONS = Arrays.asList("<", ">", ":");

    private String key;
    private String operation;
    private String value;

    public static class SearchCriteriaBuilder {

    }

    Boolean isValid() {
        return key != null && OPERATIONS.contains(operation) && value != null;
    }

}