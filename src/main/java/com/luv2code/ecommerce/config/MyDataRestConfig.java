package com.luv2code.ecommerce.config;

import com.luv2code.ecommerce.entity.Country;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.ProductCategory;
import com.luv2code.ecommerce.entity.State;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;
    private EntityManager entityManager;
    @Autowired
    public MyDataRestConfig( EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
       // RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        HttpMethod[] theUnsupportedAction = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};



        //disable Http methods for ProductCategory: PUT POST DELETE PATCH
        disabledHttpMethods(Product.class, config, theUnsupportedAction);
        disabledHttpMethods(ProductCategory.class, config, theUnsupportedAction);
        disabledHttpMethods(Country.class, config, theUnsupportedAction);
        disabledHttpMethods(State.class, config, theUnsupportedAction);
        disabledHttpMethods(Order.class, config, theUnsupportedAction);

        exposeIds(config);

        //configure cors mapping
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);

    }

    private static void disabledHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedAction) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedAction))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedAction));
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        //expose entity ids

        //get a list of all entity classes from the entity manger
        Set<EntityType<?>> entities =entityManager.getMetamodel().getEntities();

        //- create an array of enity types
        List<Class> entityClass = new ArrayList<>();

        //-get the entity types for entities
        for(EntityType tempEntityType: entities ){

            entityClass.add(tempEntityType.getJavaType());
        }

        //-expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClass.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
