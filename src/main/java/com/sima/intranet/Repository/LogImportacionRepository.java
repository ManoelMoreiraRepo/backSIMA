package com.sima.intranet.Repository;

import com.sima.intranet.Entity.LogImportacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogImportacionRepository extends JpaRepository<LogImportacion,Long> {
}
