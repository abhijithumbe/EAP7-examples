/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.helloworld;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.*;

@SuppressWarnings("serial")
@WebServlet(
        value = "/HelloWorld",
        initParams = {
                @WebInitParam(name = "configfile", value = "app2.properties"),
                
        })
public class HelloWorldServlet extends HttpServlet {

	static String PAGE_HEADER = "<html><head><title>helloworld</title></head><body>";

	static String PAGE_FOOTER = "</body></html>";

	@Inject
	HelloService helloService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	public void init(ServletConfig config) throws ServletException {

		try {
			String fileName = config.getInitParameter("configfile");
			System.out.println("file name=" + fileName);
			InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			Properties properties = new Properties();
			properties.load(input);
			System.out.println("properties file=" +properties);
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("POST method called");
		try {

			// Use Any Environmental Variable , here i have used CATALINA_HOME
			// String propertyHome = System.getenv("CATALINA_HOME");

			/*
			 * String propertiesFileName="appnew.properties";
			 * System.out.println("System.getProperty"+System.getProperty(
			 * "app-prop")); String path = System.getProperty("app-prop") +
			 * propertiesFileName; System.out.println("path"+ path); String
			 * path2="file://"+path; Properties props = new Properties(); URL
			 * url = new URL(path2); props.load(url.openStream());
			 * System.out.println("properties" + props);
			 */

			// String propertiesFileName="appnew.properties";
			System.out.println("System.getProperty" + System.getProperty("app2-prop"));
			String path = "file://" + System.getProperty("app2-prop");
			System.out.println("path" + path);

			Properties props = new Properties();
			URL url = new URL(path);
			props.load(url.openStream());
			System.out.println("properties" + props);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
