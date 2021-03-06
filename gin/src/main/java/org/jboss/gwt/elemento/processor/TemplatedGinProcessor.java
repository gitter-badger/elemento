/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.gwt.elemento.processor;

import java.util.List;
import javax.annotation.processing.Processor;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableMap;
import org.jboss.gwt.elemento.processor.context.AbstractPropertyInfo;
import org.jboss.gwt.elemento.processor.context.FreemarkerContext;
import org.jboss.gwt.elemento.template.Templated;

@AutoService(Processor.class)
@SupportedAnnotationTypes("org.jboss.gwt.elemento.template.Templated")
public class TemplatedGinProcessor extends TemplatedProcessor {

    private static final String FREEMARKER_TEMPLATE = "TemplatedProvider.ftl";

    public TemplatedGinProcessor() {
        super(TemplatedGinProcessor.class, "templates");
    }

    @Override
    protected void processType(final TypeElement type, final Templated templated) {
        String subclass = TypeSimplifier.simpleNameOf(generatedClassName(type, "Templated_", "_Provider"));
        String createMethod = verifyCreateMethod(type);
        FreemarkerContext context = new FreemarkerContext(TypeSimplifier.packageNameOf(type),
                TypeSimplifier.classNameOf(type), subclass, createMethod);

        List<AbstractPropertyInfo> abstractProperties = processAbstractProperties(type);
        context.setAbstractProperties(abstractProperties);

        code(FREEMARKER_TEMPLATE, context.getPackage(), context.getSubclass(),
                () -> ImmutableMap.of("context", context));
        info("Generated templated provider [%s] for [%s]", context.getSubclass(), context.getBase());
    }
}
