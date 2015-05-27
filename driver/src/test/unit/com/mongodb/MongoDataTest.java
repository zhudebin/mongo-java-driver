/*
 * Copyright (c) 2008-2014 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb;

import org.bson.BsonTimestamp;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by zhudebin on 15-5-26.
 */
public class MongoDataTest {

    @Test
    public void test1() {
        MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
        DBCursor cursor = mongoClient.getDB("test").getCollection("test").find();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
//            System.out.println(dbObject);
        }
    }

    @Test
    public void test2() {
        MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
        DBCollection collection = mongoClient.getDB("test").getCollection("test");
        DBObject dbObject = new BasicDBObject();
        dbObject.put("h11", new java.sql.Date(new Date().getTime()));
        dbObject.put("h12", new Timestamp(new Date().getTime()));
        dbObject.put("h13", new BsonTimestamp());
        collection.save(dbObject);
    }

}
