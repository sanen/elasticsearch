/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.client.ml;

import org.elasticsearch.client.Validatable;
import org.elasticsearch.client.core.PageParams;
import org.elasticsearch.client.ml.job.config.MlFilter;
import org.elasticsearch.common.xcontent.ObjectParser;
import org.elasticsearch.common.xcontent.ToXContentObject;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Objects;

/**
 * A request to retrieve {@link MlFilter}s
 */
public class GetFiltersRequest implements Validatable, ToXContentObject {

    public static final ObjectParser<GetFiltersRequest, Void> PARSER =
        new ObjectParser<>("get_filters_request", GetFiltersRequest::new);

    static {
        PARSER.declareString(GetFiltersRequest::setFilterId, MlFilter.ID);
        PARSER.declareInt(GetFiltersRequest::setFrom, PageParams.FROM);
        PARSER.declareInt(GetFiltersRequest::setSize, PageParams.SIZE);
    }

    private String filterId;
    private Integer from;
    private Integer size;

    public String getFilterId() {
        return filterId;
    }

    public Integer getFrom() {
        return from;
    }

    public Integer getSize() {
        return size;
    }

    /**
     * Sets the filter id
     * @param filterId the filter id
     */
    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    /**
     * Sets the number of filters to skip.
     * @param from set the `from` parameter
     */
    public void setFrom(Integer from) {
        this.from = from;
    }

    /**
     * Sets the number of filters to return.
     * @param size set the `size` parameter
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject();
        if (filterId != null) {
            builder.field(MlFilter.ID.getPreferredName(), filterId);
        }
        if (from != null) {
            builder.field(PageParams.FROM.getPreferredName(), from);
        }
        if (size != null) {
            builder.field(PageParams.SIZE.getPreferredName(), size);
        }
        builder.endObject();
        return builder;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        GetFiltersRequest request = (GetFiltersRequest) obj;
        return Objects.equals(filterId, request.filterId)
            && Objects.equals(from, request.from)
            && Objects.equals(size, request.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filterId, from, size);
    }
}
