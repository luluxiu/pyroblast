package com.tigercel.service;

import com.tigercel.Bean.PageBean;
import com.tigercel.Bean.PyroblastPing;
import com.tigercel.Utils.PyroblastUtils;
import com.tigercel.model.APInfo;
import com.tigercel.model.Group;
import com.tigercel.model.Rule;
import com.tigercel.repository.APRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by freedom on 2016/3/30.
 */
@Service
public class APService {
    @Autowired
    private APRepository apRepository;

    public static final String CACHE_NAME = "cache.ap";

    //@Cacheable(value = CACHE_NAME, key = "#name")
    public APInfo findByName(String name) {

        return  apRepository.findByName(name);
    }

    //@Cacheable(value = CACHE_NAME, key = "#mac.concat('-aps')")
    public APInfo findByMac(String mac) {

        return apRepository.findByMac(mac);
    }

    //@CacheEvict(value = CACHE_NAME, allEntries = true)
    public void save(APInfo ap, Rule rule) {
        ap.setUpdatedAt(new Date());
        ap.setRule(rule);
        apRepository.save(ap);
    }

    //@CacheEvict(value = CACHE_NAME, allEntries = true)
    public void save(APInfo ap, Group group, Rule rule) {

        ap.setGroupName(group.getName());
        ap.setGroup(group);
        ap.setRule(rule);
        ap.setUpdatedAt(new Date());
        if(ap.getRule() == null && group.getRule() != null) {
            ap.setRuleName(group.getRule().getName());
            ap.setRule(group.getRule());
        }
        apRepository.save(ap);
    }

    //@CacheEvict(value = CACHE_NAME, allEntries = true)
    public void save(APInfo ap, PyroblastPing pp, HttpServletRequest request) {

        ap.setLastHeartbeatAt(new Date());
        ap.setLastHeartbeatIp(PyroblastUtils.getIpAddr(request));
        ap.setLastHeartbeatSysLoad(pp.getSys_load());
        ap.setLastHeartbeatSysMemfree(pp.getSys_memfree());
        ap.setLastHeartbeatSysTime(pp.getSys_uptime());
        ap.setLastHeartbeatWifidogUptime(pp.getWifidog_uptime());

        apRepository.save(ap);
    }

    //@Cacheable(value = CACHE_NAME, key = "#id.toString().concat('-aps')")
    public APInfo findOne(Long id) {

        return apRepository.findOne(id);
    }

    //@CacheEvict(value = CACHE_NAME, allEntries = true)
    public void delete(Long id) {

        apRepository.delete(id);
    }

    //@Cacheable(value = CACHE_NAME)
    public Page<APInfo> findAll(PageBean pb) {
        return apRepository.findAll(
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }

    public Page<APInfo> findAllByMac(PageBean pb, String mac) {
        return apRepository.findAllByMac(mac,
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }

    public Page<APInfo> findAllByGroup(PageBean pb, Group group) {
        return apRepository.findAllByGroup(group,
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                ));
    }
}
