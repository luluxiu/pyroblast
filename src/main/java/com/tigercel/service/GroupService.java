package com.tigercel.service;

import com.tigercel.Bean.PageBean;
import com.tigercel.model.Group;
import com.tigercel.model.Rule;
import com.tigercel.repository.GroupRepository;
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
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;


    public static final String CACHE_NAME = "cache.group";

    @Cacheable(value = CACHE_NAME, key = "#name")
    public Group findByName(String name) {

        return  groupRepository.findByName(name);
    }

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void save(Group group, Rule rule) {

        group.setRuleName(rule.getName());
        group.setRule(rule);
        groupRepository.save(group);
    }

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void save(Group group) {

        groupRepository.save(group);
    }

    @Cacheable(value = CACHE_NAME, key = "#id.toString().concat('-groups')")
    public Group findOne(Long id) {

        return groupRepository.findOne(id);
    }

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void delete(Long id) {

        groupRepository.delete(id);
    }

    @Cacheable(value = CACHE_NAME)
    public Page<Group> findAll(PageBean pb) {
        return groupRepository.findAll(
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }

    @Cacheable(value = CACHE_NAME)
    public Page<Group> findAllByName(PageBean pb, String name) {
        return groupRepository.findAllByName(name,
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }
}
