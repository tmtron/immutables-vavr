/*
 * Copyright 2013-2017 Immutables Authors and Contributors
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

package org.immutables.vavr.encodings;

import org.immutables.encode.Encoding;

import io.vavr.collection.HashSet;
import io.vavr.collection.LinkedHashSet;
import io.vavr.collection.Set;

@Encoding
class VavrSetEncoding<T>
{
  // Using a linked variant provides more predictable semantics for serialization
  @Encoding.Impl
  private Set<T> field = LinkedHashSet.empty();

  VavrSetEncoding()
  {

  }

  @SafeVarargs
  @Encoding.Copy
  @Encoding.Naming(standard = Encoding.StandardNaming.WITH)
  public final Set<T> withVarArgs(final T... elements)
  {
    return LinkedHashSet.of(elements);
  }

  @Encoding.Copy
  @Encoding.Naming(standard = Encoding.StandardNaming.WITH)
  public final Set<T> withIterable(final Iterable<? extends T> elements)
  {
    return LinkedHashSet.ofAll(elements);
  }

  // NOTE: when we specify a copy method, the default one is not longer generated,
  // thus we must explicitly add it
  @Encoding.Copy
  @Encoding.Naming(standard = Encoding.StandardNaming.WITH)
  public final Set<T> with(final Set<T> value)
  {
    return value;
  }

  @Encoding.Builder
  static final class Builder<T>
  {
    private Set<T> set = HashSet.empty();

    Builder()
    {

    }

    @SafeVarargs
    @Encoding.Naming(standard = Encoding.StandardNaming.ADD)
    @Encoding.Init
    final void add(
      final T... elements)
    {
      this.set = this.set.addAll(HashSet.of(elements));
    }

    @Encoding.Naming(standard = Encoding.StandardNaming.ADD_ALL)
    @Encoding.Init
    void addAll(
      final Iterable<T> element)
    {
      this.set = this.set.addAll(element);
    }

    @Encoding.Init
    @Encoding.Copy
    void set(
      final Set<T> elements)
    {
      this.set = elements;
    }

    @Encoding.Naming(value = "setIterable*")
    @Encoding.Init
    void setIterable(
      final Iterable<T> elements)
    {
      this.set = HashSet.ofAll(elements);
    }

    @Encoding.Build
    Set<T> build()
    {
      return this.set;
    }
  }
}
