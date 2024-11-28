package com.soulstar.userFacing.repository.DataSource;

import com.soulstar.userFacing.repository.DataSource.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
@Service
public class PostgresDataSource extends DataSourceService {

    @Autowired
    @Qualifier("postgresDataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
