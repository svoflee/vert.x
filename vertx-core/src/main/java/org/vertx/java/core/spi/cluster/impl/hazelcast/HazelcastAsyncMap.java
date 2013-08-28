package org.vertx.java.core.spi.cluster.impl.hazelcast;

import com.hazelcast.core.IMap;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.impl.BlockingAction;
import org.vertx.java.core.impl.VertxInternal;
import org.vertx.java.core.spi.cluster.AsyncMap;

import java.util.Map;

/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 */
public class HazelcastAsyncMap<K, V> implements AsyncMap<K, V> {

  private final VertxInternal vertx;
  private final IMap<K, V> map;

  public HazelcastAsyncMap(VertxInternal vertx, IMap<K, V> map) {
    this.vertx = vertx;
    this.map = map;
  }

  @Override
  public void get(final K k, Handler<AsyncResult<V>> asyncResultHandler) {
    new BlockingAction<V>(vertx, asyncResultHandler) {
      public V action() {
        return map.get(k);
      }
    }.run();
  }

  @Override
  public void put(final K k, final V v, Handler<AsyncResult<Void>> completionHandler) {
    new BlockingAction<Void>(vertx, completionHandler) {
      public Void action() {
        map.put(k, v);
        return null;
      }
    }.run();
  }

  @Override
  public void remove(final K k, Handler<AsyncResult<Void>> completionHandler) {
    new BlockingAction<Void>(vertx, completionHandler) {
      public Void action() {
        map.remove(k);
        return null;
      }
    }.run();
  }
}
