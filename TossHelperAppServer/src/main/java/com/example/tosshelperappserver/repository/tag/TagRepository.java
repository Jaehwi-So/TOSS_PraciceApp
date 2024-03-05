package com.example.tosshelperappserver.repository.tag;

import com.example.tosshelperappserver.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>, TagCustomRepository {

}


