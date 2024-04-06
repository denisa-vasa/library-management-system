package com.gisdev.dea.repository;

import com.gisdev.dea.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
    Library findLibraryByName (String nameLibrary);
}
