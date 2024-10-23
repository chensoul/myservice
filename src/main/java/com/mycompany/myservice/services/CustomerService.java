package com.mycompany.myservice.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mycompany.myservice.entities.Customer;
import com.mycompany.myservice.exception.CustomerNotFoundException;
import com.mycompany.myservice.mapper.CustomerMapper;
import com.mycompany.myservice.model.query.FindCustomersQuery;
import com.mycompany.myservice.model.request.CustomerRequest;
import com.mycompany.myservice.model.response.CustomerResponse;
import com.mycompany.myservice.model.response.PagedResult;
import com.mycompany.myservice.repositories.CustomerRepository;
import jakarta.annotation.Resource;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerService extends ServiceImpl<CustomerRepository, Customer> {
    @Resource
    private CustomerMapper customerMapper;

    public PagedResult<CustomerResponse> findAllCustomers(FindCustomersQuery findCustomersQuery) {

        // create Pageable instance
        Page pageable = createPageable(findCustomersQuery);

        IPage<Customer> customersPage = page(pageable);

        List<CustomerResponse> customerResponseList = customerMapper.toResponseList(customersPage.getRecords());

        return new PagedResult(customersPage, customerResponseList);
    }

    private Page<Customer> createPageable(FindCustomersQuery findCustomersQuery) {
        int pageNo = Math.max(findCustomersQuery.pageNumber() - 1, 0);
        OrderItem orderItem = findCustomersQuery.sortDir().equalsIgnoreCase("asc")
                ? OrderItem.asc(findCustomersQuery.sortBy())
                : OrderItem.desc(findCustomersQuery.sortBy());
        Page page = Page.of(pageNo, findCustomersQuery.pageSize(), true);
        page.setOptimizeCountSql(false);
        page.setOrders(List.of(orderItem));
        return page;
    }

    public Optional<CustomerResponse> findCustomerById(Long id) {
        return Optional.ofNullable(getById(id)).map(customerMapper::toResponse);
    }

    @Transactional
    public CustomerResponse saveCustomer(CustomerRequest customerRequest) {
        Customer customer = customerMapper.toEntity(customerRequest);
        baseMapper.insert(customer);
        return customerMapper.toResponse(customer);
    }

    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {
        Customer customer = Optional.of(baseMapper.selectById(id)).orElseThrow(() -> new CustomerNotFoundException(id));

        // Update the customer object with data from customerRequest
        customerMapper.mapCustomerWithRequest(customer, customerRequest);

        // Save the updated customer object
        baseMapper.updateById(customer);

        return customerMapper.toResponse(customer);
    }

    @Transactional
    public void deleteCustomerById(Long id) {
        baseMapper.deleteById(id);
    }
}
