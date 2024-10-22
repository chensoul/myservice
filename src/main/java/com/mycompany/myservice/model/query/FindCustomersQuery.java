package com.mycompany.myservice.model.query;

public record FindCustomersQuery(int pageNumber, int pageSize, String sortBy, String sortDir) {}
