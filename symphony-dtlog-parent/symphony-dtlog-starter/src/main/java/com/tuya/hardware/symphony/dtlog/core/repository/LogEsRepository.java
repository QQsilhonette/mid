package com.tuya.hardware.symphony.dtlog.core.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageRowBounds;
import com.tuya.hardware.symphony.dtlog.core.base.LogAliyunElasticClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author 一兮
 * @Date 2021/03/15
 * @Description
 */
@Slf4j
@Repository
public class LogEsRepository {

    @Resource
    private LogAliyunElasticClient logAliyunElasticClient;

    public List<JSONObject> search(String index, QueryBuilder queryBuilder, SortBuilder sortBuilder, PageRowBounds pageRowBounds) {
        List<SortBuilder> sortBuilders = new ArrayList<>();
        if (sortBuilder != null) {
            sortBuilders.add(sortBuilder);
        }
        return searchImpl(index, queryBuilder, null, sortBuilders, pageRowBounds);
    }

    public List<JSONObject> searchImpl(String index, QueryBuilder queryBuilder, HighlightBuilder hiBuilder, List<SortBuilder> sortBuilders, PageRowBounds pageRowBounds) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(queryBuilder)
                .from(pageRowBounds.getOffset())
                .size(pageRowBounds.getLimit());
        if (CollectionUtils.isNotEmpty(sortBuilders)) {
            sortBuilders.stream().forEach(sortBuilder -> {
                searchSourceBuilder.sort(sortBuilder);
            });
        }
        if (null != hiBuilder) {
            searchSourceBuilder.highlighter(hiBuilder);
        }
        SearchRequest searchRequest = new SearchRequest(index)
                .source(searchSourceBuilder);
        SearchResponse response;
        try {
            response = logAliyunElasticClient.search(searchRequest, RequestOptions.DEFAULT);
            pageRowBounds.setTotal(response.getHits().getTotalHits().value);
            if (response.getHits().getHits() != null) {
                return StreamSupport.stream(response.getHits().spliterator(), false)
                        .map(t -> {
                            String id = t.getId();
                            JSONObject jsonObject = JSONObject.parseObject(t.getSourceAsString());
                            jsonObject.put("id", id);
                            // 处理highlight
                            if (null != hiBuilder) {
                                jsonObject.put("highlights", parseHighLightResult(t.getHighlightFields()));
                            }
                            return jsonObject;
                        })
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            log.warn("【查询es信息异常】", e);
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }


    public void addOrUpdate(String index, String id, Map<String, Object> jsonMap) {
        log.info("【request={}】", JSON.toJSONString(jsonMap));
        IndexRequest indexRequest = new IndexRequest(index).id(id);
        indexRequest.source(jsonMap);

        UpdateRequest updateRequest = new UpdateRequest(index, id);
        updateRequest.retryOnConflict(3).doc(jsonMap).upsert(indexRequest);
        updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        log.info("【update  Request={}】", updateRequest.toString());
        try {
            UpdateResponse updateResponse = logAliyunElasticClient.update(updateRequest, RequestOptions.DEFAULT);
            log.info("response={}", JSON.toJSONString(updateResponse));
        } catch (Exception e) {
            log.warn("【新增修改日志信息到es失败】", e);
        }
    }


    private Map<String, String> parseHighLightResult(Map<String, HighlightField> highlightFields) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, HighlightField> entry : highlightFields.entrySet()) {
            HighlightField content = entry.getValue();
            String newContent = "";
            if (content != null) {
                Text[] fragments = content.fragments();
                for (Text text : fragments) {
                    newContent += text;
                }
            }
            result.put(entry.getKey(), newContent);
        }
        return result;
    }

}
