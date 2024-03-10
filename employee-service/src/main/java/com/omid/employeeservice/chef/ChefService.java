package com.omid.employeeservice.chef;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChefService {
    private final ChefRepository chefRepository;
    private final MongoTemplate mongoTemplate;

    @CacheEvict(value = "all-chefs", allEntries = true)
    public Chef create(Chef chef) {
        chef.setId(UUID.randomUUID().toString());
        ZoneId zoneId = ZoneId.of("Asia/Tehran");
        LocalDate date = LocalDate.now();
        chef.setServiceDate(date.atStartOfDay(zoneId).toInstant().toEpochMilli());
        mongoTemplate.insert(chef);
        return chefRepository.save(chef);
    }

    public Chef loadOneById(String id) {
        return mongoTemplate.findById(id, Chef.class);
    }

    @CacheEvict(value = "all-chefs", allEntries = true)
    public Chef update(String id, Chef chef) {
        Chef dbChef = loadOneById(id);

        if (chef.getFirstName() != null) {
            dbChef.setFirstName(chef.getFirstName());
        }
        if (chef.getLastName() != null) {
            dbChef.setLastName(chef.getLastName());
        }
        if (chef.getExperienceYear() != null) {
            dbChef.setExperienceYear(chef.getExperienceYear());
        }
        if (chef.getAbout() != null) {
            dbChef.setAbout(chef.getAbout());
        }
        if (chef.getNationalCode() != null) {
            dbChef.setNationalCode(chef.getNationalCode());
        }
        if (chef.getServiceDate() != null) {
            dbChef.setServiceDate(chef.getServiceDate());
        }
        if (chef.getActive() != null) {
            dbChef.setActive(chef.getActive());
        }

        return chefRepository.saveAndFlush(dbChef);
    }

    @Cacheable(value = "all-chefs")
    public List<Chef> loadAll() {
        return chefRepository.findAll();
    }

    public List<Chef> search(String value) {
        return chefRepository.search("%" + value + "%");
    }

}
