package com.tigercel.service;

import com.tigercel.Bean.PageBean;
import com.tigercel.model.Rule;
import com.tigercel.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by freedom on 2016/3/30.
 */
@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    public static final String CACHE_NAME = "cache.rule";

    @Cacheable(value = CACHE_NAME)
    public Page<Rule> findAll(PageBean pb) {
        return ruleRepository.findAll(
                new PageRequest(pb.getPage() - 1,
                                pb.getSize(),
                                Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                                pb.getSortName()
                                )
        );
    }

    @Cacheable(value = CACHE_NAME, key = "#name")
    public Rule findByName(String name) {
        return ruleRepository.findByName(name);
    }

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void save(Rule rule) {
        ruleRepository.save(rule);
    }

    @Cacheable(value = CACHE_NAME, key = "#id.toString().concat('-rules')")
    public Rule findOne(Long id) {
        return ruleRepository.findOne(id);
    }

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void delete(Long id) {
        ruleRepository.delete(id);
    }
}
