package com.roze.thundercall.repository;

import com.roze.thundercall.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RequestRepository extends JpaRepository<Request,Long>, JpaSpecificationExecutor<Request> {
}
