/**
 * Copyright (C) 2019 Mike Hummel (mh@mhus.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.mhus.osgi.transform.velocity;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.resource.Resource;

import de.mhus.lib.core.MString;

public class TransformResourceManager
        implements org.apache.velocity.runtime.resource.ResourceManager {

    private org.apache.velocity.runtime.resource.ResourceManager instance =
            new org.apache.velocity.runtime.resource.ResourceManagerImpl();
    private RuntimeServices rs;

    public void updatePath(String... paths) {
        instance = new org.apache.velocity.runtime.resource.ResourceManagerImpl();
        rs.getConfiguration()
                .addProperty(
                        "resource.loader.file." + RuntimeConstants.RESOURCE_LOADER_PATHS,
                        MString.join(paths, ','));
        instance.initialize(rs);
    }

    @Override
    public void initialize(RuntimeServices rs) {
        this.rs = rs;
        if (instance != null) instance.initialize(rs);
    }

    @Override
    public Resource getResource(String resourceName, int resourceType, String encoding)
            throws ResourceNotFoundException, ParseErrorException {
        return instance.getResource(resourceName, resourceType, encoding);
    }

    @Override
    public String getLoaderNameForResource(String resourceName) {
        return instance.getLoaderNameForResource(resourceName);
    }
}
