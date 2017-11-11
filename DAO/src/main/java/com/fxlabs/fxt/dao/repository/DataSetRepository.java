package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.run.DataSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSetRepository extends JpaRepository<DataSet, String> {
}
