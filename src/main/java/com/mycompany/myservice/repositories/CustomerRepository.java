package com.mycompany.myservice.repositories;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mycompany.myservice.entities.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerRepository extends BaseMapper<Customer> {}
