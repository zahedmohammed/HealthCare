package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.run.DataSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataSetRepository extends JpaRepository<DataSet, String> {

    Page<DataSet> findByRunId(String id, Pageable pageable);
}
