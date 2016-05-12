package com.tigercel.service;

import com.tigercel.Bean.PageBean;
import com.tigercel.model.PyroblastUser;
import com.tigercel.repository.PyroblastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by freedom on 2016/4/11.
 */
@Service
public class PyroUserService {

    public static final String CACHE_NAME = "cache.pyrouser";

    @Autowired
    private PyroblastRepository pyroblastRepository;

    @Cacheable(value = CACHE_NAME)
    public Page<PyroblastUser> findAll(PageBean pb) {

        return pyroblastRepository.findAll(
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }

    public Page<PyroblastUser> findAllByUsername(String name, PageBean pb) {
        return pyroblastRepository.findAllByUsername(name,
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void delete(Long id) {
        pyroblastRepository.delete(id);
    }

    public PyroblastUser findOne(Long id) {
        return pyroblastRepository.findOne(id);
    }


    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public PyroblastUser save(PyroblastUser user) {
        return pyroblastRepository.save(user);
    }

    public PyroblastUser findOneByUsername(String username) {
        return pyroblastRepository.findByUsername(username);
    }
}
