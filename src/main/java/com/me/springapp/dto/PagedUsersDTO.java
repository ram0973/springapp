package com.me.springapp.dto;

import com.me.springapp.model.User;

import java.util.List;

public record PagedUsersDTO(List<User> users, int currentPage, long totalItems, int totalPages) {
}
