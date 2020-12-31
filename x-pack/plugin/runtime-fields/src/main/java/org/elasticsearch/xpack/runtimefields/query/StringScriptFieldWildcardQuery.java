/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.xpack.runtimefields.query;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.automaton.Automaton;
import org.apache.lucene.util.automaton.ByteRunAutomaton;
import org.elasticsearch.common.lucene.search.AutomatonQueries;
import org.elasticsearch.script.Script;
import org.elasticsearch.xpack.runtimefields.mapper.StringFieldScript;

import java.util.Objects;

public class StringScriptFieldWildcardQuery extends AbstractStringScriptFieldAutomatonQuery {
    private final String pattern;
    private final boolean caseInsensitive;

    public StringScriptFieldWildcardQuery(
        Script script,
        StringFieldScript.LeafFactory leafFactory,
        String fieldName,
        String pattern,
        boolean caseInsensitive
    ) {
        super(
            script,
            leafFactory,
            fieldName,
            new ByteRunAutomaton(buildAutomaton(new Term(fieldName, Objects.requireNonNull(pattern)), caseInsensitive))
        );
        this.pattern = pattern;
        this.caseInsensitive = caseInsensitive;
    }

    private static Automaton buildAutomaton(Term term, boolean caseInsensitive) {
        if (caseInsensitive) {
            return AutomatonQueries.toCaseInsensitiveWildcardAutomaton(term, Integer.MAX_VALUE);
        }
        return WildcardQuery.toAutomaton(term);
    }

    @Override
    public final String toString(String field) {
        if (fieldName().equals(field)) {
            return pattern;
        }
        return fieldName() + ":" + pattern;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pattern, caseInsensitive);
    }

    @Override
    public boolean equals(Object obj) {
        if (false == super.equals(obj)) {
            return false;
        }
        StringScriptFieldWildcardQuery other = (StringScriptFieldWildcardQuery) obj;
        return pattern.equals(other.pattern) && caseInsensitive == other.caseInsensitive;
    }

    String pattern() {
        return pattern;
    }

    boolean caseInsensitive() {
        return caseInsensitive;
    }
}
