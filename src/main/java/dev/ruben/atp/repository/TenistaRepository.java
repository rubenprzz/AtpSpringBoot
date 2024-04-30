package dev.ruben.atp.repository;

import dev.ruben.atp.models.Tenista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TenistaRepository extends JpaRepository<Tenista,Long>, JpaSpecificationExecutor<Tenista> {


}
