/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.cache.query;

import org.apache.ignite.lang.*;
import org.apache.ignite.internal.util.tostring.*;
import org.apache.ignite.internal.util.typedef.internal.*;

import java.util.*;

/**
 * Cache query type metadata.
 */
public class CacheQueryTypeMetadata {
    /** Key class used to store value in cache. */
    private String keyType;

    /** Type name, e.g. class name. */
    private String valType;

    /** Database table metadata.*/
    private CacheQueryTableMetadata tblMeta;

    /** Fields to be queried, in addition to indexed fields. */
    @GridToStringInclude
    private Map<String, Class<?>> qryFlds;

    /** Fields to index in ascending order. */
    @GridToStringInclude
    private Map<String, Class<?>> ascFlds;

    /** Fields to index in descending order. */
    @GridToStringInclude
    private Map<String, Class<?>> descFlds;

    /** Fields to index as text. */
    @GridToStringInclude
    private Collection<String> txtFlds;

    /** Fields to create group indexes for. */
    @GridToStringInclude
    private Map<String, LinkedHashMap<String, IgniteBiTuple<Class<?>, Boolean>>> grps;

    /**
     * Default constructor.
     */
    public CacheQueryTypeMetadata() {
        tblMeta = new CacheQueryTableMetadata();

        qryFlds = new LinkedHashMap<>();

        ascFlds = new LinkedHashMap<>();

        descFlds = new LinkedHashMap<>();

        txtFlds = new LinkedHashSet<>();

        grps = new LinkedHashMap<>();
    }

    /**
     * Copy constructor.
     */
    public CacheQueryTypeMetadata(CacheQueryTypeMetadata src) {
        keyType = src.getKeyType();

        valType = src.getType();

        tblMeta = new CacheQueryTableMetadata(src.getTableMetadata());

        qryFlds = new LinkedHashMap<>(src.getQueryFields());

        ascFlds = new LinkedHashMap<>(src.getAscendingFields());

        descFlds = new LinkedHashMap<>(src.getDescendingFields());

        txtFlds = new LinkedHashSet<>(src.getTextFields());

        grps = new LinkedHashMap<>(src.getGroups());
    }

    /**
     * Gets key type.
     *
     * @return Key type.
     */
    public String getKeyType() {
        return keyType;
    }

    /**
     * Sets key type.
     *
     * @param keyType Key type.
     */
    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    /**
     * Gets type (e.g. class name).
     *
     * @return Type name.
     */
    public String getType() {
        return valType;
    }

    /**
     * Sets type.
     *
     * @param cls Type class.
     */
    public void setType(Class<?> cls) {
        setType(cls.getName());
    }

    /**
     * Sets type.
     *
     * @param type Type name.
     */
    public void setType(String type) {
        valType = type;
    }

    /**
     * Gets table metadata.
     *
     * @return table metadata.
     */
    public CacheQueryTableMetadata getTableMetadata() {
        return tblMeta;
    }

    /**
     * Sets table metadata.
     *
     * @param tblMeta New table metadata.
     */
    public void setTableMetadata(CacheQueryTableMetadata tblMeta) {
        this.tblMeta = tblMeta;
    }

    /**
     * Gets query-enabled fields.
     *
     * @return Collection of fields available for query.
     */
    public Map<String, Class<?>> getQueryFields() {
        return qryFlds;
    }

    /**
     * Sets query fields map.
     *
     * @param qryFlds Query fields.
     */
    public void setQueryFields(Map<String, Class<?>> qryFlds) {
        this.qryFlds = qryFlds;
    }

    /**
     * Gets ascending-indexed fields.
     *
     * @return Map of ascending-indexed fields.
     */
    public Map<String, Class<?>> getAscendingFields() {
        return ascFlds;
    }

    /**
     * Sets ascending-indexed fields.
     *
     * @param ascFlds Map of ascending-indexed fields.
     */
    public void setAscendingFields(Map<String, Class<?>> ascFlds) {
        this.ascFlds = ascFlds;
    }

    /**
     * Gets descending-indexed fields.
     *
     * @return Map of descending-indexed fields.
     */
    public Map<String, Class<?>> getDescendingFields() {
        return descFlds;
    }

    /**
     * Sets descending-indexed fields.
     *
     * @param descFlds Map of descending-indexed fields.
     */
    public void setDescendingFields(Map<String, Class<?>> descFlds) {
        this.descFlds = descFlds;
    }

    /**
     * Gets text-indexed fields.
     *
     * @return Collection of text indexed fields.
     */
    public Collection<String> getTextFields() {
        return txtFlds;
    }

    /**
     * Sets text-indexed fields.
     *
     * @param txtFlds Text-indexed fields.
     */
    public void setTextFields(Collection<String> txtFlds) {
        this.txtFlds = txtFlds;
    }

    /**
     * Gets group-indexed fields.
     *
     * @return Map of group-indexed fields.
     */
    public Map<String, LinkedHashMap<String, IgniteBiTuple<Class<?>, Boolean>>> getGroups() {
        return grps;
    }

    /**
     * Sets group-indexed fields.
     *
     * @param grps Map of group-indexed fields from index name to index fields.
     */
    public void setGroups(Map<String, LinkedHashMap<String, IgniteBiTuple<Class<?>, Boolean>>> grps) {
        this.grps = grps;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(CacheQueryTypeMetadata.class, this);
    }
}
