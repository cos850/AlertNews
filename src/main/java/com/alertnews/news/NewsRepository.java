package com.alertnews.news;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends CrudRepository<News, Long> {

    Optional<News> findFirstByPublishDateAndNation(String publishDate, String nation);
}
