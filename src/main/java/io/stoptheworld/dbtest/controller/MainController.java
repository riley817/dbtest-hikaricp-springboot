package io.stoptheworld.dbtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
public class MainController {

    @Autowired @Qualifier("testDataSource")
    private DataSource testDataSource;

    @RequestMapping("/")
    public String dbcpTest() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" hikari-cp connection test ( 최초 설정값 출력 ) ");
        stringBuilder.append(" \n " + testDataSource.toString());
        return stringBuilder.toString();
    }
}
