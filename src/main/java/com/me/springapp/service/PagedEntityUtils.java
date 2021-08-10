package com.me.springapp.service;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

interface PagedEntityUtils {
    public static Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.DESC;
    }

    public static List<Sort.Order> getSortOrders(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        // sorting single column
        // ?sort=column1,direction1
        if (!sort[0].contains(",")) {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            return orders;
        }
        // sort more than 2 columns
        // ?sort=column1,direction1&sort=column2,direction2
        for (String sortOrder : sort) {
            String[] sortArray = sortOrder.split(",");
            orders.add(new Sort.Order(getSortDirection(sortArray[1]), sortArray[0]));
        }
        return orders;
    }
}
