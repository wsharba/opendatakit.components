/**
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.openid4java.util;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class HttpFetcherFactory {

  private final Provider<HttpFetcher> _provider;

  @Inject
  public HttpFetcherFactory(Provider<HttpFetcher> provider)
  {
      _provider = provider;
  }

  /**
   * Public constructor for non-Guice installations. Results in
   * {@link HttpCache} being used as the {@link HttpFetcher}
   */
  public HttpFetcherFactory(HttpClientFactory clientFactory)
  {
      this(new HttpCacheProvider(clientFactory));
  }
  
  public HttpFetcher createFetcher(HttpRequestOptions defaultOptions)
  {
      final HttpFetcher fetcher = _provider.get();
      fetcher.setDefaultRequestOptions(defaultOptions);
      return fetcher;
  }

  private static class HttpCacheProvider implements Provider<HttpFetcher> {
	  
     private final HttpClientFactory clientFactory; 
	  
	  public HttpCacheProvider(HttpClientFactory clientFactory)
	  {
	    this.clientFactory = clientFactory;
	  }
	  
    public HttpFetcher get()
    {
    	return new HttpCache(this.clientFactory);
    }
  }
}
