package com.example.demo.dao.elasticsearch;

import com.example.demo.entity.DiscussPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {

    @Highlight(fields = {
            @HighlightField(name = "title"),
            @HighlightField(name = "content")
    })
    SearchHits<DiscussPost> findByTitleOrContent(String title, String content);

    @Highlight(fields = {
            @HighlightField(name = "title"),
            @HighlightField(name = "content")
    })
    @Query("{\"bool\": {" +
            "\"should\" : [" +
            "{\"query_string\" : {\"query\" : \"?0\", \"fields\" : [\"title\"]}}," +
            "{\"query_string\" : {\"query\" : \"?0\", \"fields\" : [\"content\"]}}" +
            "]" +
            "}}")
    SearchHits<DiscussPost> find(String keyword);

    @Query("{\"bool\": {" +
            "\"should\" : [" +
            "{\"query_string\" : {\"query\" : \"?0\", \"fields\" : [\"title\"]}}," +
            "{\"query_string\" : {\"query\" : \"?0\", \"fields\" : [\"content\"]}}" +
            "]" +
            "}}")
    Page<DiscussPost> findPage(String keyword, Pageable pageable);

}
