

package com.radius.issue_tracker.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.CustomConversions;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * @author nitish on 2019-02-02.
 */
@Configuration
@EnableCassandraRepositories
public class CassandraConfig {
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  @Value("${cassandra.address}")
  private String cassandraAddress;
  @Value("${cassandra.port}")
  private String cassandraPort;
  @Value("${keyspace.name:issue_tracker}")
  private String keyspaceName;

  @Bean
  public CassandraOperations cassandraTemplate() {
    return new CassandraTemplate(session().getObject());
  }

  @Bean
  public CassandraSessionFactoryBean session() {
    CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
    session.setCluster(cluster().getObject());
    session.setKeyspaceName(keyspaceName);//keyspace defined
    session.setConverter(converter());
    session.setSchemaAction(SchemaAction.NONE);
    return session;
  }

  @Bean
  public CassandraClusterFactoryBean cluster() {
    CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
    cluster.setContactPoints(cassandraAddress); //ip address of the cassandra
    cluster.setPort(Integer.parseInt(cassandraPort)); //port of the cassandra running
    return cluster;
  }

  @Bean
  public CassandraConverter converter() {
    MappingCassandraConverter mappingCassandraConverter = new MappingCassandraConverter(
        mappingContext());
    CustomConversions customConversions = customConversions();
    mappingCassandraConverter.setCustomConversions(customConversions);
    return mappingCassandraConverter;
  }

  @Bean
  public CassandraMappingContext mappingContext() {
    return new BasicCassandraMappingContext();
  }

  private CustomConversions customConversions() {
    List<Converter> converters = new ArrayList<>();

    converters.add(new StringConverter());
    converters.add(new LocalDateTimeToDateCoverter());

    return new CustomConversions(converters);
  }

  public class StringConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
      LocalDateTime dateTime = null;
      try {
        dateTime = LocalDateTime.parse(source, FORMATTER);
      } catch (DateTimeParseException ignored) {
      }
      return dateTime;
    }

  }

  public class LocalDateTimeToDateCoverter implements Converter<LocalDateTime, String> {

    @Override
    public String convert(LocalDateTime source) {
      String dateTime = null;
      try {
        dateTime = source.format(FORMATTER);
      } catch (DateTimeParseException ignored) {
      }
      return dateTime;
    }

  }


}
