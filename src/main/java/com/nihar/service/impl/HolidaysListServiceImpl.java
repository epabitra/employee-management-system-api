package com.nihar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nihar.entity.HolidaysList;
import com.nihar.repository.HolidaysListRepository;
import com.nihar.service.HolidaysListService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HolidaysListServiceImpl implements HolidaysListService {
    private final HolidaysListRepository repo;
    public HolidaysList save(HolidaysList obj) { return repo.save(obj); }
    public List<HolidaysList> findAll() { return repo.findAll(); }
    public Optional<HolidaysList> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}
